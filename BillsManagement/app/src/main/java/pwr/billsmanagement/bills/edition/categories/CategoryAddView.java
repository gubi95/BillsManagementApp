package pwr.billsmanagement.bills.edition.categories;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.util.Properties;

import pwr.billsmanagement.R;
import pwr.billsmanagement.readers.PropertiesReader;

import static pwr.billsmanagement.bills.edition.categories.Keys.CATEGORIES_PROPERTIES;

/**
 * Created by Squier on 25.05.2017.
 */

public class CategoryAddView {

    private PopupWindow addCategoryView;
    private View popupView;
    private WindowManager windowManager;
    private Context context;

    public CategoryAddView(WindowManager windowManager, Context context) {
        this.windowManager = windowManager;
        this.context = context;
    }

    public PopupWindow getCategoryAddView() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.bill_edit_category, null);

        addCategoryView = new PopupWindow(
                popupView, getMetrics().widthPixels - 250, getMetrics().heightPixels/3
        );

        setButtonsListeners();

        return addCategoryView;
    }

    public void showPopup(View parent) {
        addCategoryView.setElevation(5.0f);
        addCategoryView.setFocusable(true);
        addCategoryView.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public void setButtonsListeners() {
        Button addCategory = (Button) popupView.findViewById(R.id.addCategory);
        Button cancelAdd = (Button) popupView.findViewById(R.id.cancelAddCategory);

        addCategory.setOnClickListener(v -> addCategoryView.dismiss());
        cancelAdd.setOnClickListener(v -> addCategoryView.dismiss());

    }

    private DisplayMetrics getMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

}
