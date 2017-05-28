package pwr.billsmanagement.readers;

import android.content.Context;
import android.content.res.AssetManager;

import com.orhanobut.logger.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Squier on 15.04.2017.
 */
public class PropertiesReader {

    private Context context;
    private Properties myProperties;

    public PropertiesReader(Context context, Properties myProperties) {
        this.context = context;
        this.myProperties = myProperties;
    }

    public Properties readMyProperties(String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open(fileName);
            myProperties.load(inputStream);
            Logger.i("Successfully loaded \"" + fileName + "\".");
        } catch (IOException e) {
            Logger.e(e, "Failed to load \"" + fileName + "\"!");
        }
        return myProperties;
    }

}
