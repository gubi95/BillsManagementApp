package pwr.billsmanagement.bills.edition.listeners;

import android.view.View;
import android.widget.TextView;

import pwr.billsmanagement.bills.edition.ChosenOption;

/**
 * Created by Squier on 16.05.2017.
 */

public class DetermineOptionListener implements View.OnClickListener {

    private ChosenOption chosenOption;
    private TextView defineProductItemLabel;
    private View defineProductItemValue;

    @Override
    public void onClick(View v) {
        defineProductItemLabel.setBackgroundColor(chosenOption.getColorPair().getDark());
        defineProductItemLabel.setText(chosenOption.getLabelText());
        defineProductItemValue.setBackgroundColor(chosenOption.getColorPair().getLight());
    }

    public void setDefineProductItemLabel(TextView defineProductItemLabel) {
        this.defineProductItemLabel = defineProductItemLabel;
    }

    public void setDefineProductItemValue(View defineProductItemValue) {
        this.defineProductItemValue = defineProductItemValue;
    }

    public void setChosenOption(ChosenOption chosenOption) {
        this.chosenOption = chosenOption;
    }
}
