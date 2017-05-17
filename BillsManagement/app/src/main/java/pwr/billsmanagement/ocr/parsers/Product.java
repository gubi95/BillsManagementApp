package pwr.billsmanagement.ocr.parsers;

/**
 * Created by Squier on 12.04.2017.
 */

public class Product {

    private String name;
    private String price;

    public Product() {
    }

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{\n" +
                "\n\tname='" + name + '\'' +
                "\n\tprice='" + price + '\'' +
                "\n}";
    }

    public String getName() {
        return name;
    }
    public String getPrice() { return price; }
}
