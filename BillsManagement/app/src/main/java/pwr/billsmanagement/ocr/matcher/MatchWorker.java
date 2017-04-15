package pwr.billsmanagement.ocr.matcher;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import pwr.billsmanagement.readers.FileReader;
import pwr.billsmanagement.readers.PropertiesReader;

/**
 * Created by Squier on 11.04.2017.
 */
public class MatchWorker {

    private static final String DICTIONARY_FILEPATH_KEY = "dict_path";

    private Matcher matcher;
    private Properties properties;

    public MatchWorker(Matcher matcher, Properties properties) {
        this.matcher = matcher;
        this.properties = properties;
    }

    public List<BestMatchesArray> doMatch() {
        return matcher.matchDefaultOcrResult();
    }

    public List<BestMatchesArray> doMatch(final List<String> ocrResult) {
        return matcher.matchExternalOcrResult(ocrResult);
    }

    public Set<String> readDictionaryWords(String inAssetsFileName, FileReader reader) {
        return reader.readFileAsSet(inAssetsFileName);
    }

    public MatchWorker initializeMatcherDefaultDictionary(String inAssetsFileName, FileReader reader) {
        matcher.setDictionary(readDictionaryWords(inAssetsFileName, reader));
        return this;
    }

    public MatchWorker initializeMatcherDefaultDictionary(FileReader reader) {
        matcher.setDictionary(readDictionaryWords(properties.getProperty(DICTIONARY_FILEPATH_KEY), reader));
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public MatchWorker setProperties(Properties properties) {
        this.properties = properties;
        return this;
    }
}