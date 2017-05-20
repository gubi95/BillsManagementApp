package pwr.billsmanagement.bills.edition;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.view.DefineProductItem;
import pwr.billsmanagement.bills.edition.view.DefineProductRowCreator;

import static pwr.billsmanagement.bills.edition.view.DefineProductRowCreator.*;

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

        productName = "";
        productTotalPrice = "";
        productUnitPrice = "";

        for (DefineProductItem shred : shredProduct.getShreds()) {
            String labelText = "";
            String valueText = "";

            labelText += shred.getLabel().getText();
            valueText += shred.getValue().getText();

            Logger.i("Assembling product: label:" + labelText + " value:" + valueText);
            Logger.i("Assembling options: " + defineOptions[0] + "/" + defineOptions[1] + "/" + defineOptions[2]);

            if(labelText.equals(defineOptions[NAME])) {
                Logger.i("Assembling, added name: " + valueText);
                productName += valueText + " ";
            } else if(labelText.equals(defineOptions[TOTAL_PRICE])) {
                Logger.i("Assembling, added totalPrice: " + valueText);
                productTotalPrice += valueText + " ";
            } else if(labelText.equals(defineOptions[UNIT_PRICE])) {
                Logger.i("Assembling, added unitPrice: " + valueText);
                productUnitPrice += valueText + " ";
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
