package pwr.billsmanagement.ocr.matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Squier on 09.04.2017.
 */
public class BestMatchesArray {

    private ArrayList<MatchProduct> bestMatches;

    public BestMatchesArray() {
    }

    public BestMatchesArray(ArrayList<MatchProduct> bestMatches) {
        this.bestMatches = bestMatches;
        if (!bestMatches.isEmpty()) {
            screenOutBestMatches();
        }
    }

    public ArrayList<MatchProduct> getBestMatches() {
        return bestMatches;
    }

    public List<MatchProduct> screenOutBestMatches() {
        Collections.sort(bestMatches);
        ArrayList<MatchProduct> onlyBestMatches = new ArrayList<>();
        final int bestMatch = bestMatches.get(0).getMatch();
        for (MatchProduct product : bestMatches) {
            if (product.getMatch() == bestMatch) {
                onlyBestMatches.add(product);
            }
        }
        bestMatches = onlyBestMatches;
        return onlyBestMatches;
    }
}
