package pwr.billsmanagement.bills.edition.products;

import pwr.billsmanagement.bills.edition.view.DefineProductView;

import static pwr.billsmanagement.bills.edition.view.DefineProductViewCreator.NAME;
import static pwr.billsmanagement.bills.edition.view.DefineProductViewCreator.TOTAL_PRICE;
import static pwr.billsmanagement.bills.edition.view.DefineProductViewCreator.UNIT_PRICE;

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

        for (DefineProductView shred : shredProduct.getShreds()) {
            String labelText = "";
            String valueText = "";

            labelText += shred.getLabel().getText();
            valueText += shred.getValue().getText();


            if (labelText.equals(defineOptions[NAME])) {
                productName += valueText + " ";
            } else if (labelText.equals(defineOptions[TOTAL_PRICE])) {
                productTotalPrice += valueText + " ";
            } else if (labelText.equals(defineOptions[UNIT_PRICE])) {
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
