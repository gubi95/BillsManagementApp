package pwr.billsmanagement.LocalDatabase;

/**
 * Created by E6520 on 2017-05-16.
 */

public class ProductCategoriesDAO {

    public static final String TABLE_PRODUCTCATEGORIES = "ProductCategories";
    public static final String COLUMN_PRODUCTCATEGORYID = "ProductCategoryID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_USEROWNERID = "UserOwnerID";

    public ProductCategoriesDAO() {
    }

    public String getTableProductCategories(){
        return "CREATE TABLE " + TABLE_PRODUCTCATEGORIES + "("
                + COLUMN_PRODUCTCATEGORYID + " INT, "
                + COLUMN_NAME + " NVARCHER(100), "
                + COLUMN_USEROWNERID + " INT"
                +")";
    }

}
