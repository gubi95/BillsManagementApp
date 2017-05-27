package pwr.billsmanagement.webApp.models;

/**
 * Created by E6520 on 2017-05-10.
 */

public class ProductCategory {
    public int productCategoryID;
    public String name;

    public int getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(int productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
