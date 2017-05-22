package pwr.billsmanagement.bills.edition.products;

/**
 * Created by Squier on 22.05.2017.
 */
public class FinalProduct {

    private String name;
    private String unitPrice;
    private String quantity;
    private String totalPrice;
    private String category;

    public FinalProduct() {
    }

    public FinalProduct(String name, String unitPrice, String quantity, String totalPrice, String category) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
