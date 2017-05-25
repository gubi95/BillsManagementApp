package pwr.billsmanagement.bills.edition.categories;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import pwr.billsmanagement.R;

/**
 * Created by Squier on 25.05.2017.
 */

public class CategoryAddView {

    private PopupWindow addCategoryView;
    private WindowManager windowManager;
    private Context context;

    public CategoryAddView(WindowManager windowManager, Context context) {
        this.windowManager = windowManager;
        this.context = context;
    }

    public PopupWindow getCategoryAddView() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.bill_edit_category, null);

        addCategoryView = new PopupWindow(
                popupView, getMetrics().widthPixels - 250, getMetrics().heightPixels/2
        );

        return addCategoryView;
    }

    public void showPopup(View parent) {
        addCategoryView.setElevation(5.0f);
        addCategoryView.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    private DisplayMetrics getMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

}
