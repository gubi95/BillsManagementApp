package pwr.billsmanagement.db.data;

import pwr.billsmanagement.bills.edition.products.FinalProduct;

/**
 * Created by E6520 on 2017-05-20.
 */

public class ProductCategories {
    public String COLUMN_PRODUCTCATEGORYID;
    public String COLUMN_NAME;
    public String COLUMN_USEROWNERID;
    public String COLUMN_COLOR ;
    public String COLUMN_MONTHBUDGET ;

    public ProductCategories() {
        COLUMN_PRODUCTCATEGORYID = "ProductCategoryID";
        COLUMN_NAME = "Name";
        COLUMN_USEROWNERID = "UserOwnerID";
        COLUMN_COLOR = "Color";
        COLUMN_MONTHBUDGET = "MonthBudget";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ProductCategories{categories:'"+getCOLUMN_PRODUCTCATEGORYID()+"','"+getCOLUMN_NAME()+"','"+getCOLUMN_USEROWNERID()+"','"+getCOLUMN_COLOR()+"','"+getCOLUMN_MONTHBUDGET()+"'");

        return builder.toString();
    }

    public String getCOLUMN_PRODUCTCATEGORYID() {
        return COLUMN_PRODUCTCATEGORYID;
    }

    public void setCOLUMN_PRODUCTCATEGORYID(String COLUMN_PRODUCTCATEGORYID) {
        this.COLUMN_PRODUCTCATEGORYID = COLUMN_PRODUCTCATEGORYID;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getCOLUMN_USEROWNERID() {
        return COLUMN_USEROWNERID;
    }

    public void setCOLUMN_USEROWNERID(String COLUMN_USEROWNERID) {
        this.COLUMN_USEROWNERID = COLUMN_USEROWNERID;
    }

    public String getCOLUMN_COLOR() {
        return COLUMN_COLOR;
    }

    public void setCOLUMN_COLOR(String COLUMN_COLOR) {
        this.COLUMN_COLOR = COLUMN_COLOR;
    }

    public String getCOLUMN_MONTHBUDGET() {
        return COLUMN_MONTHBUDGET;
    }

    public void setCOLUMN_MONTHBUDGET(String COLUMN_MONTHBUDGET) {
        this.COLUMN_MONTHBUDGET = COLUMN_MONTHBUDGET;
    }
}
