package pwr.billsmanagement.bills.edition.view;

import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Squier on 22.05.2017.
 */

public class FinalProductView {

    private EditText name, unitPrice, quantity, totalPrice;
    private Spinner category;

    public FinalProductView() {
    }

    public FinalProductView(EditText name, EditText unitPrice, EditText quantity, EditText totalPrice, Spinner category) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.category = category;
    }

    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    public EditText getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(EditText unitPrice) {
        this.unitPrice = unitPrice;
    }

    public EditText getQuantity() {
        return quantity;
    }

    public void setQuantity(EditText quantity) {
        this.quantity = quantity;
    }

    public EditText getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(EditText totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Spinner getCategory() {
        return category;
    }

    public void setCategory(Spinner category) {
        this.category = category;
    }
}
