package pwr.billsmanagement.bills.edition.listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.products.ShredProductAssembler;
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
    private ShowHelpListener helpListener;
    private ImageButton addProduct;

    public AcceptAllProductsListener(DefineProductRowCreator creator, ShredProductAssembler assembler, EditBillActivity.LayoutHandle layoutHandle) {
        this.creator = creator;
        this.assembler = assembler;
        this.layoutHandle = layoutHandle;
    }

    @Override
    public void onClick(View v) {
        helpListener.setHelpMessage(context.getString(R.string.edit_bill_show_help_page2));
        addProduct.setVisibility(View.VISIBLE);

        AssemblyProductsTask assemblyProductsTask = new AssemblyProductsTask();
        assemblyProductsTask.setAssembler(assembler);
        assemblyProductsTask.setAssembledProducts(new ArrayList<>());
        assemblyProductsTask.setLayoutHandle(layoutHandle);
        assemblyProductsTask.setContext(context);
        assemblyProductsTask.execute(creator.getShredProducts());
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHelpListener(ShowHelpListener helpListener) {
        this.helpListener = helpListener;
    }

    public void setAddProduct(ImageButton addProduct) {
        this.addProduct = addProduct;
    }
}
