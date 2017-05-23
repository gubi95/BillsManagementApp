package pwr.billsmanagement.bills.edition.listeners.params;

import android.content.Context;
import android.view.View;

import java.util.Map;

import pwr.billsmanagement.bills.edition.EditBillActivity;

/**
 * Created by Squier on 23.05.2017.
 */

public class ViewParams {

    private Context context;
    private EditBillActivity.LayoutHandle layoutHandle;
    private View[] views;

    private Map<String, Integer> viewMap;

    public ViewParams(Context context, EditBillActivity.LayoutHandle layoutHandle, View... view) {
        this.context = context;
        this.layoutHandle = layoutHandle;
        this.views = view;
    }

    public Context getContext() {
        return context;
    }

    public EditBillActivity.LayoutHandle getLayoutHandle() {
        return layoutHandle;
    }

    public View[] getViews() {
        return views;
    }

    public int getViewsLength() {
        return views.length;
    }

    public Map<String, Integer> getViewMap() {
        return viewMap;
    }

    public void setViewMap(Map<String, Integer> viewMap) {
        this.viewMap = viewMap;
    }
}
