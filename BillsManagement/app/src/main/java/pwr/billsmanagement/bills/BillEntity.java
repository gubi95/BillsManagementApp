package pwr.billsmanagement.bills;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.products.FinalProduct;

/**
 * Created by Squier on 23.05.2017.
 */

public class BillEntity {

    private String shopName;
    private ArrayList<FinalProduct> products;

    public BillEntity(String shopName, ArrayList<FinalProduct> products) {
        this.shopName = shopName;
        this.products = products;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ArrayList<FinalProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<FinalProduct> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("BillEntity{shopName='" + shopName + "\' products:");
        for (FinalProduct product :
                products) {
            builder.append("'");
            builder.append(product.getName());
            builder.append("',");
        }
        builder.append("'}");
        return builder.toString();
    }
}