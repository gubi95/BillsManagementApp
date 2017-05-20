package pwr.billsmanagement.localDatabase.dataObjects;

/**
 * Created by E6520 on 2017-05-20.
 */

public class Shops {
    public String COLUMN_SHOPID;
    public String COLUMN_SHOPNAME;
    public String COLUMN_USEROWNER_USERID;

    public Shops() {
        COLUMN_SHOPID = "ShopID";
        COLUMN_SHOPNAME = "ShopName";
        COLUMN_USEROWNER_USERID = "UserOwner_UserID";
    }

    public String getCOLUMN_SHOPID() {
        return COLUMN_SHOPID;
    }

    public void setCOLUMN_SHOPID(String COLUMN_SHOPID) {
        this.COLUMN_SHOPID = COLUMN_SHOPID;
    }

    public String getCOLUMN_SHOPNAME() {
        return COLUMN_SHOPNAME;
    }

    public void setCOLUMN_SHOPNAME(String COLUMN_SHOPNAME) {
        this.COLUMN_SHOPNAME = COLUMN_SHOPNAME;
    }

    public String getCOLUMN_USEROWNER_USERID() {
        return COLUMN_USEROWNER_USERID;
    }

    public void setCOLUMN_USEROWNER_USERID(String COLUMN_USEROWNER_USERID) {
        this.COLUMN_USEROWNER_USERID = COLUMN_USEROWNER_USERID;
    }
}

