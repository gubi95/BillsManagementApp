package pwr.billsmanagement.ocr.parsers;

import java.util.List;

/**
 * Created by Squier on 12.04.2017.
 */
public abstract class BillParser {

    protected final String OCR_RESULT;
    protected final String SHOP_NAME;
    protected List<String> products;
    protected List<String> prices;

    public BillParser(String OCR_RESULT, String SHOP_NAME) {
        this.OCR_RESULT = OCR_RESULT;
        this.SHOP_NAME = SHOP_NAME;
    }

    public BillParser(String OCR_RESULT, String SHOP_NAME, List<String> products, List<String> prices) {
        this.OCR_RESULT = OCR_RESULT;
        this.SHOP_NAME = SHOP_NAME;
        this.products = products;
        this.prices = prices;
    }

    public String getOCR_RESULT() {
        return OCR_RESULT;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public List<String> getPrices() {
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }

    public abstract List<ShopProduct> parseOcrResult();
    public abstract String filterByLength(final String line, final int wordLength);
    public abstract boolean lineIsPrice(final String word);
}
