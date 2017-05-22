package pwr.billsmanagement.bills.edition.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.BillEntity;
import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.bills.edition.products.FinalProductAssembler;
import pwr.billsmanagement.bills.edition.view.FinalProductView;

/**
 * Created by Squier on 22.05.2017.
 */

public class AssemblyFinalsTask extends AsyncTask<ArrayList<FinalProductView>, Void, Void> {

    private ProgressDialog progressDialog;

    private ArrayList<FinalProduct> finalProducts;
    private FinalProductAssembler assembler;
    private Context context;
    private String shopName;

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(
                context,
                context.getString(R.string.assembly_finals_progress_title),
                context.getString(R.string.assembly_finals_progress_message)
        );
    }

    @Override
    protected Void doInBackground(ArrayList<FinalProductView>... params) {
        for (FinalProductView view : params[0]) {
            finalProducts.add(assembler.assembly(view));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        BillEntity bill = new BillEntity(shopName, finalProducts);
        Logger.i("Read bill: " + bill.toString());
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setFinalProducts(ArrayList<FinalProduct> finalProducts) {
        this.finalProducts = finalProducts;
    }

    public void setAssembler(FinalProductAssembler assembler) {
        this.assembler = assembler;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
