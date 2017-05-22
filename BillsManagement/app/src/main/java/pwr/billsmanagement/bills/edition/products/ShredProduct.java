package pwr.billsmanagement.bills.edition.products;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.view.DefineProductItem;

/**
 * Created by Squier on 17.05.2017.
 */
public class ShredProduct {

    private ArrayList<DefineProductItem> shreds;

    public ShredProduct(ArrayList<DefineProductItem> shreds) {
        this.shreds = shreds;
    }

    public void addShred(DefineProductItem shred) {
        shreds.add(shred);
    }

    public ArrayList<DefineProductItem> getShreds() {
        return shreds;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ShredProduct={");
        for (DefineProductItem shred :
                shreds) {
            builder.append("shred=");
            builder.append(shred.toString());
            builder.append(", ");
        }
        builder.append("}");
        return builder.toString();
    }
}
