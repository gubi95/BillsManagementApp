package pwr.billsmanagement.bills.edition.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.products.AssembledProduct;
import pwr.billsmanagement.readers.PropertiesReader;

/**
 * Created by Squier on 17.05.2017.
 */

public class FinalProductViewCreator implements ViewCreator<AssembledProduct> {

    private LayoutInflater inflater;
    private Context context;
    private String rowTitle;
    private ArrayList<FinalProductView> finalProductViews;

    public FinalProductViewCreator(Context context, ArrayList<FinalProductView> finalProductViews) {
        inflater = LayoutInflater.from(context);
        rowTitle = context.getResources().getString(R.string.define_label);
        this.finalProductViews = finalProductViews;
        this.context = context;
    }

    public View getProductRowAndSave(AssembledProduct product, int id) {
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

        initCategorySpinner(item.getCategory());

        finalProductViews.add(item);

        return row;
    }

    private void initCategorySpinner(Spinner category) {
        PropertiesReader reader = new PropertiesReader(context, new Properties());
        String categories = reader.readMyProperties("properties/products.properties").getProperty("categories_values");
        String[] categoriesTab = categories.split(",");
        ArrayAdapter<String> adapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, categoriesTab);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        category.setAdapter(adapter);
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

    private class SpinnerAdapter extends ArrayAdapter<String> {

        private String[] categories;

        public SpinnerAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            categories = objects;
        }

        public View getCustomView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.spinner_item, null);

            ViewHandle handle = new ViewHandle(
                    rowView.findViewById(R.id.itemIcon),
                    rowView.findViewById(R.id.itemText),
                    rowView.findViewById(R.id.itemMainContainer)
            );

            setImageResourcesByCategory(position, handle.icon, handle.text);
            handle.text.setText(getItem(position));

            return rowView;
        }

        @Override
        public String getItem(int position) {
            return categories[position];
        }

        @Override
        public View getDropDownView(int position, View rowView, ViewGroup parent) {
            return getCustomView(position, rowView, parent);
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            return getCustomView(position, rowView, parent);
        }

        private void setImageResourcesByCategory(int item, ImageView icon, TextView text) {
            switch (item) {
                case 0: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_groceries);
                    text.setTypeface(Typeface.DEFAULT_BOLD);
                    break;
                }
                case 1: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_ind_goods);
                    text.setTypeface(Typeface.DEFAULT_BOLD);
                    break;
                }
                case 2: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_clothes);
                    text.setTypeface(Typeface.DEFAULT_BOLD);
                    break;
                }
                case 3: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_food);
                    break;
                }
                case 4: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_beverages);
                    break;
                }
                case 5: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_vegetables_fruits);
                    break;
                }
                case 6: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_bread);
                    break;
                }
                case 7: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_dairy);
                    break;
                }
                case 8: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_meat);
                    break;
                }
                case 9: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_chemistry);
                    break;
                }
                case 10: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_cosmetics);
                    break;
                }
                case 11: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_animals);
                    break;
                }
                case 12: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_kids);
                    break;
                }
                default: {
                    Logger.i("In case: " + item);
                    icon.setImageResource(R.drawable.ic_default);
                    break;
                }
            }
        }


        private class ViewHandle {
            ImageView icon;
            TextView text;
            LinearLayout layout;

            public ViewHandle(View icon, View text, View layout) {
                this.icon = (ImageView) icon;
                this.text = (TextView) text;
                this.layout = (LinearLayout) layout;
            }

        }
    }

}
