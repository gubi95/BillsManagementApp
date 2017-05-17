package pwr.billsmanagement.bills.edition.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.ChosenOption;
import pwr.billsmanagement.bills.edition.ShredProduct;
import pwr.billsmanagement.bills.edition.listeners.DetermineOptionListener;
import pwr.billsmanagement.ocr.parsers.Product;

/**
 * Created by Squier on 16.05.2017.
 */

public class DefineProductRowCreator {

    public enum ColorPair {

        BLUE(Color.argb(200, 3, 169, 244), Color.argb(200, 129, 212, 250)),
        GREEN(Color.argb(200, 76, 175, 80), Color.argb(200, 165, 214, 167)),
        RED(Color.argb(200, 244, 67, 54), Color.argb(200, 239, 154, 154)),
        GRAY(Color.argb(200, 158, 158, 158), Color.argb(200, 238, 238, 238));

        private int dark;
        private int light;

        ColorPair(int dark, int light) {
            this.dark = dark;
            this.light = light;
        }

        public int getDark() {
            return dark;
        }

        public int getLight() {
            return light;
        }
    }

    public static final int NAME = 0;
    public static final int TOTAL_PRICE = 1;
    public static final int UNIT_PRICE = 2;
    public static final int IGNORE = 3;

    private String[] defineOptions;
    private String rowTitle;

    private LayoutInflater inflater;
    private ChosenOption chosenOption;

    private ArrayList<ShredProduct> shredProducts;

    public DefineProductRowCreator(Context context, ChosenOption chosenOption, ArrayList<ShredProduct> shredProducts) {
        this.chosenOption = chosenOption;
        inflater = LayoutInflater.from(context);
        defineOptions = context.getResources().getStringArray(R.array.define_options);
        rowTitle = context.getResources().getString(R.string.define_label);
        this.shredProducts = shredProducts;
    }

    public View getProductRow(Product product, int id) {

        View row = inflater.inflate(R.layout.bill_define_product_row, null);
        initMain(row, id);

        initSubLayout(
                row,
                product.getName().split(" "),
                ColorPair.GREEN,
                defineOptions[NAME],
                R.id.productNameStrings
        );

        initSubLayout(row,
                product.getPrice().split(" "),
                ColorPair.BLUE,
                defineOptions[TOTAL_PRICE],
                R.id.productPriceStrings
        );

        return row;
    }

    public ArrayList<ShredProduct> getShredProducts() {
        return shredProducts;
    }

    private void initMain(View row, int id) {
        ((ImageView) row.findViewById(R.id.deleteProduct)).setImageResource(R.drawable.ic_delete_product);
        ((TextView) row.findViewById(R.id.productNumber)).setText(rowTitle + " " + id);
    }

    private void initSubLayout(View row, String[] words, ColorPair colorPair, String labelText, int linearLayoutId) {
        LinearLayout linearLayout = (LinearLayout) row.findViewById(linearLayoutId);

        ShredProduct shredProduct = new ShredProduct(new ArrayList<>());

        for (String word : words) {
            View define = getDefineProductItem(word, labelText, colorPair, shredProduct);
            linearLayout.addView(define);
        }

        shredProducts.add(shredProduct);
    }

    private View getDefineProductItem(String word, String labelText, ColorPair colorPair, ShredProduct shredProduct) {
        View define = inflater.inflate(R.layout.bill_define_product_item, null);

        TextView label = (TextView) define.findViewById(R.id.label);
        label.setText(labelText);
        label.setBackgroundColor(colorPair.dark);

        TextView value = (TextView) define.findViewById(R.id.value);
        value.setText(word);
        value.setBackgroundColor(colorPair.light);

        setOnClickListener(define, label, value);

        shredProduct.addShred(new DefineProductItem(label, value));

        return define;
    }

    private void setOnClickListener(View define, TextView label, TextView value) {
        DetermineOptionListener optionListener = new DetermineOptionListener();
        optionListener.setChosenOption(chosenOption);
        optionListener.setDefineProductItemLabel(label);
        optionListener.setDefineProductItemValue(value);
        define.setOnClickListener(optionListener);
    }


}