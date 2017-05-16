package pwr.billsmanagement.LocalDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by E6520 on 2017-05-16.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BillsDB";

    BillEntriesDAO tabBillEntries;
    BillsDAO tabBills;
    ProductCategoriesDAO tabProductCategories;
    ShopsDAO tabShops;
    UsersDAO tabUsers;


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        tabBillEntries= new BillEntriesDAO();
        tabBills = new BillsDAO();
        tabProductCategories = new ProductCategoriesDAO();
        tabShops = new ShopsDAO();
        tabUsers = new UsersDAO();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(tabBillEntries.getTableBillEntries());
        db.execSQL(tabBills.getTableBills());
        db.execSQL(tabProductCategories.getTableProductCategories());
        db.execSQL(tabShops.getTableShops());
        db.execSQL(tabUsers.getTableUsers());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
