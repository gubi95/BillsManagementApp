package pwr.billsmanagement.bills.edition.listeners.params;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;

import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.listeners.AcceptAllListenerFactory;
import pwr.billsmanagement.bills.edition.listeners.ShowHelpListener;
import pwr.billsmanagement.bills.edition.products.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.view.DefineProductViewCreator;
import pwr.billsmanagement.bills.edition.view.FinalProductViewCreator;

/**
 * Created by Squier on 23.05.2017.
 */
public class DefineParams implements ListenerParams {
    private CreatorsParams creatorsParams;
    private ShredProductAssembler assembler;
    private ViewParams viewParams;
    private ShowHelpListener helpListener;

    public DefineParams(ViewParams viewParams, CreatorsParams creatorsParams, ShredProductAssembler assembler,
                        ShowHelpListener helpListener) {
        this.creatorsParams = creatorsParams;
        this.assembler = assembler;
        this.helpListener = helpListener;
        this.viewParams = viewParams;
    }

    public CreatorsParams getCreatorsParams() {
        return creatorsParams;
    }

    public ShredProductAssembler getAssembler() {
        return assembler;
    }

    public ShowHelpListener getHelpListener() {
        return helpListener;
    }

    public ViewParams getViewParams() {
        return viewParams;
    }
}
