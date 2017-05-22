package pwr.billsmanagement.bills.edition.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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
    private ArrayList<FinalProductView> finalProductViews;

    public FinalProductViewCreator(Context context, ArrayList<FinalProductView> finalProductViews) {
        inflater = LayoutInflater.from(context);
        rowTitle = context.getResources().getString(R.string.define_label);
        this.finalProductViews = finalProductViews;
    }

    public View getRowViewAndSave(AssembledProduct product, int id) {
        View row = inflater.inflate(R.layout.bill_edit_product_row, null);

        FinalProductView item = new FinalProductView(
                (EditText)row.findViewById(R.id.productFinalName),
                (EditText)row.findViewById(R.id.productFinalUnitPrice),
                (EditText)row.findViewById(R.id.productFinalQuantity),
                (EditText)row.findViewById(R.id.productFinalTotalPrice),
                (Spinner)row.findViewById(R.id.productFinalCategory)
        );

        ((TextView)row.findViewById(R.id.productFinalNumber)).setText(rowTitle + " " + id);
        item.getName().setText(product.getProductName());

        String totalPrice = extractPrice(product.getProductTotalPrice());
        String unitPrice = extractPrice(product.getProductUnitPrice());

        item.getTotalPrice().setText(totalPrice);
        item.getUnitPrice().setText(unitPrice);

        String quantity = "1.0";

        try {
            quantity = Float.toString(Float.parseFloat(totalPrice) / Float.parseFloat(unitPrice));
        } catch (NumberFormatException e) {
            Logger.e(e, "Possibly no price found.");
        }
        item.getQuantity().setText(quantity);

        finalProductViews.add(item);

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

    public ArrayList<FinalProductView> getFinalProductViews() {
        return finalProductViews;
    }
}
