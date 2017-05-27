package pwr.billsmanagement.bills.edition.products;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.view.DefineProductView;

/**
 * Created by Squier on 17.05.2017.
 */
public class ShredProduct {

    private ArrayList<DefineProductView> shreds;

    public ShredProduct(ArrayList<DefineProductView> shreds) {
        this.shreds = shreds;
    }

    public void addShred(DefineProductView shred) {
        shreds.add(shred);
    }

    public ArrayList<DefineProductView> getShreds() {
        return shreds;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ShredProduct={");
        for (DefineProductView shred :
                shreds) {
            builder.append("shred=");
            builder.append(shred.toString());
            builder.append(", ");
        }
        builder.append("}");
        return builder.toString();
    }
}
