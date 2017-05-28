package pwr.billsmanagement.db.creators;

import static pwr.billsmanagement.db.creators.CreateShops.COLUMN_SHOPID;
import static pwr.billsmanagement.db.creators.CreateShops.TABLE_SHOPS;
import static pwr.billsmanagement.db.creators.CreateUsers.COLUMN_USERID;
import static pwr.billsmanagement.db.creators.CreateUsers.TABLE_USERS;

/**
 * Created by E6520 on 2017-05-16.
 */

public class CreateBills {

    public static final String TABLE_BILLS = "Bills";
    public static final String COLUMN_BILLID = "_id";
    public static final String COLUMN_PURCHASEDATE = "PurchaseDate";
    public static final String COLUMN_USER_USERID = "User_UserID";
    public static final String COLUMN_SHOP_SHOPID = "Shop_ShopID";


    public CreateBills() {
    }

    public String getTableBills(){
        return "CREATE TABLE " + TABLE_BILLS + "("
                + COLUMN_BILLID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PURCHASEDATE + " DEFAULT CURRENT_DATE, "
                + COLUMN_USER_USERID + " INTEGER, "
                + COLUMN_SHOP_SHOPID + " INTEGER, "
                + " FOREIGN KEY("+COLUMN_USER_USERID+") REFERENCES "+TABLE_USERS+"("+COLUMN_USERID+"), "
                + " FOREIGN KEY("+COLUMN_SHOP_SHOPID+") REFERENCES "+TABLE_SHOPS+"("+COLUMN_SHOPID+") "
                +")";
    }


    // Przykladowe dane
    public String addSampleData() {
        return "INSERT INTO " + TABLE_BILLS + " (PurchaseDate, User_UserID, Shop_ShopID) VALUES ('2017-05-10', 2, 1)";
    }

    public String addSampleData2() {
        return "INSERT INTO " + TABLE_BILLS + " (PurchaseDate, User_UserID, Shop_ShopID) VALUES ('2016-06-30', 10, 2)";
    }

    public String addSampleData3() {
        return "INSERT INTO " + TABLE_BILLS + " (PurchaseDate, User_UserID, Shop_ShopID) VALUES ('2017-05-27', 2, 3)";
    }

}
