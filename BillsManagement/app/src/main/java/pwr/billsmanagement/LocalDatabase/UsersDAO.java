package pwr.billsmanagement.LocalDatabase;

/**
 * Created by E6520 on 2017-05-16.
 */

public class UsersDAO {

    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USERID = "UserID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_FIRSTNAME = "FirstName";
    public static final String COLUMN_LASTNAME = "LastName";

    public UsersDAO() {
    }

    public String getTableUsers(){
        return "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USERID + " INT, "
                + COLUMN_USERNAME + " NVARCHER(40), "
                + COLUMN_PASSWORD + " NVARCHAR(300),"
                + COLUMN_EMAIL + "NVARCHAR(100),"
                + COLUMN_FIRSTNAME + "NVARCHAR(100),"
                + COLUMN_LASTNAME + "NVARCHAR(150)"
                +")";
    }

}
