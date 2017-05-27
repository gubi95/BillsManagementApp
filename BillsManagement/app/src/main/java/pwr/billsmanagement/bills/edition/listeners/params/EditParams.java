package pwr.billsmanagement.bills.edition.listeners.params;

import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.bills.edition.products.FinalProductAssembler;
import pwr.billsmanagement.bills.edition.view.FinalProductView;

/**
 * Created by Squier on 23.05.2017.
 */

public class EditParams implements ListenerParams {
    private ArrayListParams<FinalProductView, FinalProduct> arrayListParams;
    private ViewParams viewParams;
    private FinalProductAssembler assembler;

    public EditParams(ArrayListParams<FinalProductView, FinalProduct> arrayListParams,
                      FinalProductAssembler assembler, ViewParams viewParams) {
        this.arrayListParams = arrayListParams;
        this.assembler = assembler;
        this.viewParams = viewParams;
    }

    public ArrayListParams<FinalProductView, FinalProduct> getArrayListParams() {
        return arrayListParams;
    }

    public FinalProductAssembler getAssembler() {
        return assembler;
    }

    public ViewParams getViewParams() {
        return viewParams;
    }
}
