package pwr.billsmanagement.ocr;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Squier on 12.04.2017.
 */

public class BillsOCR {

    private static final String TAG = BillsOCR.class.getName();
    private static final String IMG_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/imgs";
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/";
    private static final String TESSDATA = "tessdata";
    private static final String LANG = "pol";

    private Uri outputFileUri;
    private TessBaseAPI tessBaseApi;
    private AssetManager assets;

    public BillsOCR(AssetManager assets) {
        this.assets = assets;
    }

    public Intent startCameraActivity() {
        try {

            prepareDirectory(IMG_PATH);

            String img_path = IMG_PATH + "/ocr.jpg";

            outputFileUri = Uri.fromFile(new File(img_path));

            final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            return takePictureIntent;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    private void prepareDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(TAG, "ERROR: Creation of directory " + path + " failed, check does Android Manifest have permission to write to external storage.");
            }
        } else {
            Log.i(TAG, "Created directory " + path);
        }
    }

    public String doOCR() {
        prepareTesseract();
        return startOCR(outputFileUri);
    }

    private void prepareTesseract() {
        try {
            prepareDirectory(DATA_PATH + TESSDATA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyTessDataFiles(TESSDATA);
    }

    private void copyTessDataFiles(String path) {
        try {
            String fileList[] = assets.list(path);
            for (String fileName : fileList) {
                String pathToDataFile = DATA_PATH + path + "/" + fileName;
                if (!(new File(pathToDataFile)).exists()) {

                    InputStream in = assets.open(path + "/" + fileName);

                    OutputStream out = new FileOutputStream(pathToDataFile);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    Log.d(TAG, "Copied " + fileName + "to tessdata");
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to copy files to tessdata " + e.toString());
        }
    }

    private String startOCR(Uri imgUri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.
            Bitmap bitmap = BitmapFactory.decodeFile(imgUri.getPath(), options);

            return extractText(bitmap);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    private String extractText(Bitmap bitmap) {
        try {
            tessBaseApi = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (tessBaseApi == null) {
                Log.e(TAG, "TessBaseAPI is null. TessFactory not returning tess object.");
            }
        }

        tessBaseApi.init(DATA_PATH, LANG);

//       //EXTRA SETTINGS
//        //For example if we only want to detect numbers
        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,.*");

        //blackList Example
        //tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&()_+=-[]}{" +
        //"'\"\\|~`/<>?");

        Log.d(TAG, "Training file loaded");
        tessBaseApi.setImage(bitmap);
        String extractedText = "empty result";
        try {
            extractedText = tessBaseApi.getUTF8Text();
        } catch (Exception e) {
            Log.e(TAG, "Error in recognizing text.");
        }
        tessBaseApi.end();
        return extractedText;
    }


}
