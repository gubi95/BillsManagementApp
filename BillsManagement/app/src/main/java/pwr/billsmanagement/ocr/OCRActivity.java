package pwr.billsmanagement.ocr;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import pwr.billsmanagement.bills.BillEntity;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.db.creators.DBHandler;
import pwr.billsmanagement.ocr.matcher.MatchWorker;
import pwr.billsmanagement.ocr.matcher.Matcher;
import pwr.billsmanagement.ocr.parsers.BillParser;
import pwr.billsmanagement.ocr.parsers.OcrProduct;
import pwr.billsmanagement.ocr.parsers.TwoLineBillParser;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsTool;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsToolImpl;
import pwr.billsmanagement.readers.FileReader;
import pwr.billsmanagement.readers.PropertiesReader;

public class OCRActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_ONE_LINE_BILL_PARSER = 1;
    private static final int REQUEST_TWO_LINE_BILL_PARSER = 2;
    private static final int PHOTO_REQUEST_CODE = 1;
    private static final String CONFIG_FILE = "properties/config.properties";
    private static final String EXTERNAL_FILES = "properties/external_files.properties";
    DBHandler mydb;
    private int REQUESTES_BILL_PARSER;
    private RequestPermissionsTool requestTool;
    private OCRActivityView mView;
    private BillsOCR billsOCR;
    private MatchWorker matchWorker;
    private PropertiesReader reader;
    private Uri billPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        Logger.init("OCR");

        initMView();
        initListeners();

        reader = new PropertiesReader(getApplicationContext(), new Properties());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }

    }

    private void initListeners() {
        mView.cropImageView.setOnCropImageCompleteListener(new CropImageListener());
    }

    private void initMView() {

        mView = new OCRActivityView(
                findViewById(R.id.cropImageView),
                findViewById(R.id.myToolbar),
                findViewById(R.id.shootPhoto),
                findViewById(R.id.startOCRForCroppedImage),
                findViewById(R.id.twoLineBill),
                findViewById(R.id.oneLineBill)
        );

        mView.init();
        mView.billsIconSetOnClick();

        captureImageSetOnClick();
        startOCRForCroppedImageSetOnClick();
    }

    private void startOCRForCroppedImageSetOnClick() {
        mView.startOCRForCroppedImage.setOnClickListener(v -> mView.cropImageView.getCroppedImageAsync());
    }

    private void startOCROnCroppedImageAsync() {
        StartOCRForBitmapTask startOCRForBitmapTask = new StartOCRForBitmapTask();
        startOCRForBitmapTask.execute();
    }

    private void captureImageSetOnClick() {
        if (mView.captureImg != null) {
            mView.captureImg.setOnClickListener(v -> {
                billsOCR = new BillsOCR(getAssets(), reader.readMyProperties(CONFIG_FILE), this);
                final Intent takePhotoIntent = billsOCR.startCameraActivity();
                mView.switchToAfterPhotoView();
                startActivityForResult(takePhotoIntent, PHOTO_REQUEST_CODE);
            });
        }
    }

    private void initializeMatchWorker() {
        matchWorker = new MatchWorker(new Matcher(reader.readMyProperties(CONFIG_FILE)), new Properties());
        matchWorker.setProperties(reader.readMyProperties(EXTERNAL_FILES));
        matchWorker.initializeMatcherDefaultDictionary(new FileReader(getApplicationContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            billPhoto = billsOCR.getOutputFileUri();
            mView.cropImageView.setImageUriAsync(billPhoto);
            mView.activateStartOcrButton(ContextCompat.getColor(this, R.color.activeButton), true);
        } else {
            Toast.makeText(this, "ERROR: Image was not obtained.", Toast.LENGTH_SHORT).show();
        }
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

        private ProgressDialog progressDialog;
        private Context mainActivityContext = OCRActivity.this;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(
                    mainActivityContext,
                    mainActivityContext.getString(R.string.save_cropped_image_progress_title),
                    mainActivityContext.getString(R.string.save_cropped_image_progress_message)
            );
        }

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
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            startOCROnCroppedImageAsync();
        }

    }

    private class StartOCRForBitmapTask extends AsyncTask<Void, Void, ArrayList<OcrProduct>> {

        private ProgressDialog progressDialog;
        private Context mainActivityContext = OCRActivity.this;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(
                    mainActivityContext,
                    mainActivityContext.getString(R.string.start_ocr_for_bitmap_progress_title),
                    mainActivityContext.getString(R.string.start_ocr_for_bitmap_progress_message)
            );
        }

        @Override
        protected ArrayList<OcrProduct> doInBackground(Void... params) {
            String result = billsOCR.doOCR(billPhoto);
            result = result.replaceAll("\n\n", "\n");
            Logger.e("WYNIK " + result);
            BillParser billParser = new TwoLineBillParser(result, null, reader.readMyProperties(CONFIG_FILE));
            billParser.setPrices(new ArrayList<>());
            billParser.setProducts(new ArrayList<>());
            ArrayList<OcrProduct> ocrProducts = (ArrayList<OcrProduct>) billParser.parseOcrResult();

            return ocrProducts;
        }

        @Override
        protected void onPostExecute(ArrayList<OcrProduct> shopOcrProducts) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Gson gson = new Gson();
            Logger.i(gson.toJson(shopOcrProducts));
            Intent editBillActivity = new Intent(getApplicationContext(), EditBillActivity.class);
            editBillActivity.putExtra("run_mode", "define");
            editBillActivity.putExtra("products_json", gson.toJson(shopOcrProducts));
            editBillActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(editBillActivity);
        }
    }

    private class OCRActivityView {
        private CropImageView cropImageView;
        private Toolbar toolbar;
        private ImageButton captureImg, startOCRForCroppedImage;
        private ImageView twoLineBill, oneLineBill;

        public OCRActivityView(View cropImageView, View toolbar, View captureImg,
                               View startOCRForCroppedImage, View twoLineBill,
                               View oneLineBill) {

            this.cropImageView = (CropImageView) cropImageView;
            this.toolbar = (Toolbar) toolbar;
            this.captureImg = (ImageButton) captureImg;
            this.startOCRForCroppedImage = (ImageButton) startOCRForCroppedImage;
            this.twoLineBill = (ImageView) twoLineBill;
            this.oneLineBill = (ImageView) oneLineBill;
        }

        void init() {
            toolbar.setTitle(getString(R.string.ocr_activity_name));

            twoLineBill.setImageResource(R.mipmap.ic_two_line_bill);
            oneLineBill.setImageResource(R.mipmap.ic_one_line_bill);

            captureImg.setImageResource(R.drawable.ic_take_photo);
            captureImg.setBackgroundColor(ContextCompat.getColor(OCRActivity.this, R.color.activeButton));

            startOCRForCroppedImage.setImageResource(R.drawable.ic_start_ocr);
            startOCRForCroppedImage.setBackgroundColor(ContextCompat.getColor(OCRActivity.this, R.color.transparentGrey));
            startOCRForCroppedImage.setEnabled(false);

        }

        void billsIconSetOnClick() {
            twoLineBill.setOnClickListener(v -> {
                oneLineBill.setImageResource(R.mipmap.ic_one_line_bill);
                twoLineBill.setImageResource(R.mipmap.ic_two_line_bill_selected);
                REQUESTES_BILL_PARSER = REQUEST_TWO_LINE_BILL_PARSER;
            });

            oneLineBill.setOnClickListener(v -> {
                oneLineBill.setImageResource(R.mipmap.ic_one_line_bill_selected);
                twoLineBill.setImageResource(R.mipmap.ic_two_line_bill);
                REQUESTES_BILL_PARSER = REQUEST_ONE_LINE_BILL_PARSER;
            });
        }

        void activateStartOcrButton(int color, boolean enabled) {
            startOCRForCroppedImage.setBackgroundColor(color);
            startOCRForCroppedImage.setEnabled(enabled);
        }

        public void switchToAfterPhotoView() {
            twoLineBill.setVisibility(View.GONE);
            oneLineBill.setVisibility(View.GONE);
            cropImageView.setVisibility(View.VISIBLE);
        }
    }
}