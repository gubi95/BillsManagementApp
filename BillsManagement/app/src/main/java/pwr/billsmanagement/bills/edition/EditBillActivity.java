package pwr.billsmanagement.bills.edition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.listeners.AcceptAllListenerFactory;
import pwr.billsmanagement.bills.edition.listeners.ShowHelpListener;
import pwr.billsmanagement.bills.edition.products.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.view.DefineProductViewCreator;
import pwr.billsmanagement.bills.edition.view.FinalProductViewCreator;
import pwr.billsmanagement.ocr.parsers.OcrProduct;

import static pwr.billsmanagement.bills.edition.listeners.AcceptAllListenerFactory.*;

/**
 * Created by Squier on 08.05.2017.
 */

public class EditBillActivity extends Activity {

    private EditBillActivityView mView;

    private ChosenOption chosenOption;
    private DefineProductViewCreator defineProductViewCreator;
    private FinalProductViewCreator finalProductViewCreator;

    private ShowHelpListener showHelpListener;

    private ArrayList<OcrProduct> ocrProducts;
    private String[] defineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_define_product);

        initView();
        initResources();
        initListeners();
        initCreators();
        readPassedData();
        createCustomList();
    }

    private void initCreators() {
        chosenOption = new ChosenOption();
        defineProductViewCreator = new DefineProductViewCreator(
                EditBillActivity.this,
                chosenOption,
                new ArrayList<>()
        );

        finalProductViewCreator = new FinalProductViewCreator(
                EditBillActivity.this,
                new ArrayList<>()
        );
    }

    private void initListeners() {
        showHelpListener = new ShowHelpListener();
        showHelpListener.setInflater((LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE));
        showHelpListener.setHelpMessage(this.getString(R.string.edit_bill_show_help_page1));
        showHelpListener.setParentView(findViewById(R.id.defineProductMainLayout));
        showHelpListener.setWindowManager(getWindowManager());
    }

    private void initView() {
        Logger.i("Init view");
        mView = new EditBillActivityView(
                findViewById(R.id.productList),
                findViewById(R.id.showHelp),
                findViewById(R.id.shopName),
                findViewById(R.id.addProduct),
                findViewById(R.id.acceptAll),
                findViewById(R.id.takePhotoAgain),
                findViewById(R.id.selectName),
                findViewById(R.id.selectPrice),
                findViewById(R.id.selectUnitPrice),
                findViewById(R.id.selectAsNothing)
        );

        mView.setIcons();

    }

    private void initResources() {
        Logger.i("Init resources");
        defineOptions = getResources().getStringArray(R.array.define_options);
    }

    private void readPassedData() {
        Logger.i("Read pass data");
        Bundle extras = getIntent().getExtras();
        String productJson = extras.getString("products_json");
        Gson gson = new Gson();
        ocrProducts = gson.fromJson(productJson, new TypeToken<ArrayList<OcrProduct>>() {
        }.getType());
    }

    private void createCustomList() {
        Logger.i("Create custom list");
        CreateCustomListAsync createCustomListAsync = new CreateCustomListAsync();
        createCustomListAsync.execute();
    }

    private enum SelectedOption {
        SELECT_NAMES, SELECT_TOTAL_PRICE, SELECT_UNIT_PRICE, SELECT_AS_GARBAGE
    }

    public class LayoutHandle {
        public LinearLayout optionButtons;
        public LinearLayout productList;

        LayoutHandle(View optionButtons, View productList) {
            this.optionButtons = (LinearLayout) optionButtons;
            this.productList = (LinearLayout) productList;
        }
    }

    private class EditBillActivityView {
        LinearLayout productList;
        EditText shopName;
        ImageButton showHelp, addProduct, acceptAll, takePhotoAgain;
        Button markAsProductName, markAsProductTotalPrice, markAsProductUnitPrice, markAsGarbage;

        EditBillActivityView(View productList, View showHelp, View shopName, View addProduct, View acceptAll, View takePhotoAgain,
                             View markAsProductName, View markAsProductTotalPrice, View markAsProductUnitPrice,
                             View markAsGarbage) {
            this.productList = (LinearLayout) productList;
            this.showHelp = (ImageButton) showHelp;
            this.shopName = (EditText) shopName;
            this.addProduct = (ImageButton) addProduct;
            this.acceptAll = (ImageButton) acceptAll;
            this.takePhotoAgain = (ImageButton) takePhotoAgain;
            this.markAsProductName = (Button) markAsProductName;
            this.markAsProductTotalPrice = (Button) markAsProductTotalPrice;
            this.markAsProductUnitPrice = (Button) markAsProductUnitPrice;
            this.markAsGarbage = (Button) markAsGarbage;
        }

        void setIcons() {
            showHelp.setImageResource(R.drawable.ic_show_help);
            addProduct.setImageResource(R.drawable.ic_add_new_product_white);
            acceptAll.setImageResource(R.drawable.ic_accept_white);
            takePhotoAgain.setImageResource(R.drawable.ic_take_photo_white);
        }

        private void setOptionButtonsColors(int nameColor, int totalPriceColor, int unitPriceColor, int garbageColor) {
            markAsProductName.setTextColor(nameColor);
            markAsProductTotalPrice.setTextColor(totalPriceColor);
            markAsProductUnitPrice.setTextColor(unitPriceColor);
            markAsGarbage.setTextColor(garbageColor);
        }

        private void indicateSelectedOption(SelectedOption option) {
            switch (option) {
                case SELECT_NAMES: {
                    setOptionButtonsColors(
                            ContextCompat.getColor(getApplicationContext(), R.color.selected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button)
                    );
                    break;
                }
                case SELECT_TOTAL_PRICE: {
                    setOptionButtonsColors(
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.selected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button)
                    );
                    break;
                }
                case SELECT_UNIT_PRICE: {
                    setOptionButtonsColors(
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.selected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button)
                    );
                    break;
                }
                case SELECT_AS_GARBAGE: {
                    setOptionButtonsColors(
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.unselected_option_button),
                            ContextCompat.getColor(getApplicationContext(), R.color.selected_option_button)
                    );
                    break;
                }
            }
        }

        void setListeners() {

            showHelp.setOnClickListener(showHelpListener);

            ListenerParams params = new DefineParams(
                    EditBillActivity.this,
                    defineProductViewCreator,
                    finalProductViewCreator,
                    new ShredProductAssembler(defineOptions),
                    new LayoutHandle(findViewById(R.id.optionButtons), findViewById(R.id.productList)),
                    addProduct,
                    acceptAll,
                    showHelpListener,
                    shopName
            );

            acceptAll.setOnClickListener(AcceptAllListenerFactory.getListener(ListenerType.DEFINE, params));

            takePhotoAgain.setOnClickListener(v -> {
                //TODO go back to camera activity and repeat ocr stuff
            });

            markAsProductName.setOnClickListener(v -> {
                chosenOption.setColorPair(DefineProductViewCreator.ColorPair.GREEN);
                chosenOption.setLabelText(defineOptions[DefineProductViewCreator.NAME]);
                indicateSelectedOption(SelectedOption.SELECT_NAMES);
            });

            markAsProductTotalPrice.setOnClickListener(v -> {
                chosenOption.setColorPair(DefineProductViewCreator.ColorPair.BLUE);
                chosenOption.setLabelText(defineOptions[DefineProductViewCreator.TOTAL_PRICE]);
                indicateSelectedOption(SelectedOption.SELECT_TOTAL_PRICE);
            });

            markAsProductUnitPrice.setOnClickListener(v -> {
                chosenOption.setColorPair(DefineProductViewCreator.ColorPair.RED);
                chosenOption.setLabelText(defineOptions[DefineProductViewCreator.UNIT_PRICE]);
                indicateSelectedOption(SelectedOption.SELECT_UNIT_PRICE);
            });

            markAsGarbage.setOnClickListener(v -> {
                chosenOption.setColorPair(DefineProductViewCreator.ColorPair.GRAY);
                chosenOption.setLabelText(defineOptions[DefineProductViewCreator.IGNORE]);
                indicateSelectedOption(SelectedOption.SELECT_AS_GARBAGE);
            });
        }
    }

    private class CreateCustomListAsync extends AsyncTask<Void, Void, ArrayList<View>> {

        private ProgressDialog progressDialog;
        private Context context = EditBillActivity.this;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context,
                    context.getString(R.string.edit_bill_create_view_progress_title),
                    context.getString(R.string.edit_bill_create_view_progress_message)
            );
        }

        @Override
        protected ArrayList<View> doInBackground(Void... params) {
            int id = 1;

            ArrayList<View> views = new ArrayList<>();
            for (OcrProduct ocrProduct : ocrProducts) {
                views.add(defineProductViewCreator.getProductRow(ocrProduct, id++));
            }
            return views;
        }

        @Override
        protected void onPostExecute(ArrayList<View> views) {
            for (View view : views) {
                mView.productList.addView(view);
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Logger.i("Set listeners");
            mView.setListeners();
        }
    }

}