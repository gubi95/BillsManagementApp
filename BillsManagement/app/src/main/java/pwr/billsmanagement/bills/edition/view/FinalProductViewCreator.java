package pwr.billsmanagement.bills.edition.view;

import android.content.Context;
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
        String categories = reader.readMyProperties("properties/categories.properties").getProperty("categories_values");
        String[] categoriesTab = categories.split(",");
        ArrayAdapter<String> adapter = new SpinnerAdapter(
                context, android.R.layout.simple_spinner_item, categoriesTab);
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

        private final String[] ICON_KEYS = {
                "groceries_icon", "ind_good_icon", "clothes_icon", "food_icon", "beverages_icon",
                "vegetables_icon", "bread_icon", "dairy_icon", "meat_icon", "chemistry_icon",
                "cosmetics_icon", "animals_icon", "kids_icon"
        };

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

            setIconAndText(getItem(position), handle.icon, handle.text);

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

        private void setIconAndText(String item, ImageView icon, TextView text) {
            String[] valueWithIcon = item.split(";");
            text.setText(valueWithIcon[0]);

            int id = context.getResources().getIdentifier(valueWithIcon[1], "drawable", context.getPackageName());
            icon.setImageResource(id);
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
