package pwr.billsmanagement.bills.edition.categories;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import pwr.billsmanagement.R;

/**
 * Created by Squier on 28.05.2017.
 */

public class IconSpinner extends ArrayAdapter<String> {


    public IconSpinner(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public View getCustomView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.icon_spinner_item, null);

        ImageView icon = (ImageView) mView.findViewById(R.id.iconItem);
        int id = getContext().getResources().getIdentifier(getItem(position), "drawable", getContext().getPackageName());
        icon.setImageResource(id);

        return mView;
    }

    @Override
    public View getDropDownView(int position, View rowView, ViewGroup parent) {
        return getCustomView(position, rowView, parent);
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        return getCustomView(position, rowView, parent);
    }

}
