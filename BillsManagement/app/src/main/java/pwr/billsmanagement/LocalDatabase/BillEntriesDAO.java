package pwr.billsmanagement.LocalDatabase;

/**
 * Created by E6520 on 2017-05-16.
 */

public class BillEntriesDAO {

    public static final String TABLE_BILLENTRIES = "BillEntries";
    public static final String COLUMN_BILLENTRYID = "BillEntryID";
    public static final String COLUMN_PRODUCTNAME = "ProductName";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_BILLBILLID = "Bill_BillID";
    public static final String COLUMN_CATEGORYPRODUCTCATEGORYID = "Category_ProductCategoryID";

    public BillEntriesDAO() {
    }

    public String getTableBillEntries(){
        return "CREATE TABLE " + TABLE_BILLENTRIES + "("
                + COLUMN_BILLENTRYID + " INT, "
                + COLUMN_PRODUCTNAME + " NVARCHER(100), "
                + COLUMN_PRICE + " DECIMAL(18,2),"
                + COLUMN_QUANTITY + "FLOAT,"
                + COLUMN_BILLBILLID + "INT,"
                + COLUMN_CATEGORYPRODUCTCATEGORYID + "INT"
                +")";
    }
}
