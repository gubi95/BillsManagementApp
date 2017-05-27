package pwr.billsmanagement.bills.edition;

import pwr.billsmanagement.bills.edition.view.DefineProductViewCreator;

/**
 * Created by Squier on 17.05.2017.
 */

public class ChosenOption {

    private DefineProductViewCreator.ColorPair colorPair;
    private String labelText;

    public ChosenOption() {
        colorPair = DefineProductViewCreator.ColorPair.GRAY;
    }

    public DefineProductViewCreator.ColorPair getColorPair() {
        return colorPair;
    }

    public void setColorPair(DefineProductViewCreator.ColorPair colorPair) {
        this.colorPair = colorPair;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }
}
