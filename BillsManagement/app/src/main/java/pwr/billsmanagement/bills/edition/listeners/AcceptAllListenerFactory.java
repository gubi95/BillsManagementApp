package pwr.billsmanagement.bills.edition.listeners;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.bills.edition.products.FinalProductAssembler;
import pwr.billsmanagement.bills.edition.products.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.view.DefineProductViewCreator;
import pwr.billsmanagement.bills.edition.view.FinalProductView;
import pwr.billsmanagement.bills.edition.view.FinalProductViewCreator;

/**
 * Created by Squier on 22.05.2017.
 */

public class AcceptAllListenerFactory {

    public enum ListenerType {
        DEFINE, EDIT
    }

    public static AcceptAllListener getListener(ListenerType type, ListenerParams params) {
        switch (type) {
            case DEFINE: {

                AssemblyShredsListener listener = new AssemblyShredsListener((DefineParams) params);
                return listener;
            }
            case EDIT: {
                AssemblyFinalsListener listener = new AssemblyFinalsListener((EditParams) params);
                return listener;
            }
            default:
                return null;
        }
    }

    public interface ListenerParams {}

    public static class DefineParams implements ListenerParams {
        private Context context;
        private DefineProductViewCreator defineCreator;
        private FinalProductViewCreator finalCreator;
        private ShredProductAssembler assembler;
        private EditBillActivity.LayoutHandle layoutHandle;
        private ImageButton addNewProduct;
        private ImageButton acceptAll;
        private ShowHelpListener helpListener;
        private EditText shopName;

        public DefineParams(Context context, DefineProductViewCreator defineCreator,
                            FinalProductViewCreator finalCreator, ShredProductAssembler assembler,
                            EditBillActivity.LayoutHandle layoutHandle, ImageButton addNewProduct,
                            ImageButton acceptAll, ShowHelpListener helpListener, EditText shopName) {
            this.context = context;
            this.defineCreator = defineCreator;
            this.finalCreator = finalCreator;
            this.assembler = assembler;
            this.layoutHandle = layoutHandle;
            this.addNewProduct = addNewProduct;
            this.acceptAll = acceptAll;
            this.helpListener = helpListener;
            this.shopName = shopName;
        }

        public Context getContext() {
            return context;
        }

        public DefineProductViewCreator getDefineCreator() {
            return defineCreator;
        }

        public FinalProductViewCreator getFinalCreator() {
            return finalCreator;
        }

        public ShredProductAssembler getAssembler() {
            return assembler;
        }

        public EditBillActivity.LayoutHandle getLayoutHandle() {
            return layoutHandle;
        }

        public ImageButton getAddNewProduct() {
            return addNewProduct;
        }

        public ShowHelpListener getHelpListener() {
            return helpListener;
        }

        public ImageButton getAcceptAll() {
            return acceptAll;
        }

        public EditText getShopName() {
            return shopName;
        }
    }

    public static class EditParams implements ListenerParams {
        private ArrayList<FinalProductView> finalProductViews;
        private ArrayList<FinalProduct> finalProducts;
        private FinalProductAssembler assembler;
        private EditText shopName;
        private Context context;

        public EditParams(ArrayList<FinalProductView> finalProductViews,
                          ArrayList<FinalProduct> finalProducts, FinalProductAssembler assembler,
                          EditText shopName, Context context) {
            this.finalProductViews = finalProductViews;
            this.finalProducts = finalProducts;
            this.assembler = assembler;
            this.shopName = shopName;
            this.context = context;
        }

        public ArrayList<FinalProductView> getFinalProductViews() {
            return finalProductViews;
        }

        public ArrayList<FinalProduct> getFinalProducts() {
            return finalProducts;
        }

        public FinalProductAssembler getAssembler() {
            return assembler;
        }

        public Context getContext() {
            return context;
        }

        public EditText getShopName() {
            return shopName;
        }
    }
}
