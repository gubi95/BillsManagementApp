package pwr.billsmanagement.db.data;

import java.util.ArrayList;

/**
 * Created by E6520 on 2017-05-20.
 */

public class BillEntries {

    public String TABLE_BILLENTRIES;
    public String COLUMN_BILLENTRYID ;
    public String COLUMN_PRODUCTNAME;
    public String COLUMN_PRICE ;
    public String COLUMN_QUANTITY ;
    public String COLUMN_BILL_BILLID;
    public String COLUMN_CATEGORY_PRODUCTCATEGORYID ;
    public ArrayList<String> LIST_OF_ENTRIES;

    public BillEntries() {
        TABLE_BILLENTRIES = "BillEntries";
        COLUMN_BILLENTRYID = "BillEntryID";
        COLUMN_PRODUCTNAME = "ProductName";
        COLUMN_PRICE = "Price";
        COLUMN_QUANTITY = "Quantity";
        COLUMN_BILL_BILLID = "Bill_BillID";
        COLUMN_CATEGORY_PRODUCTCATEGORYID = "Category_ProductCategoryID";
        LIST_OF_ENTRIES=new ArrayList<>();
    }

    public ArrayList makeList(){
        LIST_OF_ENTRIES.add(COLUMN_BILLENTRYID);
        LIST_OF_ENTRIES.add(COLUMN_PRODUCTNAME);
        LIST_OF_ENTRIES.add(COLUMN_PRICE);
        LIST_OF_ENTRIES.add(COLUMN_QUANTITY);
        LIST_OF_ENTRIES.add(COLUMN_BILL_BILLID);
        LIST_OF_ENTRIES.add(COLUMN_CATEGORY_PRODUCTCATEGORYID);
        return LIST_OF_ENTRIES;
    }

    public String getTABLE_BILLENTRIES() {
        return TABLE_BILLENTRIES;
    }

    public void setTABLE_BILLENTRIES(String TABLE_BILLENTRIES) {
        this.TABLE_BILLENTRIES = TABLE_BILLENTRIES;
    }

    public String getCOLUMN_BILLENTRYID() {
        return COLUMN_BILLENTRYID;
    }

    public void setCOLUMN_BILLENTRYID(String COLUMN_BILLENTRYID) {
        this.COLUMN_BILLENTRYID = COLUMN_BILLENTRYID;
    }

    public String getCOLUMN_PRODUCTNAME() {
        return COLUMN_PRODUCTNAME;
    }

    public void setCOLUMN_PRODUCTNAME(String COLUMN_PRODUCTNAME) {
        this.COLUMN_PRODUCTNAME = COLUMN_PRODUCTNAME;
    }

    public String getCOLUMN_PRICE() {
        return COLUMN_PRICE;
    }

    public void setCOLUMN_PRICE(String COLUMN_PRICE) {
        this.COLUMN_PRICE = COLUMN_PRICE;
    }

    public String getCOLUMN_QUANTITY() {
        return COLUMN_QUANTITY;
    }

    public void setCOLUMN_QUANTITY(String COLUMN_QUANTITY) {
        this.COLUMN_QUANTITY = COLUMN_QUANTITY;
    }

    public String getCOLUMN_BILL_BILLID() {
        return COLUMN_BILL_BILLID;
    }

    public void setCOLUMN_BILL_BILLID(String COLUMN_BILL_BILLID) {
        this.COLUMN_BILL_BILLID = COLUMN_BILL_BILLID;
    }

    public String getCOLUMN_CATEGORY_PRODUCTCATEGORYID() {
        return COLUMN_CATEGORY_PRODUCTCATEGORYID;
    }

    public void setCOLUMN_CATEGORY_PRODUCTCATEGORYID(String COLUMN_CATEGORY_PRODUCTCATEGORYID) {
        this.COLUMN_CATEGORY_PRODUCTCATEGORYID = COLUMN_CATEGORY_PRODUCTCATEGORYID;
    }
}
