package pwr.billsmanagement.localDatabase.creationDatabase;

import static pwr.billsmanagement.localDatabase.creationDatabase.CreateUsers.COLUMN_USERID;
import static pwr.billsmanagement.localDatabase.creationDatabase.CreateUsers.TABLE_USERS;

/**
 * Created by E6520 on 2017-05-16.
 */

public class CreateShops {

    public static final String TABLE_SHOPS = "Shops";
    public static final String COLUMN_SHOPID = "ShopID";
    public static final String COLUMN_SHOPNAME = "ShopName";
    public static final String COLUMN_USEROWNER_USERID = "UserOwner_UserID";

    public CreateShops() {
    }

    public String getTableShops(){
        return "CREATE TABLE " + TABLE_SHOPS + "("
                + COLUMN_SHOPID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SHOPNAME + " NVARCHER(100), "
                + COLUMN_USEROWNER_USERID + " INTEGER,"
                + "FOREIGN KEY("+COLUMN_USEROWNER_USERID+") REFERENCED "+TABLE_USERS+"("+COLUMN_USERID+")"
                +")";
    }

}
