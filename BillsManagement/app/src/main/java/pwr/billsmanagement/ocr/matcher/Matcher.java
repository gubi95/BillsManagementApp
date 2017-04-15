package pwr.billsmanagement.ocr.matcher;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Squier on 11.04.2017.
 */
public class Matcher {

    private final int MARGIN;

    private Set<String> dictionary;
    private List<String> ocrResult;

    public Matcher(Properties config) {
        MARGIN = Integer.parseInt(config.getProperty("intersect_by_length_margin"));
    }

    public Matcher(Set<String> dictionary, List<String> ocrResult, Properties config) {
        MARGIN = Integer.parseInt(config.getProperty("intersect_by_length_margin"));
        this.dictionary = dictionary;
        this.ocrResult = ocrResult;
    }

    public ArrayList<BestMatchesArray> matchDefaultOcrResult() {
        ArrayList<BestMatchesArray> matches = new ArrayList<>();
        for (String result : ocrResult) {
            matches.add(new BestMatchesArray(
                    (ArrayList<ProductMatch>) findMatches(
                            (ArrayList<String>) intersectionByLength(result), result)));
        }
        Logger.i("Matched default ocr result.");
        return matches;
    }

    public ArrayList<BestMatchesArray> matchExternalOcrResult(final List<String> ocrResult) {
        ArrayList<BestMatchesArray> matches = new ArrayList<>();
        Logger.i("Matching words...");
        for (String result : ocrResult) {
            matches.add(new BestMatchesArray(
                    (ArrayList<ProductMatch>) findMatches(
                            (ArrayList<String>) intersectionByLength(result), result)));
        }
        Logger.wtf("Matched external ocr result.");
        return matches;
    }

    private List<ProductMatch> findMatches(final ArrayList<String> sameLengthDict, final String ocrResult) {
        // TODO jak już zrobisz intersectByLegth to wtedy to trzeba poprawić żeby jakoś sensownie dopasowaywał literki
        List<ProductMatch> bestMatches = new ArrayList<>();

        for (String word : sameLengthDict) {
            int match = 0;
            for (int i = 0; i < word.length(); i++) {
                if(word.charAt(i) == ocrResult.charAt(i)) match++;
            }
            bestMatches.add(new ProductMatch(ocrResult, word, match));
        }

        Logger.i("Found matches [" + bestMatches.size() + "] in dictionary to word " + ocrResult);
        return bestMatches;
    }

    private List<String> intersectionByLength(final String ocrResult) {
        // TODO trzeba dodać jakiś margines z tą długością, żeby dobierał też słowa o MARGIN krósze lub dłuższe, w configu jest wartość
        List<String> shortDict = new ArrayList<>();
        for (String dictWord : dictionary) {
            if (dictWord.length() == ocrResult.length()) {
                shortDict.add(dictWord);
            }
        }

        Logger.i("Intersected dictionary by length " + ocrResult.length());
        return shortDict;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
        Logger.i("Dictionary set.");
    }

    public List<String> getOcrResult() {
        return ocrResult;
    }

    public void setOcrResult(List<String> ocrResult) {
        this.ocrResult = ocrResult;
    }
}
