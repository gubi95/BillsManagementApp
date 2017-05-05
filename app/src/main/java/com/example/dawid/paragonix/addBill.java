package com.example.dawid.paragonix;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class addBill extends AppCompatActivity {

    ArrayList<BillRecord> arrayOfRecords = new ArrayList<BillRecord>();
    // Create the adapter to convert the array to views
    BillRecordAdapter adapter = new BillRecordAdapter(this, arrayOfRecords);


    // Attach the adapter to a ListView
    //ListView listView = (ListView) findViewById(R.id.tvProduct);
    //listView.setAdapter(adapter);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
    }

}
