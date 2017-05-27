package pwr.billsmanagement.ocr.parsers;

/**
 * Created by Squier on 12.04.2017.
 */
public class OcrProduct {

    private String name;
    private String price;

    public OcrProduct() {
    }

    public OcrProduct(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OcrProduct{\n" +
                "\n\tname='" + name + '\'' +
                "\n\tprice='" + price + '\'' +
                "\n}";
    }

}
