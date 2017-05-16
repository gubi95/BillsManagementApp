package pwr.billsmanagement.LocalDatabase;

/**
 * Created by E6520 on 2017-05-16.
 */

public class ShopsDAO {

    public static final String TABLE_SHOPS = "Shops";
    public static final String COLUMN_SHOPID = "ShopID";
    public static final String COLUMN_SHOPNAME = "ShopName";
    public static final String COLUMN_USEROWNER_USERID = "UserOwner_UserID";

    public ShopsDAO() {
    }

    public String getTableShops(){
        return "CREATE TABLE " + TABLE_SHOPS + "("
                + COLUMN_SHOPID + " INT, "
                + COLUMN_SHOPNAME + " NVARCHER(100), "
                + COLUMN_USEROWNER_USERID + " INT"
                +")";
    }

}
