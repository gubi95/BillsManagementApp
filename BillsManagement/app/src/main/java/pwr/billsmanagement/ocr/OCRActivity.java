package pwr.billsmanagement.ocr;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.ocr.matcher.MatchWorker;
import pwr.billsmanagement.ocr.matcher.Matcher;
import pwr.billsmanagement.ocr.parsers.BillParser;
import pwr.billsmanagement.ocr.parsers.Product;
import pwr.billsmanagement.ocr.parsers.TwoLineBillParser;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsTool;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsToolImpl;
import pwr.billsmanagement.readers.FileReader;
import pwr.billsmanagement.readers.PropertiesReader;

public class OCRActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PHOTO_REQUEST_CODE = 1;
    private static final String CONFIG_FILE = "properties/config.properties";
    private static final String EXTERNAL_FILES = "properties/external_files.properties";

    private CropImageView cropImageView;
    private RequestPermissionsTool requestTool;
    private Toolbar toolbar;
    private ImageButton captureImg, startOCRForCroppedImage;

    private BillsOCR billsOCR;
    private MatchWorker matchWorker;
    private PropertiesReader reader;

    private Uri billPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        Logger.init("OCR");

        initView();
        initListeners();

        reader = new PropertiesReader(getApplicationContext(), new Properties());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    private void initListeners() {
        cropImageView.setOnCropImageCompleteListener(new CropImageListener());
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        toolbar.setTitle(getString(R.string.app_name));

        captureImg = (ImageButton) findViewById(R.id.shootPhoto);
        captureImg.setImageResource(R.drawable.ic_take_photo);
        captureImg.setBackgroundColor(ContextCompat.getColor(this, R.color.activeButton));

        startOCRForCroppedImage = (ImageButton) findViewById(R.id.startOCRForCroppedImage);
        startOCRForCroppedImage.setImageResource(R.drawable.ic_start_ocr);
        startOCRForCroppedImage.setBackgroundColor(ContextCompat.getColor(this, R.color.transparentGrey));
        startOCRForCroppedImage.setEnabled(false);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        captureImageSetOnClick();
        startOCRForCroppedImageSetOnClick();
    }

    private void startOCRForCroppedImageSetOnClick() {
        startOCRForCroppedImage.setOnClickListener(v -> cropImageView.getCroppedImageAsync());
    }

    private void startOCROnCroppedImageAsync() {

        new AsyncTask<Void, Void, ArrayList<Product>>() {
            @Override
            protected ArrayList<Product> doInBackground(Void... params) {
                String result = billsOCR.doOCR(billPhoto);
                result = result.replaceAll("\n\n", "\n");
                Logger.e("WYNIK " + result);
                BillParser billParser = new TwoLineBillParser(result, null, reader.readMyProperties(CONFIG_FILE));
                billParser.setPrices(new ArrayList<>());
                billParser.setProducts(new ArrayList<>());
                ArrayList<Product> products = (ArrayList<Product>) billParser.parseOcrResult();

                return products;
            }

            @Override
            protected void onPostExecute(ArrayList<Product> shopProducts) {
                Gson gson = new Gson();
                Logger.i(gson.toJson(shopProducts));
                Intent editBillActivity = new Intent(getApplicationContext(), EditBillActivity.class);
                editBillActivity.putExtra("products_json", gson.toJson(shopProducts));
                editBillActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(editBillActivity);
            }
        }.execute();

    }

    private void captureImageSetOnClick() {
        if (captureImg != null) {
            captureImg.setOnClickListener(v -> {
                billsOCR = new BillsOCR(getAssets(), reader.readMyProperties(CONFIG_FILE), this);
                final Intent takePhotoIntent = billsOCR.startCameraActivity();
                startActivityForResult(takePhotoIntent, PHOTO_REQUEST_CODE);
            });
        }
    }

    private void initializeMatchWorker() {
        matchWorker = new MatchWorker(new Matcher(reader.readMyProperties(CONFIG_FILE)), new Properties());
        matchWorker.setProperties(reader.readMyProperties(EXTERNAL_FILES))
                .initializeMatcherDefaultDictionary(new FileReader(getApplicationContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //making photo
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Logger.i("In onActivityResult after shooting photo.");
            billPhoto = billsOCR.getOutputFileUri();
            cropImageView.setImageUriAsync(billPhoto);
            activateStartOcrButton(ContextCompat.getColor(this, R.color.activeButton), true);
        } else {
            Toast.makeText(this, "ERROR: Image was not obtained.", Toast.LENGTH_SHORT).show();
        }
    }

    private void activateStartOcrButton(int color, boolean enabled) {
        startOCRForCroppedImage.setBackgroundColor(color);
        startOCRForCroppedImage.setEnabled(enabled);
    }

    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestTool = new RequestPermissionsToolImpl();
        requestTool.requestPermissions(this, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean grantedAllPermissions = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                grantedAllPermissions = false;
            }
        }

        if (grantResults.length != permissions.length || (!grantedAllPermissions)) {
            requestTool.onPermissionDenied();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private class CropImageListener implements CropImageView.OnCropImageCompleteListener {
        @Override
        public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
            SaveCroppedImageTask saveCroppedImageTask = new SaveCroppedImageTask();
            saveCroppedImageTask.execute(result.getBitmap());
        }
    }

    private class SaveCroppedImageTask extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected Void doInBackground(Bitmap... params) {
            billPhoto = Uri.fromFile(new File(billsOCR.IMG_PATH + billsOCR.SAVE_CROPPED_AS));
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(billsOCR.IMG_PATH + billsOCR.SAVE_CROPPED_AS);
                params[0].compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            startOCROnCroppedImageAsync();
        }
    }
}