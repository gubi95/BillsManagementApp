package pwr.billsmanagement.bills.edition.view;

import android.widget.TextView;

/**
 * Created by Squier on 17.05.2017.
 */
public class DefineProductView {

    private TextView label;
    private TextView value;

    public DefineProductView(TextView label, TextView value) {
        this.label = label;
        this.value = value;
    }

    public TextView getLabel() {
        return label;
    }

    public TextView getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{label=" + label.getText() +
                ", value=" + value.getText() +
                '}';
    }
}
