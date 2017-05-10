package pwr.billsmanagement.ocr.parsers;

/**
 * Created by Squier on 12.04.2017.
 */

public class ShopProduct {

    private String shop;
    private String name;
    private String price;

    public ShopProduct() {
    }

    public ShopProduct(String shop, String name, String price) {
        this.shop = shop;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShopProduct{\n" +
                "\tshop='" + shop + '\'' +
                "\n\tname='" + name + '\'' +
                "\n\tprice='" + price + '\'' +
                "\n}";
    }

    public String getName() {
        return name;
    }
    public String getPrice() { return price; }
}
