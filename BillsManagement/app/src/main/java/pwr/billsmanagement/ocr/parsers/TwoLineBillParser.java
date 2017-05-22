package pwr.billsmanagement.ocr.parsers;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Squier on 12.04.2017.
 */

public class TwoLineBillParser extends BillParser {

    private static final String TAG = TwoLineBillParser.class.getSimpleName();
    private static final int NINE = 57;
    private static final int ZERO = 48;
    private static int LONGEST_ACCEPTABLE_PRICE;
    private final int SHORTEST_WORD;
    private final int NUMBERS_PERCENT;

    public TwoLineBillParser(String ocrResult, String SHOP_NAME, List<String> products, List<String> prices, Properties config) {
        super(ocrResult, SHOP_NAME, products, prices);
        SHORTEST_WORD = Integer.parseInt(config.getProperty("shortest_word"));
        NUMBERS_PERCENT = Integer.parseInt(config.getProperty("numbers_percent"));
        LONGEST_ACCEPTABLE_PRICE = Integer.parseInt(config.getProperty("longest_acceptable_price"));
    }

    public TwoLineBillParser(String OCR_RESULT, String SHOP_NAME, Properties config) {
        super(OCR_RESULT, SHOP_NAME);
        SHORTEST_WORD = Integer.parseInt(config.getProperty("shortest_word"));
        NUMBERS_PERCENT = Integer.parseInt(config.getProperty("numbers_percent"));
        LONGEST_ACCEPTABLE_PRICE = Integer.parseInt(config.getProperty("longest_acceptable_price"));
        Logger.i(TAG + ": Numbers: " +  NUMBERS_PERCENT + " shortest word: " + SHORTEST_WORD);
    }

    @Override
    public List<OcrProduct> parseOcrResult() {
        List<OcrProduct> parsedOcrProducts = new ArrayList<>();
        Logger.i("Parsing ocr bill...");
        for (String line : ocrResult.split("\n")) {
            String filteredLine = filterByLength(line, SHORTEST_WORD);
            extractProducts(filteredLine, products, prices);
        }

        int shorterArray = products.size() <= prices.size() ? products.size() : prices.size();
        for (int i = 0; i < shorterArray; i++) {
            parsedOcrProducts.add(new OcrProduct(products.get(i), prices.get(i)));
        }
        Logger.i("Ocr bill parsed.");
        return parsedOcrProducts;
    }

    private void extractProducts(String filteredLine, List<String> names, List<String> prices) {
        Logger.i("Extracting products...");
        if(lineIsPrice(filteredLine)) {
            prices.add(filteredLine);
            Logger.i("Founded price: " + filteredLine);
        }
        else if (filteredLine.length() > SHORTEST_WORD) {
            names.add(filteredLine);
            Logger.i("Founded name: " + filteredLine);
        }
    }

    @Override
    public String filterByLength(final String line, final int wordLength) {
        Logger.i("Filtering word in line by length: " + wordLength);
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
        int words = 0;
        int prices = 0;
        int numbersInWord = 0;
        for (String retval: line.split(" ")) {
            for (int i = 0; i < retval.length(); i++) {
                numbersInWord += (retval.charAt(i) <= NINE && retval.charAt(i) >= ZERO) ? 1 : 0;
            }
            if(numbersInWord <= (retval.length()/2) && numbersInWord < LONGEST_ACCEPTABLE_PRICE) {
                words++;
            } else {
                numbersInWord = 0;
                prices++;
            }
        }

        prices = prices > 0 ? prices : 1;
        words = words > 0 ? words : 1;

        return prices/words  > NUMBERS_PERCENT/100;
    }
}
