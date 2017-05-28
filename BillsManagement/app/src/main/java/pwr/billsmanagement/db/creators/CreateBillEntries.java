package pwr.billsmanagement.db.creators;

import static pwr.billsmanagement.db.creators.CreateBills.COLUMN_BILLID;
import static pwr.billsmanagement.db.creators.CreateBills.TABLE_BILLS;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_PRODUCTCATEGORYID;
import static pwr.billsmanagement.db.creators.CreateProductCategories.TABLE_PRODUCTCATEGORIES;


/**
 * Created by E6520 on 2017-05-16.
 */

public class CreateBillEntries {

    public static final String TABLE_BILLENTRIES = "BillEntries";
    public static final String COLUMN_BILLENTRYID = "BillEntryID";
    public static final String COLUMN_PRODUCTNAME = "ProductName";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_BILL_BILLID = "Bill_BillID";
    public static final String COLUMN_CATEGORY_PRODUCTCATEGORYID = "Category_ProductCategoryID";

    public CreateBillEntries() {
    }

    public String getTableBillEntries() {
        return "CREATE TABLE IF NOT EXISTS" + TABLE_BILLENTRIES + "("
                + COLUMN_BILLENTRYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCTNAME + " NVARCHAR(100), "
                + COLUMN_PRICE + " DECIMAL(18,2), "
                + COLUMN_QUANTITY + " FLOAT, "
                + COLUMN_BILL_BILLID + " INTEGER, "
                + COLUMN_CATEGORY_PRODUCTCATEGORYID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_BILL_BILLID + ") REFERENCES " + TABLE_BILLS + "(" + COLUMN_BILLID + "),"
                + "FOREIGN KEY(" + COLUMN_CATEGORY_PRODUCTCATEGORYID + ") REFERENCES " + TABLE_PRODUCTCATEGORIES + "(" + COLUMN_PRODUCTCATEGORYID + ")"
                + ")";
    }


}
