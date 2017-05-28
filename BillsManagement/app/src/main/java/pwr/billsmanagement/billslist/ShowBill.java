package pwr.billsmanagement.billslist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import pwr.billsmanagement.R;
import pwr.billsmanagement.db.creators.CreateBills;
import pwr.billsmanagement.db.creators.CreateShops;
import pwr.billsmanagement.db.creators.DBHandler;

/**
 * Created by Dawid on 28/05/2017.
 */

public class ShowBill extends Activity {

    final String[] from = new String[]{CreateBills.COLUMN_BILLID, CreateShops.COLUMN_SHOPNAME, CreateBills.COLUMN_PURCHASEDATE};
    final int[] to = new int[]{R.id.textView, R.id.textView2, R.id.textView3};

    private DBHandler dbManager;

    @Override
    protected void onCreate(Bundle savedInstateState) {
        super.onCreate(savedInstateState);

        setTitle("Paragony");

        dbManager = new DBHandler(this);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        int _id = Integer.parseInt(id);

        Cursor cursor = dbManager.getBill(_id);




    }
}
