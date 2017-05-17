package pwr.billsmanagement.bills.edition.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.AssembledProduct;

/**
 * Created by Squier on 17.05.2017.
 */

public class FinalProductViewCreator {

    private LayoutInflater inflater;
    private String rowTitle;

    public FinalProductViewCreator(Context context) {
        inflater = LayoutInflater.from(context);
        rowTitle = context.getResources().getString(R.string.define_label);
    }

    public View getRowView(AssembledProduct product, int id) {
        View row = inflater.inflate(R.layout.bill_edit_product_row, null);

        ((TextView)row.findViewById(R.id.productFinalNumber)).setText(rowTitle + " " + id);
        ((EditText)row.findViewById(R.id.productFinalName)).setText(product.getProductName());
        ((EditText)row.findViewById(R.id.productFinalTotalPrice)).setText(product.getProductTotalPrice());
        ((EditText)row.findViewById(R.id.productFinalUnitPrice)).setText(product.getProductUnitPrice());

        return row;
    }

}
