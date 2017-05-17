package pwr.billsmanagement.bills.edition.listeners;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.tasks.AssemblyProductsTask;
import pwr.billsmanagement.bills.edition.view.DefineProductRowCreator;

/**
 * Created by Squier on 17.05.2017.
 */

public class AcceptAllProductsListener implements View.OnClickListener {

    private DefineProductRowCreator creator;
    private ShredProductAssembler assembler;
    private EditBillActivity.LayoutHandle layoutHandle;
    private Context context;

    public AcceptAllProductsListener(DefineProductRowCreator creator, ShredProductAssembler shredProductAssembler, EditBillActivity.LayoutHandle layoutHandle) {
        this.creator = creator;
        this.layoutHandle = layoutHandle;
        assembler = shredProductAssembler;
    }

    @Override
    public void onClick(View v) {
        AssemblyProductsTask assemblyProductsTask = new AssemblyProductsTask();
        assemblyProductsTask.setAssembler(assembler);
        assemblyProductsTask.setAssembledProducts(new ArrayList<>());
        assemblyProductsTask.setLayoutHandle(layoutHandle);
        assemblyProductsTask.setContext(context);
        assemblyProductsTask.execute(creator.getShredProducts());
    }

    public AcceptAllProductsListener setContext(Context context) {
        this.context = context;
        return this;
    }
}
