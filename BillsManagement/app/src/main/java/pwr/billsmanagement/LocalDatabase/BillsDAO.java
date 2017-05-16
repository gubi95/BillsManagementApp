package pwr.billsmanagement.LocalDatabase;

/**
 * Created by E6520 on 2017-05-16.
 */

public class BillsDAO {

    public static final String TABLE_BILLS = "Bills";
    public static final String COLUMN_BILLID = "BillID";
    public static final String COLUMN_PURCHASEDATE = "PurchaseDate";
    public static final String COLUMN_USER_USERID = "User_UserID";
    public static final String COLUMN_SHOP_SHOPID = "Shop_ShopID";


    public BillsDAO() {
    }

    public String getTableBills(){
        return "CREATE TABLE " + TABLE_BILLS + "("
                + COLUMN_BILLID + " INT, "
                + COLUMN_PURCHASEDATE + " DATE, "
                + COLUMN_USER_USERID + " INT,"
                + COLUMN_SHOP_SHOPID + "INT"
                +")";
    }

}
