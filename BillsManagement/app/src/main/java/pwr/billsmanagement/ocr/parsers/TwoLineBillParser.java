package pwr.billsmanagement.ocr.parsers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Squier on 12.04.2017.
 */

public class TwoLineBillParser extends BillParser {

    private static final int SHORTEST_WORD = 3;

    public TwoLineBillParser(String ocrResult, String SHOP_NAME, List<String> products, List<String> prices) {
        super(ocrResult, SHOP_NAME, products, prices);
    }

    @Override
    public List<ShopProduct> parseOcrResult(final int wordLength) {
        List<ShopProduct> parsedProducts = new ArrayList<>();

        for (String line : OCR_RESULT.split("\n")) {
            String filteredLine = filterByLength(line, wordLength);
            extractProducts(filteredLine, products, prices);
        }

        int shorterArray = products.size() <= prices.size() ? products.size() : prices.size();
        for (int i = 0; i < shorterArray; i++) {
            parsedProducts.add(new ShopProduct(SHOP_NAME, products.get(i), prices.get(i)));
        }

        return parsedProducts;
    }

    private void extractProducts(String filteredLine, List<String> names, List<String> prices) {
        if(lineIsPrice(filteredLine)) prices.add(filteredLine);
        else if (filteredLine.length() > SHORTEST_WORD) names.add(filteredLine);
    }

    @Override
    public String filterByLength(final String line, final int wordLength) {
        StringBuilder filteredLine = new StringBuilder();
        for (String word : line.split(" ")) {
            filteredLine.append(
                    word.length() > wordLength ? word + " " : ""
            );
        }
        return filteredLine.toString().trim();
    }

    @Override
    public boolean lineIsPrice(final String line) {
        int number = 0;
        for (int i = 0; i < line.length(); i++) {
            number += (line.charAt(i) <= 57 && line.charAt(i) >= 48) ? 1 : 0;
        }
        return (number > line.replaceAll(",", "").replaceAll(" ", "").length()/2);
    }
}
