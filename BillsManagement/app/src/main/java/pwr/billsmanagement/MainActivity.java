package pwr.billsmanagement;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import pwr.billsmanagement.ocr.BillsOCR;
import pwr.billsmanagement.ocr.matcher.BestMatchesArray;
import pwr.billsmanagement.ocr.matcher.MatchWorker;
import pwr.billsmanagement.ocr.matcher.Matcher;
import pwr.billsmanagement.ocr.matcher.ProductMatch;
import pwr.billsmanagement.ocr.parsers.BillParser;
import pwr.billsmanagement.ocr.parsers.ShopProduct;
import pwr.billsmanagement.ocr.parsers.TwoLineBillParser;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsTool;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsToolImpl;
import pwr.billsmanagement.readers.FileReader;
import pwr.billsmanagement.readers.PropertiesReader;

public class MainActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback  {

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

    private Bitmap croppedBill;
    private Uri billPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init("OCR");

        initView();

        reader = new PropertiesReader(getApplicationContext(), new Properties());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        toolbar.setTitle(getString(R.string.app_name));

        captureImg = (ImageButton) findViewById(R.id.shootPhoto);
        captureImg.setImageResource(R.drawable.ic_take_photo);
        captureImg.setBackgroundColor(ContextCompat.getColor(this, R.color.activeButton));

        startOCRForCroppedImage = (ImageButton) findViewById(R.id.startOCRForCroppedImage);
        startOCRForCroppedImage.setImageResource(R.drawable.ic_start_ocr);
        startOCRForCroppedImage.setBackgroundColor(ContextCompat.getColor(this, R.color.inactiveButton));
        startOCRForCroppedImage.setEnabled(false);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        captureImageSetOnClick();
        startOCRForCroppedImageSetOnClick();
    }

    private void startOCRForCroppedImageSetOnClick() {

        startOCRForCroppedImage.setOnClickListener(v -> {
            croppedBill = cropImageView.getCroppedImage();
            if(croppedBill != null) {
                String result = billsOCR.doOCR(croppedBill);
                BillParser billParser = new TwoLineBillParser(result, "ZABKA", reader.readMyProperties(CONFIG_FILE));
                billParser.setPrices(new ArrayList<>());
                billParser.setProducts(new ArrayList<>());
                ArrayList<ShopProduct> shopProducts = (ArrayList<ShopProduct>) billParser.parseOcrResult();

                String check;
                initializeMatchWorker();
                for (ShopProduct product : shopProducts) {
                    check = "FINAL " + product.getName() + " may be: ";
                    List<BestMatchesArray> bestMatches = matchWorker.doMatch(Arrays.asList(product.getName().split(" ")));
                    for (BestMatchesArray array : bestMatches) {
                        for (ProductMatch productMatch : array.getBestMatches()) {
                            check += productMatch.getMatch() >= productMatch.getName().length()/2 ? productMatch.getName() + " " : "";
                        }
                    }
                    Logger.i(check);
                }
            }
        });

    }

    private void captureImageSetOnClick() {
        if (captureImg != null) {
            captureImg.setOnClickListener(v -> {
                billsOCR = new BillsOCR(getAssets(), reader.readMyProperties(CONFIG_FILE));
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
}

