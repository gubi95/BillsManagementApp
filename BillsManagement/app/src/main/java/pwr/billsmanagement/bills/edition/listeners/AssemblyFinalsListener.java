package pwr.billsmanagement.bills.edition.listeners;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.bills.edition.products.FinalProductAssembler;
import pwr.billsmanagement.bills.edition.tasks.AssemblyFinalsTask;
import pwr.billsmanagement.bills.edition.view.FinalProductView;

/**
 * Created by Squier on 22.05.2017.
 */

public class AssemblyFinalsListener implements View.OnClickListener, AcceptAllListener {

    private ArrayList<FinalProductView> finalProductViews;
    private ArrayList<FinalProduct> finalProducts;
    private FinalProductAssembler assembler;
    private Context context;
    private EditText shopName;

    public AssemblyFinalsListener(AcceptAllListenerFactory.EditParams params) {
        finalProducts = params.getFinalProducts();
        finalProductViews = params.getFinalProductViews();
        assembler = params.getAssembler();
        context = params.getContext();
        shopName = params.getShopName();
    }

    @Override
    public void onClick(View v) {

        AssemblyFinalsTask task = new AssemblyFinalsTask();
        task.setAssembler(assembler);
        task.setContext(context);
        task.setFinalProducts(finalProducts);
        task.setShopName(shopName.getText().toString());
        task.execute(finalProductViews);

    }

}