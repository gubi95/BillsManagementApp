package pwr.billsmanagement.webApp.models;

import java.text.DecimalFormat;

/**
 * Created by E6520 on 2017-05-10.
 */

public class BillEntry {
    public int billEntryID;
    public String productName;
    public DecimalFormat price;
    public double quantity;

    public int getBillEntryID() {
        return billEntryID;
    }

    public void setBillEntryID(int billEntryID) {
        this.billEntryID = billEntryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public DecimalFormat getPrice() {
        return price;
    }

    public void setPrice(DecimalFormat price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductCategory category;
}
