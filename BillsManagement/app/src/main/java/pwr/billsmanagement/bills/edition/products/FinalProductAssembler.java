package pwr.billsmanagement.bills.edition.products;

import com.orhanobut.logger.Logger;

import pwr.billsmanagement.bills.edition.view.FinalProductView;

/**
 * Created by Squier on 22.05.2017.
 */

public class FinalProductAssembler {

    public FinalProduct assembly(FinalProductView finalProductView) {

        FinalProduct finalProduct = new FinalProduct();

        finalProduct.setName(finalProductView.getName().getText().toString());
        finalProduct.setUnitPrice(finalProductView.getUnitPrice().getText().toString());
        finalProduct.setQuantity(finalProductView.getQuantity().getText().toString());
        finalProduct.setTotalPrice(finalProductView.getTotalPrice().getText().toString());
        finalProduct.setCategory("Jedzenie");
        //finalProduct.setCategory(finalProductView.getCategory().getSelectedItem().toString());

        Logger.i("Assembled final product: " + finalProduct.toString());

        return finalProduct;
    }

}
