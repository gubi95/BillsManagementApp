package pwr.billsmanagement.list;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import pwr.billsmanagement.R;
import pwr.billsmanagement.db.creators.CreateBills;
import pwr.billsmanagement.db.creators.CreateShops;
import pwr.billsmanagement.db.creators.DBHandler;
import pwr.billsmanagement.db.data.Bills;

/**
 * Created by Dawid on 27/05/2017.
 */

public class BillsList extends AppCompatActivity {

    private DBHandler dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private SQLiteDatabase dbSelect;

    final String[] from = new String[]{CreateBills.COLUMN_BILLID, CreateShops.COLUMN_SHOPNAME, CreateBills.COLUMN_PURCHASEDATE};
    final int[] to = new int[]{R.id.id, R.id.shopname, R.id.date};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.empty_list);

        dbManager = new DBHandler(this);
        //dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);


        // OnCLickListener For List Items


    }

}
