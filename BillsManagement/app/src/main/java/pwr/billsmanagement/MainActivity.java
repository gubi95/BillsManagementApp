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
import android.support.annotation.RequiresApi;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import pwr.billsmanagement.ocr.BillsOCR;
import pwr.billsmanagement.ocr.parsers.BillParser;
import pwr.billsmanagement.ocr.parsers.ShopProduct;
import pwr.billsmanagement.ocr.parsers.TwoLineBillParser;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsTool;
import pwr.billsmanagement.ocr.permissions.RequestPermissionsToolImpl;

public class MainActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback  {

    static final int PHOTO_REQUEST_CODE = 1;
    TextView textView;
    private RequestPermissionsTool requestTool; //for API >=23 only
    private BillsOCR billsOCR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button captureImg = (Button) findViewById(R.id.shootPhoto);
        if (captureImg != null) {
            captureImg.setOnClickListener(v -> {
                billsOCR = new BillsOCR(getAssets());
                final Intent takePhotoIntent = billsOCR.startCameraActivity();
                startActivityForResult(takePhotoIntent, PHOTO_REQUEST_CODE);
            });
        }
        textView = (TextView) findViewById(R.id.textResult);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //making photo
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String result = billsOCR.doOCR();
            BillParser billParser = new TwoLineBillParser(result, "ZABKA", new ArrayList<>(), new ArrayList<>());
            ArrayList<ShopProduct> shopProducts = (ArrayList<ShopProduct>) billParser.parseOcrResult(2);

            StringBuilder parsedOcr = new StringBuilder();
            for (ShopProduct product : shopProducts) {
                parsedOcr.append(product.toString() +"\n\n");
            }

            textView.setText(parsedOcr.toString());

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

