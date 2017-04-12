package pwr.billsmanagement;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import pwr.billsmanagement.ocr.BillsOCR;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsTool;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsToolImpl;

public class MainActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback  {

    private static final String TAG = MainActivity.class.getSimpleName();
    static final int PHOTO_REQUEST_CODE = 1;
    private TessBaseAPI tessBaseApi;
    TextView textView;
    Uri outputFileUri;
    private static final String lang = "pol";
    String result = "empty";
    private RequestPermissionsTool requestTool; //for API >=23 only

    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/";
    private static final String TESSDATA = "tessdata";

    private BillsOCR billsOCR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button captureImg = (Button) findViewById(R.id.shootPhoto);
        if (captureImg != null) {
            captureImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    billsOCR = new BillsOCR(getAssets());
                    final Intent takePhotoIntent = billsOCR.startCameraActivity();
                    startActivityForResult(takePhotoIntent, PHOTO_REQUEST_CODE);
                }
            });
        }
        textView = (TextView) findViewById(R.id.textResult);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        //making photo
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String result = billsOCR.doOCR();
            textView.setText(result);
            ArrayList<String> products = new ArrayList<>();
            ArrayList<String> prices = new ArrayList<>();

            for(String line : result.split("\n")) {
                StringBuilder removedErrors = new StringBuilder();
                for(String word : line.split(" ")) {
                    if(word.length() > 2) {
                        removedErrors.append(word + " ");
                    }
                }

                String noErrorsLine = removedErrors.toString();

                int numbers = 0;
                int size = 0;
                for(int i = 0; i < noErrorsLine.length(); i++) {
                    if(noErrorsLine.charAt(i) >= 48 && noErrorsLine.charAt(i) <= 57) {
                        numbers++;
                        size++;
                        continue;
                    }
                    if(noErrorsLine.charAt(i) == 44 || noErrorsLine.charAt(i) == 32){
                        size--;
                        continue;
                    }
                    size++;
                }
                if(numbers > size/2) {
                    prices.add(noErrorsLine);
                } else {
                    products.add(noErrorsLine);
                }

            }

            for(int i = 0; i < products.size(); i++) {
                System.out.println(i + ". Produkt: " + products.get(i));
            }
            System.out.println("\n");
            for(int i = 0; i < prices.size(); i++) {
                System.out.println(i + ". Cena: " + prices.get(i));
            }

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
}

