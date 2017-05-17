package pwr.billsmanagement.bills.edition;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.view.DefineProductItem;
import pwr.billsmanagement.bills.edition.view.DefineProductRowCreator;

/**
 * Created by Squier on 17.05.2017.
 */

public class ShredProductAssembler {

    private String productName;
    private String productUnitPrice;
    private String productTotalPrice;

    private String[] defineOptions;

    public ShredProductAssembler(String[] defineOptions) {
        this.defineOptions = defineOptions;
    }

    public void assembly(ShredProduct shredProduct) {
        for (DefineProductItem shred : shredProduct.getShreds()) {
            String labelText = shred.getLabel().getText().toString();
            productName = "";
            productTotalPrice = "";
            productUnitPrice = "";

            if(labelText.equals(defineOptions[DefineProductRowCreator.NAME])) {
                productName += shred.getValue().getText();
            } else if(labelText.equals(defineOptions[DefineProductRowCreator.TOTAL_PRICE])) {
                productTotalPrice += shred.getValue().getText();
            } else if(labelText.equals(defineOptions[DefineProductRowCreator.UNIT_PRICE])) {
                productUnitPrice += shred.getValue().getText();
            }
        }
    }

    public String getProductName() {
        return productName;
    }

    public String getProductUnitPrice() {
        return productUnitPrice;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }
}
