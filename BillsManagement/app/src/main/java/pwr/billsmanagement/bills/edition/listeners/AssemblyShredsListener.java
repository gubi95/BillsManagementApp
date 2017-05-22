package pwr.billsmanagement.bills.edition.listeners;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.products.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.tasks.AssemblyShredsTask;
import pwr.billsmanagement.bills.edition.view.DefineProductViewCreator;
import pwr.billsmanagement.bills.edition.view.FinalProductViewCreator;

/**
 * Created by Squier on 17.05.2017.
 */
public class AssemblyShredsListener implements View.OnClickListener, AcceptAllListener {

    private DefineProductViewCreator defineProductViewCreator;
    private ShredProductAssembler assembler;
    private EditBillActivity.LayoutHandle layoutHandle;

    private FinalProductViewCreator finalProductViewCreator;
    private Context context;
    private ShowHelpListener helpListener;
    private ImageButton addProduct, acceptAll;
    private EditText shopName;

    public AssemblyShredsListener(AcceptAllListenerFactory.DefineParams params) {
        defineProductViewCreator = params.getDefineCreator();
        assembler = params.getAssembler();
        layoutHandle = params.getLayoutHandle();

        finalProductViewCreator = params.getFinalCreator();
        context = params.getContext();
        helpListener = params.getHelpListener();
        addProduct = params.getAddNewProduct();
        acceptAll = params.getAcceptAll();
        shopName = params.getShopName();
    }

    @Override
    public void onClick(View v) {
        helpListener.setHelpMessage(context.getString(R.string.edit_bill_show_help_page2));
        addProduct.setVisibility(View.VISIBLE);

        AssemblyShredsTask assemblyShredsTask = new AssemblyShredsTask();
        assemblyShredsTask.setAssembler(assembler);
        assemblyShredsTask.setAssembledProducts(new ArrayList<>());
        assemblyShredsTask.setLayoutHandle(layoutHandle);
        assemblyShredsTask.setContext(context);
        assemblyShredsTask.setAcceptAll(acceptAll);
        assemblyShredsTask.setCreator(finalProductViewCreator);
        assemblyShredsTask.setShopName(shopName);
        assemblyShredsTask.execute(defineProductViewCreator.getShredProducts());
    }

}
