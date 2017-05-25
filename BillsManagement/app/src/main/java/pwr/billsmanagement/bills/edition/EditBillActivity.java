package pwr.billsmanagement.bills.edition;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.categories.CategoryAddView;
import pwr.billsmanagement.bills.edition.listeners.AcceptAllListenerFactory;
import pwr.billsmanagement.bills.edition.listeners.ShowHelpListener;
import pwr.billsmanagement.bills.edition.listeners.params.CreatorsParams;
import pwr.billsmanagement.bills.edition.listeners.params.DefineParams;
import pwr.billsmanagement.bills.edition.listeners.params.ListenerParams;
import pwr.billsmanagement.bills.edition.listeners.params.ViewParams;
import pwr.billsmanagement.bills.edition.products.ShredProductAssembler;
import pwr.billsmanagement.bills.edition.view.DefineProductViewCreator;
import pwr.billsmanagement.bills.edition.view.FinalProductViewCreator;
import pwr.billsmanagement.ocr.parsers.OcrProduct;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
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

    private CreatorsParams getCreatorParams() {
        CreatorsParams creatorsParams = new CreatorsParams(
                defineProductViewCreator, finalProductViewCreator
        );

        Map<String, Integer> creatorMap = new HashMap<>();
        creatorMap.put("DEFINE_CREATOR", 0);
        creatorMap.put("FINAL_CREATOR", 1);

        creatorsParams.setCreatorsMap(creatorMap);

        return creatorsParams;
    }

    private ViewParams getViewParams() {
        ViewParams viewParams = new ViewParams(
                EditBillActivity.this,
                new LayoutHandle(findViewById(R.id.optionButtons), findViewById(R.id.productList)),
                mView.addProduct, mView.acceptAll, mView.shopName
        );

        HashMap<String, Integer> viewMap = new HashMap<>();
        viewMap.put("ADD_PRODUCT", 0);
        viewMap.put("ACCEPT_ALL", 1);
        viewMap.put("SHOP_NAME", 2);

        viewParams.setViewMap(viewMap);

        return viewParams;
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
                    getViewParams(), getCreatorParams(),
                    new ShredProductAssembler(defineOptions),
                    showHelpListener
            );

            acceptAll.setOnClickListener(AcceptAllListenerFactory.getListener(ListenerType.DEFINE, params));

            addProduct.setOnClickListener(new ShowOptionsPopupLister());

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
                views.add(defineProductViewCreator.getProductRowAndSave(ocrProduct, id++));
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

    private class ShowOptionsPopupLister implements View.OnClickListener {

        private final String[] ADD_OPTIONS = EditBillActivity.this.getResources().getStringArray(R.array.add_options);

        @Override
        public void onClick(View v) {

            LayoutInflater inflater = (LayoutInflater) EditBillActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.bill_options_popup, null);

            ListView optionsList = getListView(popupView);
            PopupWindow options = getPopupWindow(popupView);
            setOnOptionClickListener(optionsList, options);
            showPopup(options);

        }

        @NonNull
        private PopupWindow getPopupWindow(View popupView) {
            return new PopupWindow(
                            popupView, getMetrics().widthPixels/2, WRAP_CONTENT
                    );
        }

        @NonNull
        private ListView getListView(View popupView) {
            ListView optionsList = (ListView) popupView.findViewById(R.id.addWhatList);
            optionsList.setAdapter(
                    new ArrayAdapter<>(
                            EditBillActivity.this, android.R.layout.simple_list_item_1, ADD_OPTIONS)
            );
            return optionsList;
        }

        private void showPopup(PopupWindow options) {
            options.setElevation(5.0f);

            int[] addLocation = new int[2];
            mView.addProduct.getLocationOnScreen(addLocation);

            options.showAtLocation(
                    findViewById(R.id.defineProductMainLayout),
                    Gravity.NO_GRAVITY, addLocation[0], addLocation[1]
            );
        }

        private void setOnOptionClickListener(ListView optionsList, PopupWindow options) {
            optionsList.setOnItemClickListener((parent, view, position, id) -> {
                CharSequence option = ((TextView) optionsList.getChildAt(position)).getText();
                Logger.i("OPTION: " + option);
                onClickSwitch(option);
                options.dismiss();
            });
        }

        private void onClickSwitch(CharSequence option) {
            final String CATEGORY = ADD_OPTIONS[0];
            final String PRODUCT = ADD_OPTIONS[1];

            if(option.toString().equals(CATEGORY)) {
                CategoryAddView categoryView = new CategoryAddView(
                        EditBillActivity.this.getWindowManager(), EditBillActivity.this
                );

                categoryView.getCategoryAddView();
                categoryView.showPopup(findViewById(R.id.defineProductMainLayout));

            } else if (option.toString().equals(PRODUCT)){

            }
        }

        private DisplayMetrics getMetrics() {
            WindowManager windowManager = EditBillActivity.this.getWindowManager();

            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            return metrics;
        }

    }
}