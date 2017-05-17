package pwr.billsmanagement.bills.edition.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.AssembledProduct;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.ShredProduct;
import pwr.billsmanagement.bills.edition.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.view.FinalProductViewCreator;

/**
 * Created by Squier on 17.05.2017.
 */

public class AssemblyProductsTask extends AsyncTask<ArrayList<ShredProduct>, Void, ArrayList<AssembledProduct>> {

    private ShredProductAssembler assembler;
    private ArrayList<AssembledProduct> assembledProducts;
    private EditBillActivity.LayoutHandle layoutHandle;
    private Context context;

    @Override
    protected ArrayList<AssembledProduct> doInBackground(ArrayList<ShredProduct>... params) {
        for (ShredProduct shred : params[0]) {
            assembler.assembly(shred);
            assembledProducts.add(new AssembledProduct(
                    assembler.getProductName(),
                    assembler.getProductUnitPrice(),
                    assembler.getProductTotalPrice()
                    ));
        }
        return assembledProducts;
    }

    @Override
    protected void onPostExecute(ArrayList<AssembledProduct> assembledProducts) {
        layoutHandle.optionButtons.removeAllViews();
        layoutHandle.productList.removeAllViews();

        FinalProductViewCreator creator = new FinalProductViewCreator(context);
        int id = 1;

        for (AssembledProduct product : assembledProducts) {
            layoutHandle.productList.addView(creator.getRowView(product, id++));
        }

    }

    public void setAssembler(ShredProductAssembler assembler) {
        this.assembler = assembler;
    }

    public void setAssembledProducts(ArrayList<AssembledProduct> assembledProducts) {
        this.assembledProducts = assembledProducts;
    }

    public void setLayoutHandle(EditBillActivity.LayoutHandle layoutHandle) {
        this.layoutHandle = layoutHandle;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
