package pwr.billsmanagement.db.data;

/**
 * Created by E6520 on 2017-05-20.
 */

public class Bills {

    public String COLUMN_BILLID ;
    public String COLUMN_PURCHASEDATE;
    public String COLUMN_USER_USERID ;
    public String COLUMN_SHOP_SHOPID ;

    public Bills() {

        COLUMN_BILLID = "BillID";
        COLUMN_PURCHASEDATE = "PurchaseDate";
        COLUMN_USER_USERID = "User_UserID";
        COLUMN_SHOP_SHOPID = "Shop_ShopID";

    }

    public String getCOLUMN_BILLID() {
        return COLUMN_BILLID;
    }

    public void setCOLUMN_BILLID(String COLUMN_BILLID) {
        this.COLUMN_BILLID = COLUMN_BILLID;
    }

    public String getCOLUMN_PURCHASEDATE() {
        return COLUMN_PURCHASEDATE;
    }

    public void setCOLUMN_PURCHASEDATE(String COLUMN_PURCHASEDATE) {
        this.COLUMN_PURCHASEDATE = COLUMN_PURCHASEDATE;
    }

    public String getCOLUMN_USER_USERID() {
        return COLUMN_USER_USERID;
    }

    public void setCOLUMN_USER_USERID(String COLUMN_USER_USERID) {
        this.COLUMN_USER_USERID = COLUMN_USER_USERID;
    }

    public String getCOLUMN_SHOP_SHOPID() {
        return COLUMN_SHOP_SHOPID;
    }

    public void setCOLUMN_SHOP_SHOPID(String COLUMN_SHOP_SHOPID) {
        this.COLUMN_SHOP_SHOPID = COLUMN_SHOP_SHOPID;
    }
}
