package pwr.billsmanagement.bills.edition.listeners.params;

import java.util.ArrayList;

/**
 * Created by Squier on 23.05.2017.
 */

public class ArrayListParams<T1, T2> {

    private ArrayList<T1> typeOneArray;
    private ArrayList<T2> typeTwoArray;

    public ArrayListParams(ArrayList<T1> typeOneArray, ArrayList<T2> typeTwoArray) {
        this.typeOneArray = typeOneArray;
        this.typeTwoArray = typeTwoArray;
    }

    public ArrayList<T1> getTypeOneArray() {
        return typeOneArray;
    }

    public ArrayList<T2> getTypeTwoArray() {
        return typeTwoArray;
    }
}
