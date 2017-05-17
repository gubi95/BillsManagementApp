package pwr.billsmanagement.bills.edition;

import pwr.billsmanagement.bills.edition.view.DefineProductRowCreator;

/**
 * Created by Squier on 17.05.2017.
 */

public class ChosenOption {

    private DefineProductRowCreator.ColorPair colorPair;
    private String labelText;

    public ChosenOption() {
        colorPair = DefineProductRowCreator.ColorPair.GRAY;
    }

    public void setColorPair(DefineProductRowCreator.ColorPair colorPair) {
        this.colorPair = colorPair;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public DefineProductRowCreator.ColorPair getColorPair() {
        return colorPair;
    }

    public String getLabelText() {
        return labelText;
    }
}
