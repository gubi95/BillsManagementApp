package pwr.billsmanagement.localDatabase.creationDatabase;

import static pwr.billsmanagement.localDatabase.creationDatabase.CreateUsers.COLUMN_USERID;
import static pwr.billsmanagement.localDatabase.creationDatabase.CreateUsers.TABLE_USERS;

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
                + COLUMN_NAME + " NVARCHER(100), "
                + COLUMN_USEROWNERID + " INTEGER,"
                + COLUMN_COLOR + " NVARCHAR(MAX),"
                + COLUMN_MONTHBUDGET + " DECIMAL,"
                + "FOREIGN KEY("+COLUMN_USEROWNERID+") REFERENCED "+TABLE_USERS+"("+COLUMN_USERID+")"
                +")";
    }

}
