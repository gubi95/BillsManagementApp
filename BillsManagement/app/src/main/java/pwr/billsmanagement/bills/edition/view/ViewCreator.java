package pwr.billsmanagement.bills.edition.view;

import android.view.View;

/**
 * Created by Squier on 23.05.2017.
 */

public interface ViewCreator<T> {

    View getProductRowAndSave(T product, int id);

}
