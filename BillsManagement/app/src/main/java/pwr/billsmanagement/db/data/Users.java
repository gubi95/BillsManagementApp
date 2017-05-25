package pwr.billsmanagement.db.data;

/**
 * Created by E6520 on 2017-05-20.
 */

public class Users {
    public String COLUMN_USERID ;
    public String COLUMN_USERNAME ;
    public String COLUMN_PASSWORD ;
    public String COLUMN_EMAIL ;
    public String COLUMN_FIRSTNAME ;
    public String COLUMN_LASTNAME ;

    public Users() {
        COLUMN_USERID = "UserID";
        COLUMN_USERNAME = "Username";
        COLUMN_PASSWORD = "Password";
        COLUMN_EMAIL = "Email";
        COLUMN_FIRSTNAME = "FirstName";
        COLUMN_LASTNAME = "LastName";
    }

    public String getCOLUMN_USERID() {
        return COLUMN_USERID;
    }

    public void setCOLUMN_USERID(String COLUMN_USERID) {
        this.COLUMN_USERID = COLUMN_USERID;
    }

    public String getCOLUMN_USERNAME() {
        return COLUMN_USERNAME;
    }

    public void setCOLUMN_USERNAME(String COLUMN_USERNAME) {
        this.COLUMN_USERNAME = COLUMN_USERNAME;
    }

    public String getCOLUMN_PASSWORD() {
        return COLUMN_PASSWORD;
    }

    public void setCOLUMN_PASSWORD(String COLUMN_PASSWORD) {
        this.COLUMN_PASSWORD = COLUMN_PASSWORD;
    }

    public String getCOLUMN_EMAIL() {
        return COLUMN_EMAIL;
    }

    public void setCOLUMN_EMAIL(String COLUMN_EMAIL) {
        this.COLUMN_EMAIL = COLUMN_EMAIL;
    }

    public String getCOLUMN_FIRSTNAME() {
        return COLUMN_FIRSTNAME;
    }

    public void setCOLUMN_FIRSTNAME(String COLUMN_FIRSTNAME) {
        this.COLUMN_FIRSTNAME = COLUMN_FIRSTNAME;
    }

    public String getCOLUMN_LASTNAME() {
        return COLUMN_LASTNAME;
    }

    public void setCOLUMN_LASTNAME(String COLUMN_LASTNAME) {
        this.COLUMN_LASTNAME = COLUMN_LASTNAME;
    }
}
