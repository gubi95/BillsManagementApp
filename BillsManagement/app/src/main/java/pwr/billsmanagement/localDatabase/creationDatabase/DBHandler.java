package pwr.billsmanagement.localDatabase.creationDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import pwr.billsmanagement.localDatabase.dataObjects.BillEntries;
import pwr.billsmanagement.webApp.models.BillEntry;

/**
 * Created by E6520 on 2017-05-16.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BillsDB";

    CreateBillEntries tabBillEntries;
    CreateBills tabBills;
    CreateProductCategories tabProductCategories;
    CreateShops tabShops;
    CreateUsers tabUsers;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        tabBillEntries= new CreateBillEntries();
        tabBills = new CreateBills();
        tabProductCategories = new CreateProductCategories();
        tabShops = new CreateShops();
        tabUsers = new CreateUsers();

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


    public boolean insertion (String bill, String entry, String cat, String price,String name,String quant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateBillEntries.COLUMN_BILL_BILLID, bill);
        contentValues.put(CreateBillEntries.COLUMN_BILLENTRYID, entry);
        contentValues.put(CreateBillEntries.COLUMN_CATEGORY_PRODUCTCATEGORYID, cat);
        contentValues.put(CreateBillEntries.COLUMN_PRICE, price);
        contentValues.put(CreateBillEntries.COLUMN_PRODUCTNAME, name);
        contentValues.put(CreateBillEntries.COLUMN_QUANTITY, quant);
        db.insert(CreateBillEntries.TABLE_BILLENTRIES, null, contentValues);
        return true;
    }

    public Cursor getData(int id,String idTable) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CreateBillEntries.TABLE_BILLENTRIES+" where "+idTable+"="+id+"", null );
        return res;
    }

    public ArrayList<String> getTable(String tableName, String ColumnName) {
        ArrayList<String> array_list = new ArrayList<String>();

        ;
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+tableName, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ColumnName)));
            res.moveToNext();
        }
        return array_list;
    }
}
