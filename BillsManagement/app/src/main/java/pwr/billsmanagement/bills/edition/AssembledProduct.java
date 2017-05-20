package pwr.billsmanagement.bills.edition;

/**
 * Created by Squier on 17.05.2017.
 */

public class AssembledProduct {

    private String productName;
    private String productUnitPrice;
    private String productTotalPrice;

    public AssembledProduct(String productName, String productUnitPrice, String productTotalPrice) {
        this.productName = productName;
        this.productUnitPrice = productUnitPrice;
        this.productTotalPrice = productTotalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(String productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    @Override
    public String toString() {
        return "AssembledProduct{" +
                "productName='" + productName + '\'' +
                ", productUnitPrice='" + productUnitPrice + '\'' +
                ", productTotalPrice='" + productTotalPrice + '\'' +
                '}';
    }
}
