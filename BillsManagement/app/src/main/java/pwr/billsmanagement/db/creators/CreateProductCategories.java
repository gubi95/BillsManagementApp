package pwr.billsmanagement.db.creators;

import static pwr.billsmanagement.db.creators.CreateUsers.COLUMN_USERID;
import static pwr.billsmanagement.db.creators.CreateUsers.TABLE_USERS;

/**
 * Created by E6520 on 2017-05-16.
 */

public class CreateProductCategories {

    public static final String TABLE_PRODUCTCATEGORIES = "ProductCategories";
    public static final String COLUMN_PRODUCTCATEGORYID = "ProductCategoryID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_USEROWNERID = "UserOwnerID";
    public static final String COLUMN_COLOR = "Color";
    public static final String COLUMN_MONTHBUDGET = "MonthBudget";

    public CreateProductCategories() {
    }

    public String getTableProductCategories(){
        return "CREATE TABLE " + TABLE_PRODUCTCATEGORIES + "("
                + COLUMN_PRODUCTCATEGORYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " NVARCHAR(100), "
                + COLUMN_USEROWNERID + " INTEGER,"
                + COLUMN_COLOR + " NVARCHAR(100),"
                + COLUMN_MONTHBUDGET + " DECIMAL,"
                + "FOREIGN KEY("+COLUMN_USEROWNERID+") REFERENCES "+TABLE_USERS+"("+COLUMN_USERID+")"
                +")";
    }

}
