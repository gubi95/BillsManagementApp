package com.example.dawid.paragonix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class BillRecordAdapter extends ArrayAdapter<BillRecord> {
    public BillRecordAdapter(Context context, ArrayList<BillRecord> records) {
        super(context, 0, records);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BillRecord record = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_add_bill, parent, false);
        }
        // Lookup view for data population
        TextView tvProduct = (TextView) convertView.findViewById(R.id.tvProduct);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);

        tvProduct.setText(record.product);
        tvPrice.setText(record.price);
        tvCategory.setText(record.category);
        return convertView;
    }
}