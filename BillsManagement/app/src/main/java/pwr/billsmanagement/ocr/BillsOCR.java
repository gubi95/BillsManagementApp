package pwr.billsmanagement.ocr;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Squier on 12.04.2017.
 */

public class BillsOCR {

    public final String CHAR_WHITE_LIST;
    public final String DATA_PATH;
    public final String IMG_PATH;
    public final String TESSERACT_TRAINED_DATA_LOCATION;
    public final String LANG;
    public final String CAPTURE_AS;
    public final String SAVE_CROPPED_AS;

    private Uri outputFileUri;
    private TessBaseAPI tessBaseApi;
    private AssetManager assets;
    private Context context;

    public BillsOCR(AssetManager assets, Properties config, Context context) {
        this.assets = assets;
        this.context = context;
        DATA_PATH = Environment.getExternalStorageDirectory().toString() + config.getProperty("data_path");
        IMG_PATH = DATA_PATH + config.getProperty("img_path");
        CHAR_WHITE_LIST = config.getProperty("ocr_char_white_list");
        TESSERACT_TRAINED_DATA_LOCATION = config.getProperty("ocr_trained_data_location");
        LANG = config.getProperty("ocr_lang");
        CAPTURE_AS = config.getProperty("save_captured_img_as");
        SAVE_CROPPED_AS = config.getProperty("save_cropped_img_as");
    }

    public Intent startCameraActivity() {
        try {
            prepareDirectory(IMG_PATH);

            Logger.i("Version: " + Build.VERSION.SDK_INT + " " + context.getPackageName());

            if (Build.VERSION.SDK_INT > 23) {
                outputFileUri = FileProvider.getUriForFile(
                        context, context.getPackageName() + ".provider", new File(IMG_PATH + CAPTURE_AS)
                );
            } else {
                outputFileUri = Uri.fromFile(new File(IMG_PATH + CAPTURE_AS));
            }

            return new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void prepareDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Logger.e("ERROR: Creation of directory " + path + " failed, check does Android Manifest have permission to write to external storage.");
            }
        } else {
            Logger.i("Created directory " + path);
        }
    }

    public String doOCR(Bitmap croppedImage) {
        prepareTesseract();
        return startOCR(croppedImage);
    }

    public String doOCR(Uri fileUri) {
        prepareTesseract();
        return startOCR(fileUri);
    }

    public String doOCR() {
        prepareTesseract();
        return startOCR(outputFileUri);
    }

    private void prepareTesseract() {
        try {
            prepareDirectory(DATA_PATH + TESSERACT_TRAINED_DATA_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyTessDataFiles(TESSERACT_TRAINED_DATA_LOCATION);
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

                    Logger.d("Copied " + fileName + "to tessdata");
                }
            }
        } catch (IOException e) {
            Logger.e("Unable to copy files to tessdata " + e.toString());
        }
    }

    private String startOCR(Bitmap bitmap) {
        return extractText(bitmap);
    }

    private String startOCR(Uri imgUri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.
            Bitmap bitmap = BitmapFactory.decodeFile(imgUri.getPath(), options);

            return extractText(bitmap);

        } catch (Exception e) {
            Logger.e(e.getMessage());
        }

        return null;
    }

    private String extractText(Bitmap bitmap) {
        try {
            tessBaseApi = new TessBaseAPI();
        } catch (Exception e) {
            Logger.e(e.getMessage());
            if (tessBaseApi == null) {
                Logger.e("TessBaseAPI is null. TessFactory not returning tess object.");
            }
        }

        tessBaseApi.init(DATA_PATH, LANG);

//       //EXTRA SETTINGS
//        //For example if we only want to detect numbers
        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, CHAR_WHITE_LIST);

        //blackList Example
        //tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&()_+=-[]}{" +
        //"'\"\\|~`/<>?");

        Logger.d("Training file loaded");
        tessBaseApi.setImage(bitmap);
        String extractedText = "empty result";
        try {
            extractedText = tessBaseApi.getUTF8Text();
        } catch (Exception e) {
            Logger.e("Error in recognizing text.");
        }
        tessBaseApi.end();
        return extractedText;
    }

    public Uri getOutputFileUri() {
        return outputFileUri;
    }

}
