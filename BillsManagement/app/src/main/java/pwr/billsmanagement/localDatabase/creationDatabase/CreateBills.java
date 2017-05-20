package pwr.billsmanagement.localDatabase.creationDatabase;

import static pwr.billsmanagement.localDatabase.creationDatabase.CreateShops.COLUMN_SHOPID;
import static pwr.billsmanagement.localDatabase.creationDatabase.CreateShops.TABLE_SHOPS;
import static pwr.billsmanagement.localDatabase.creationDatabase.CreateUsers.COLUMN_USERID;
import static pwr.billsmanagement.localDatabase.creationDatabase.CreateUsers.TABLE_USERS;

/**
 * Created by E6520 on 2017-05-16.
 */

public class CreateBills {

    public static final String TABLE_BILLS = "Bills";
    public static final String COLUMN_BILLID = "BillID";
    public static final String COLUMN_PURCHASEDATE = "PurchaseDate";
    public static final String COLUMN_USER_USERID = "User_UserID";
    public static final String COLUMN_SHOP_SHOPID = "Shop_ShopID";


    public CreateBills() {
    }

    public String getTableBills(){
        return "CREATE TABLE " + TABLE_BILLS + "("
                + COLUMN_BILLID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PURCHASEDATE + " DATE, "
                + COLUMN_USER_USERID + " INTEGER, "
                + COLUMN_SHOP_SHOPID + "INTEGER, "
                + "FOREIGN KEY("+COLUMN_USER_USERID+") REFERENCED "+TABLE_USERS+"("+COLUMN_USERID+") "
                + "FOREIGN KEY("+COLUMN_SHOP_SHOPID+") REFERENCED "+TABLE_SHOPS+"("+COLUMN_SHOPID+") "
                +")";
    }

}
