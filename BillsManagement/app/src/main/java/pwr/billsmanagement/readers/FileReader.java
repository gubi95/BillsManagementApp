package pwr.billsmanagement.readers;

import android.content.Context;
import android.content.res.AssetManager;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Squier on 15.04.2017.
 */
public class FileReader {

    private Context context;

    public FileReader(Context context) {
        this.context = context;
    }

    public Set<String> readFileAsSet(String fileName) {
        Set<String> fileAsSet = new HashSet<>();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = reader.readLine()) != null) {
                fileAsSet.addAll(Arrays.asList(line.split(" ")));
            }

            Logger.i("Successfully converted file " + fileName + " to set");
            return fileAsSet;
        } catch (IOException e) {
            Logger.e(e, "Failed to convert file " + fileName + " to set");
        }
        return null;
    }
}
