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
    private final int SHORTEST_WORD;
    private final int NUMBERS_PERCENT;

    public TwoLineBillParser(String ocrResult, String SHOP_NAME, List<String> products, List<String> prices, Properties config) {
        super(ocrResult, SHOP_NAME, products, prices);
        SHORTEST_WORD = Integer.parseInt(config.getProperty("shortest_word"));
        NUMBERS_PERCENT = Integer.parseInt(config.getProperty("numbers_percent"));
    }

    public TwoLineBillParser(String OCR_RESULT, String SHOP_NAME, Properties config) {
        super(OCR_RESULT, SHOP_NAME);
        SHORTEST_WORD = Integer.parseInt(config.getProperty("shortest_word"));
        NUMBERS_PERCENT = Integer.parseInt(config.getProperty("numbers_percent"));
        Logger.i(TAG + ": Numbers: " +  NUMBERS_PERCENT + " shortest word: " + SHORTEST_WORD);
    }

    @Override
    public List<ShopProduct> parseOcrResult() {
        List<ShopProduct> parsedProducts = new ArrayList<>();
        Logger.i("Parsing ocr bill...");
        for (String line : OCR_RESULT.split("\n")) {
            String filteredLine = filterByLength(line, SHORTEST_WORD);
            extractProducts(filteredLine, products, prices);
        }

        int shorterArray = products.size() <= prices.size() ? products.size() : prices.size();
        for (int i = 0; i < shorterArray; i++) {
            parsedProducts.add(new ShopProduct(SHOP_NAME, products.get(i), prices.get(i)));
        }
        Logger.i("Ocr bill parsed.");
        return parsedProducts;
    }

    private void extractProducts(String filteredLine, List<String> names, List<String> prices) {
        Logger.i("Extracting products...");
        if(lineIsPrice(filteredLine)) {
            prices.add(filteredLine);
            Logger.i("Founded price: " + filteredLine);
        }
        else if (filteredLine.length() > SHORTEST_WORD) {   //TODO kurwa po co sprawdzenie robie? Nie po to, żeby przypadkiem nie czytać, jeśli linia jest krótsza od najdłuższego słowa w słowniku?
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
        /*TODO Zdażają się sytuacje że błędnie przypisuje linię do ceny popatrz na przykład niżej
            SEREK 444111041 JOVI, niestety zostanie przypisana do ceny choć ewidentnie jest to nazwa
            produktu, zostanie przypisany do ceny bo stosunek ilości cyfr do całkowitej ilości znaków
            (bez przecinków i spacji) to 9/18. W config.prperties można se pozmieniać wartość NUMBERS_PERCENT
            ale chyba lepiej będzie jak takie coś zrobisz.
            W tej klasie patrzymy na całą linię więc wystarczy zrobić splita po spacjach i sprawdzić
            czy liczba jest otoczona słowami. EZ SHIET! jak coś to pytaj


         */
        int word=0;//ilość słów (litery)
        int number = 0; //ogólna ilość słów
        int wordStr = 0;//ilość liter w słowie
        for (String retval: line.split(" ")) {
            for (int i = 0; i < retval.length(); i++) {
                wordStr += (retval.charAt(i) <= NINE && retval.charAt(i) >= ZERO) ? 1 : 0;
            }
            if(wordStr >=(retval.length()/2)) {//jeśli słowo ma więcej liter niż innego syfu to ilość słów +1
                word++;
            }
            wordStr = 0;
            number++;
        }


        return word /number  > NUMBERS_PERCENT/100;//ilość słów z literami przez ilość słów

        /*
         int number = 0;
        for (int i = 0; i < line.length(); i++) {
            number += (line.charAt(i) <= NINE && line.charAt(i) >= ZERO) ? 1 : 0;
        }

        return (((float)number) / (line.replaceAll("[,.* ]", "").length())) > NUMBERS_PERCENT/100;
         */
    }
}
