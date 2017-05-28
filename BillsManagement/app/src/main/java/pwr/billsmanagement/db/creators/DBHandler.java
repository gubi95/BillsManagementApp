package pwr.billsmanagement.db.creators;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import pwr.billsmanagement.bills.BillEntity;
import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.db.data.ProductCategories;
import pwr.billsmanagement.webApp.models.ProductCategory;

import static pwr.billsmanagement.db.creators.CreateBillEntries.*;
import static pwr.billsmanagement.db.creators.CreateBills.TABLE_BILLS;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_COLOR;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_EXTERNALSYSTEMID;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_LASTMODIFIEDDATE;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_MONTHBUDGET;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_NAME;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_PRODUCTCATEGORYID;
import static pwr.billsmanagement.db.creators.CreateProductCategories.COLUMN_USEROWNERID;
import static pwr.billsmanagement.db.creators.CreateProductCategories.TABLE_PRODUCTCATEGORIES;
import static pwr.billsmanagement.db.creators.CreateShops.TABLE_SHOPS;

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
        tabBillEntries = new CreateBillEntries();
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


//ADDS BY ASYNC

    public void insertProductsAsync(BillEntity billEntity) {

        InsertProductAsync task = new InsertProductAsync();
        task.setDbInsert(this.getWritableDatabase());
        task.setDbSelect(this.getReadableDatabase());
        task.execute(billEntity);


    }

    public void insertProductCategoriesAsync(ProductCategories category) {


        InsertProductCategoriesAsync task = new InsertProductCategoriesAsync();
        task.setDbInsert(this.getWritableDatabase());
        task.setDbSelect(this.getReadableDatabase());
        task.execute(category);


    }








    //ASYNC

    private class InsertProductAsync extends AsyncTask<BillEntity, Void, Void> {

        private SQLiteDatabase dbSelect;
        private SQLiteDatabase dbInsert;

        @Override
        protected Void doInBackground(BillEntity... params) {

            for (FinalProduct product : params[0].getProducts()) {

                insertShopByString(dbInsert, params[0].getShopName());
                product.setCategoryID(findCategoryIDByString(dbSelect, product.getCategory()));
                params[0].setShopID(findShopIDByName(dbSelect, params[0].getShopName()));
                insertProductToDB(dbInsert, dbSelect, params[0].getShopID(), params[0].getShopName(), product);


            }


            //Przykład wywołania getTable //ile kolumn się chce
            Cursor curs = getTable(TABLE_SHOPS);
            curs.moveToFirst();
            while (curs.moveToNext()) {
                Log.d("tabela shops", curs.getString(0) + " " + curs.getString(1) + " " + curs.getString(2));

            }


            for (String s : getProductsFromShop(dbSelect, params[0].getShopName())
                    ) {
                Log.d("tabela sklepy", s);
            }

            return null;
        }




        public void setDbSelect(SQLiteDatabase dbSelect) {
            this.dbSelect = dbSelect;
        }

        public void setDbInsert(SQLiteDatabase dbInsert) {
            this.dbInsert = dbInsert;
        }


    }

    private class InsertProductCategoriesAsync extends AsyncTask<ProductCategories, Void, Void> {

        private SQLiteDatabase dbSelect;
        private SQLiteDatabase dbInsert;

        @Override
        protected Void doInBackground(ProductCategories...category) {

            insertProductCategoryToDB(dbInsert,category[0]);

            return null;
        }




        public void setDbSelect(SQLiteDatabase dbSelect) {
            this.dbSelect = dbSelect;
        }

        public void setDbInsert(SQLiteDatabase dbInsert) {
            this.dbInsert = dbInsert;
        }


    }



    //CURSOR

    public Cursor getData(int id, String idTable) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_BILLENTRIES + " where " + idTable + "=" + id + "", null);
        return res;
    }

    public Cursor getTable(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + tableName, null);

        return res;
    }


    public Cursor fetch(){
        SQLiteDatabase dbSelect=this.getReadableDatabase();
        Cursor res= dbSelect.rawQuery("SELECT Bills.BillID ,Bills.PurchaseDate,Shops.ShopName FROM Bills, Shops WHERE Bills.Shop_ShopID=Shops.ShopID",null);

        if(res!=null){
            res.moveToFirst();
        }



        return res;
    }


    //ARRAY

    public ArrayList<String> getProductsFromShop(SQLiteDatabase dbSelect, String shop) {

        String query = "SELECT " + CreateBillEntries.TABLE_BILLENTRIES + "." + CreateBillEntries.COLUMN_PRODUCTNAME + " FROM " + CreateBillEntries.TABLE_BILLENTRIES +
                " LEFT JOIN " + TABLE_BILLS + " on " + TABLE_BILLS + "." + CreateBills.COLUMN_BILLID + "=" + CreateBillEntries.TABLE_BILLENTRIES + "." + CreateBillEntries.COLUMN_BILL_BILLID +
                " LEFT JOIN " + TABLE_SHOPS + " on " + TABLE_SHOPS + "." + CreateShops.COLUMN_SHOPID + "=" + TABLE_BILLS + "." + CreateBills.COLUMN_SHOP_SHOPID +
                " WHERE " + TABLE_SHOPS + "." + CreateShops.COLUMN_SHOPNAME + " LIKE '%" + shop + "%'";

        Cursor res = dbSelect.rawQuery(query, null);
        res.moveToFirst();
        ArrayList<String> listProduct = new ArrayList<>();
        while (res.moveToNext()) {
            listProduct.add(res.getString(0));
        }

        return listProduct;
    }

    public ArrayList<String> getColumn(SQLiteDatabase dbSelect, String column, String table) {

        String query = "SELECT " + column + "FROM" + table;

        Cursor res = dbSelect.rawQuery(query, null);
        res.moveToFirst();
        ArrayList<String> listProduct = new ArrayList<>();
        while (res.moveToNext()) {
            listProduct.add(res.getString(0));
        }

        return listProduct;
    }


    public ArrayList<ProductCategories> getAllCategories() {

        ProductCategories productCategories;
        Cursor res=getTable(TABLE_PRODUCTCATEGORIES);
        res.moveToFirst();
        ArrayList<ProductCategories> listCategories = new ArrayList<>();
        while(res.moveToNext()){
            productCategories = new ProductCategories();
            productCategories.setCOLUMN_PRODUCTCATEGORYID(res.getString(0));
            productCategories.setCOLUMN_NAME(res.getString(1));
            productCategories.setCOLUMN_USEROWNERID(res.getString(2));
            productCategories.setCOLUMN_COLOR(res.getString(3));
            productCategories.setCOLUMN_MONTHBUDGET(res.getString(4));
            productCategories.setCOLUMN_EXTERNALSYSTEMID(res.getString(5));
            productCategories.setCOLUMN_LASTMODIFIEDDATE(res.getString(6));
            listCategories.add(productCategories);
        }

        return listCategories;
    }


    //VOID




    public Cursor getBills() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Bills._id, Bills.PurchaseDate, Shops.ShopName FROM Bills, Shops WHERE Bills.Shop_ShopID = Shops._id", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    private void insertShopByString(SQLiteDatabase dbInsert, String shopName) {


        String query = "INSERT INTO " + TABLE_SHOPS + "(" + CreateShops.COLUMN_SHOPNAME + ") " + " SELECT '" + shopName + "' WHERE NOT EXISTS(SELECT 1 FROM " + TABLE_SHOPS + " WHERE " + CreateShops.COLUMN_SHOPNAME + " LIKE '" + shopName + "')";
        dbInsert.execSQL(query);

    }

    public void insertProductCategoryToDB(SQLiteDatabase dbInsert,ProductCategories cat){
        String query = "INSERT INTO "+TABLE_PRODUCTCATEGORIES+"('"+COLUMN_PRODUCTCATEGORYID+"','"+COLUMN_NAME+"','"+COLUMN_USEROWNERID+"','"+COLUMN_COLOR+"','"+COLUMN_MONTHBUDGET+"') VALUES ('"+cat.getCOLUMN_PRODUCTCATEGORYID()+"','"+cat.getCOLUMN_NAME()+"','"+cat.getCOLUMN_USEROWNERID()+"','"+cat.getCOLUMN_COLOR()+"','"+cat.getCOLUMN_MONTHBUDGET()+"')";
        dbInsert.execSQL(query);

    }

    private void insertProductToDB(SQLiteDatabase dbInsert, SQLiteDatabase dbSelect, String shopID, String shopName, FinalProduct prod) {

        ContentValues content = new ContentValues();
        content.put(CreateBills.COLUMN_SHOP_SHOPID, shopID);
        dbInsert.insert(TABLE_BILLS, null, content);

        content = new ContentValues();
        content.put(COLUMN_BILL_BILLID, findBillID(dbSelect));
        content.put(COLUMN_CATEGORY_PRODUCTCATEGORYID, prod.getCategoryID());
        content.put(COLUMN_PRICE, prod.getUnitPrice());
        content.put(COLUMN_PRODUCTNAME, prod.getName());
        content.put(COLUMN_QUANTITY, prod.getQuantity());
        dbInsert.insert(CreateBillEntries.TABLE_BILLENTRIES, null, content);


    }

    public void updateProductCategoryInDB(SQLiteDatabase dbInsert,ProductCategories cat){
        String query = "UPDATE  "+TABLE_PRODUCTCATEGORIES+" SET "+COLUMN_NAME+"='"+cat.getCOLUMN_NAME()+"',"+COLUMN_USEROWNERID+"='"+cat.getCOLUMN_USEROWNERID()+"',"+COLUMN_COLOR+"='"+cat.getCOLUMN_COLOR()+"',"+COLUMN_MONTHBUDGET+"='"+cat.getCOLUMN_MONTHBUDGET()+"',"+COLUMN_EXTERNALSYSTEMID+"='"+cat.getCOLUMN_EXTERNALSYSTEMID()+"',"+COLUMN_LASTMODIFIEDDATE+"='"+cat.getCOLUMN_LASTMODIFIEDDATE()+"') WHERE "+COLUMN_PRODUCTCATEGORYID+"="+cat.getCOLUMN_PRODUCTCATEGORYID();
        dbInsert.execSQL(query);

    }

    //STRING

    private String findCategoryIDByString(SQLiteDatabase dbSelect, String category) {
        String query = "SELECT " + CreateProductCategories.COLUMN_PRODUCTCATEGORYID + " FROM " + TABLE_PRODUCTCATEGORIES + " WHERE " + CreateProductCategories.COLUMN_NAME + " = \'" + category + "\'";
        Cursor res = dbSelect.rawQuery(query, null);

        if (res != null) {
            res.moveToFirst();
        }
        return res.toString();
    }


    private String findShopIDByName(SQLiteDatabase dbSelect, String shop) {
        String query = "SELECT " + CreateShops.COLUMN_SHOPID + " FROM " + TABLE_SHOPS + " WHERE " + CreateShops.COLUMN_SHOPNAME + " = '%" + shop + "%'";
        Cursor res = dbSelect.rawQuery(query, null);
        res.moveToFirst();
        String str = res.getString(res.getColumnIndex(CreateShops.COLUMN_SHOPID));

        return str;
    }

    private String findBillID(SQLiteDatabase dbSelect) {
        String query = "SELECT " + CreateBills.COLUMN_BILLID + " FROM " + TABLE_BILLS + " ORDER BY " + CreateBills.COLUMN_BILLID + " DESC LIMIT 1";
        Cursor res = dbSelect.rawQuery(query, null);
        res.moveToFirst();
        String str = res.getString(res.getColumnIndex(CreateBills.COLUMN_BILLID));

        return str;
    }




}
