package pwr.billsmanagement.billslist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.db.creators.CreateBills;
import pwr.billsmanagement.db.creators.CreateShops;
import pwr.billsmanagement.db.creators.DBHandler;
import pwr.billsmanagement.ocr.OCRActivity;
import pwr.billsmanagement.charts.AnalysisActivity;
import pwr.billsmanagement.ocr.parsers.OcrProduct;


public class BillsList extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerList;
    private ActionBarDrawerToggle drawerListener;
    private Toolbar toolbar;
    private String login = "Przykładowy Login";
    private String[] mMenuOptions;
    private TextView tvUsername;


    private DBHandler dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private SQLiteDatabase dbSelect;

    final String[] from = new String[]{CreateBills.COLUMN_BILLID, CreateShops.COLUMN_SHOPNAME, CreateBills.COLUMN_PURCHASEDATE};
    final int[] to = new int[]{R.id.id, R.id.shopname, R.id.date};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (NavigationView) findViewById(R.id.drawer_list);
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        mMenuOptions = getResources().getStringArray(R.array.menu_items);

        getSupportActionBar().setTitle("Paragony");
        View header = mDrawerList.getHeaderView(0);
        tvUsername = (TextView) header.findViewById(R.id.username);
        tvUsername.setText(login);


        mDrawerList.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent mIntent = null;
                switch (item.getItemId()) {
                    case R.id.menu_list:
                        mIntent = new Intent(BillsList.this, BillsList.class);
                        break;
                    case R.id.menu_add_pic:
                        Logger.init("SZYSZKA");
                        mIntent = new Intent(BillsList.this, OCRActivity.class);
                        break;
                    case R.id.menu_add_man:
                        Logger.init("SZYSZKA");
                        Gson gson = new Gson();
                        mIntent = new Intent(BillsList.this, EditBillActivity.class);
                        mIntent.putExtra("run_mode", "edit");
                        mIntent.putExtra("products_json", gson.toJson(populateProductArray()));
                        break;
                    case R.id.menu_charts:
                        mIntent = new Intent(BillsList.this, AnalysisActivity.class);
                        break;
                    case R.id.menu_sync:
                        break;
                    case R.id.menu_logout:
                        mIntent = new Intent(Intent.ACTION_MAIN);
                        mIntent.addCategory(Intent.CATEGORY_HOME);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        break;
                    default:
                        mIntent = new Intent(BillsList.this, BillsList.class); // Activity_0 as default
                        break;
                }
                mDrawerLayout.closeDrawers();
                startActivity(mIntent);
                return false;
            }
        });

        drawerListener = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO
                // action performed when drawer closed
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerOpened(View drawerView) {
                //TODO
                // action performed when drawer opened
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(drawerListener);


        dbManager = new DBHandler(this);
        Cursor cursor = dbManager.getBills();

        listView = (ListView) findViewById(R.id.list_view);

        adapter = new SimpleCursorAdapter(this, R.layout.show_bills, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
    }

    private ArrayList<OcrProduct> populateProductArray() {
        ArrayList<OcrProduct> products = new ArrayList<>();
        products.add(new OcrProduct("", ""));
        products.add(new OcrProduct("", ""));
        return products;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }


}
