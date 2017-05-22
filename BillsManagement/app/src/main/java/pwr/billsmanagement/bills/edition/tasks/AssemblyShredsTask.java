package pwr.billsmanagement.bills.edition.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageButton;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.listeners.AcceptAllListenerFactory;
import pwr.billsmanagement.bills.edition.products.AssembledProduct;
import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.bills.edition.products.FinalProductAssembler;
import pwr.billsmanagement.bills.edition.products.ShredProduct;
import pwr.billsmanagement.bills.edition.products.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.view.FinalProductViewCreator;

import static pwr.billsmanagement.bills.edition.listeners.AcceptAllListenerFactory.*;

/**
 * Created by Squier on 17.05.2017.
 */
public class AssemblyShredsTask extends AsyncTask<ArrayList<ShredProduct>, Void, ArrayList<AssembledProduct>> {

    private ShredProductAssembler assembler;
    private ArrayList<AssembledProduct> assembledProducts;
    private EditBillActivity.LayoutHandle layoutHandle;
    private FinalProductViewCreator creator;
    private Context context;
    private ImageButton acceptAll;
    private EditText shopName;

    @Override
    protected ArrayList<AssembledProduct> doInBackground(ArrayList<ShredProduct>... params) {
        for (ShredProduct shred : params[0]) {
            assembler.assembly(shred);
            AssembledProduct assembledProduct = new AssembledProduct(
                    assembler.getProductName(),
                    assembler.getProductUnitPrice(),
                    assembler.getProductTotalPrice()
            );
            Logger.i("Assembled product: " + assembledProduct.toString());
            assembledProducts.add(assembledProduct);
        }
        return assembledProducts;
    }

    @Override
    protected void onPostExecute(ArrayList<AssembledProduct> assembledProducts) {
        layoutHandle.optionButtons.removeAllViews();
        layoutHandle.productList.removeAllViews();

        int id = 1;

        for (AssembledProduct product : assembledProducts) {
            layoutHandle.productList.addView(creator.getRowViewAndSave(product, id++));
        }

        setNewListener();
    }

    private void setNewListener() {
        ListenerParams params = new EditParams(
                creator.getFinalProductViews(),
                new ArrayList<>(),
                new FinalProductAssembler(),
                shopName,
                context
        );

        acceptAll.setOnClickListener(AcceptAllListenerFactory.getListener(ListenerType.EDIT, params));

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

    public void setCreator(FinalProductViewCreator creator) {
        this.creator = creator;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAcceptAll(ImageButton acceptAll) {
        this.acceptAll = acceptAll;
    }

    public void setShopName(EditText shopName) {
        this.shopName = shopName;
    }
}
