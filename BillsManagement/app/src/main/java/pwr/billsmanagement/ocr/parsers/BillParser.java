package pwr.billsmanagement.ocr.parsers;

import java.util.List;

/**
 * Created by Squier on 12.04.2017.
 */
public abstract class BillParser {

    protected final String ocrResult;
    protected final String shopName;
    protected List<String> products;
    protected List<String> prices;

    public BillParser(String ocrResult, String shopName) {
        this.ocrResult = ocrResult;
        this.shopName = shopName;
    }

    public BillParser(String ocrResult, String shopName, List<String> products, List<String> prices) {
        this.ocrResult = ocrResult;
        this.shopName = shopName;
        this.products = products;
        this.prices = prices;
    }

    public String getOcrResult() {
        return ocrResult;
    }

    public String getShopName() {
        return shopName;
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

    public abstract List<OcrProduct> parseOcrResult();

    public abstract String filterByLength(final String line, final int wordLength);

    public abstract boolean lineIsPrice(final String word);
}
