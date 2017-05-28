package pwr.billsmanagement.db.creators;

import static pwr.billsmanagement.db.creators.CreateUsers.COLUMN_USERID;
import static pwr.billsmanagement.db.creators.CreateUsers.TABLE_USERS;

/**
 * Created by E6520 on 2017-05-16.
 */

public class CreateShops {

    public static final String TABLE_SHOPS = "Shops";
    public static final String COLUMN_SHOPID = "_id";
    public static final String COLUMN_SHOPNAME = "ShopName";
    public static final String COLUMN_USEROWNER_USERID = "UserOwner_UserID";

    public CreateShops() {
    }

    public String getTableShops(){
        return "CREATE TABLE " + TABLE_SHOPS + "("
                + COLUMN_SHOPID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SHOPNAME + " NVARCHER(100) UNIQUE, "
                + COLUMN_USEROWNER_USERID + " INTEGER,"
                + " FOREIGN KEY("+COLUMN_USEROWNER_USERID+") REFERENCES "+TABLE_USERS+"("+COLUMN_USERID+"),"
                + " UNIQUE("+COLUMN_SHOPID+","+COLUMN_SHOPNAME+") ON CONFLICT REPLACE"
                +")";
    }

    // Przykladowe dane
    public String addSampleData() {
        return "INSERT INTO " + TABLE_SHOPS + " (ShopName, UserOwner_UserID) VALUES ('Kaufland', 2)";
    }

    public String addSampleData2() {
        return "INSERT INTO " + TABLE_SHOPS + " (ShopName, UserOwner_UserID) VALUES ('Lidl', 3)";
    }

    public String addSampleData3() {
        return "INSERT INTO " + TABLE_SHOPS + " (ShopName, UserOwner_UserID) VALUES ('Żabka', 4)";
    }

}
