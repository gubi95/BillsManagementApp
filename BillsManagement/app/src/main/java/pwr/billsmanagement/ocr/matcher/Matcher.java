package pwr.billsmanagement.ocr.matcher;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
                    (ArrayList<MatchProduct>) findMatches(
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
                    (ArrayList<MatchProduct>) findMatches(
                            (ArrayList<String>) intersectionByLength(result), result)));
        }
        Logger.i("Matched external ocr result.");
        return matches;
    }

    private List<MatchProduct> findMatches(final ArrayList<String> sameLengthDict, final String ocrResult) {
        List<MatchProduct> bestMatches = new ArrayList<>();

        for (String word : sameLengthDict) {
            int match = 0;
            if(word.length()==ocrResult.length()) {
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == ocrResult.charAt(i)) match++;
                }
                bestMatches.add(new MatchProduct(ocrResult, word, match));
            }
            else {
                String shorter = ocrResult.length() > word.length() ? word : ocrResult;
                String longer = ocrResult.length() > word.length() ? ocrResult : word;
                int diff = longer.length() - shorter.length();
                ArrayList<Integer> matches = new ArrayList<>();

                for (int i = 0; i <= diff; i++) {
                    matches.add(0);
                }

                for (int i = 0; i <= diff; i++) {
                    for (int j = 0; j < shorter.length(); j++) {
                        if(shorter.charAt(j) == longer.charAt(j)) {
                            matches.set(i, matches.get(i)+1);
                        }
                    }
                    shorter = " " + shorter;
                }

                bestMatches.add(new MatchProduct(ocrResult, word, Collections.max(matches)));
            }

        }

        Logger.i("Found matches [" + bestMatches.size() + "] in dictionary to word " + ocrResult);
        return bestMatches;
    }

    private List<String> intersectionByLength(final String ocrResult) {
        List<String> shortDict = new ArrayList<>();
        for (String dictWord : dictionary) {
            if (dictWord.length() >= ocrResult.length() - MARGIN && dictWord.length() <= ocrResult.length() + MARGIN){
                shortDict.add(dictWord);
            }
        }

        Logger.i("Intersected dictionary by length " + ocrResult.length() + ".\nFounded " + shortDict.size() + " length matches.");
        return shortDict;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public List<String> getOcrResult() {
        return ocrResult;
    }

    public void setOcrResult(List<String> ocrResult) {
        this.ocrResult = ocrResult;
    }
}
