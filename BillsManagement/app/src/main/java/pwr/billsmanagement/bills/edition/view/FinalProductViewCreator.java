package pwr.billsmanagement.bills.edition.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.products.AssembledProduct;

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

        String totalPrice = extractPrice(product.getProductTotalPrice());
        String unitPrice = extractPrice(product.getProductUnitPrice());

        ((EditText)row.findViewById(R.id.productFinalTotalPrice)).setText(totalPrice);
        ((EditText)row.findViewById(R.id.productFinalUnitPrice)).setText(unitPrice);

        String quantity = Float.toString(Float.parseFloat(totalPrice)/Float.parseFloat(unitPrice));

        ((EditText)row.findViewById(R.id.productFinalQuantity)).setText(quantity);


        return row;
    }

    private String extractPrice(String string) {
        Pattern pattern = Pattern.compile("([1-9][0-9]*[.,][0-9][0-9])");
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()) {
            Logger.i("IS PRICE: " + matcher.group(1) + " FROM: " + string);
            return matcher.group(1).replaceAll("[,]", ".");
        }
        else return string;
    }

}
