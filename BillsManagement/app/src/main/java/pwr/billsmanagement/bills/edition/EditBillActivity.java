package pwr.billsmanagement.bills.edition;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.listeners.AcceptAllProductsListener;
import pwr.billsmanagement.bills.edition.view.DefineProductRowCreator;
import pwr.billsmanagement.ocr.parsers.Product;

/**
 * Created by Squier on 08.05.2017.
 */

public class EditBillActivity extends Activity {

    private enum SelectedOption {
        SELECT_NAMES, SELECT_TOTAL_PRICE, SELECT_UNIT_PRICE, SELECT_AS_GARBAGE
    }

    private LinearLayout productList;
    private ImageButton acceptAll, takePhotoAgain;
    private Button markAsProductName, markAsProductTotalPrice, markAsProductUnitPrice, markAsGarbage;

    private ChosenOption chosenOption;
    private DefineProductRowCreator creator;

    private ArrayList<Product> products;
    private String[] defineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_define_product);

        initView();
        initResources();
        readPassedData();
        createCustomList();
        setListeners();
    }

    private void initResources() {
        defineOptions = getResources().getStringArray(R.array.define_options);
    }

    private void createCustomList() {

        chosenOption = new ChosenOption();
        creator = new DefineProductRowCreator(
                getApplicationContext(),
                chosenOption,
                new ArrayList<>()
        );

        int id = 1;

        for (Product product : products) {
            productList.addView(creator.getProductRow(product, id++));
        }

    }

    private void readPassedData() {
        Bundle extras = getIntent().getExtras();
        String productJson = extras.getString("products_json");
        Gson gson = new Gson();
        products = gson.fromJson(productJson, new TypeToken<ArrayList<Product>>() {
        }.getType());
    }

    private void setListeners() {

        acceptAll.setOnClickListener(new AcceptAllProductsListener(
                creator,
                new ShredProductAssembler(defineOptions),
                new LayoutHandle(findViewById(R.id.optionButtons), findViewById(R.id.productList))
        ).setContext(getApplicationContext()));

        takePhotoAgain.setOnClickListener(v -> {
            //TODO go back to camera activity and repeat ocr stuff
        });

        markAsProductName.setOnClickListener(v -> {
            chosenOption.setColorPair(DefineProductRowCreator.ColorPair.GREEN);
            chosenOption.setLabelText(defineOptions[DefineProductRowCreator.NAME]);
//            indicateSelectedOption(SelectedOption.SELECT_NAMES);
        });

        markAsProductTotalPrice.setOnClickListener(v -> {
            chosenOption.setColorPair(DefineProductRowCreator.ColorPair.BLUE);
            chosenOption.setLabelText(defineOptions[DefineProductRowCreator.TOTAL_PRICE]);
//            indicateSelectedOption(SelectedOption.SELECT_TOTAL_PRICE);
        });
        
        markAsProductUnitPrice.setOnClickListener(v -> {
            chosenOption.setColorPair(DefineProductRowCreator.ColorPair.RED);
            chosenOption.setLabelText(defineOptions[DefineProductRowCreator.UNIT_PRICE]);
//            indicateSelectedOption(SelectedOption.SELECT_UNIT_PRICE);
        });
        
        markAsGarbage.setOnClickListener(v -> {
            chosenOption.setColorPair(DefineProductRowCreator.ColorPair.GRAY);
            chosenOption.setLabelText(defineOptions[DefineProductRowCreator.IGNORE]);
//            indicateSelectedOption(SelectedOption.SELECT_AS_GARBAGE);
        });
    }

    //TODO came up with some clever way to mark active option
    private void indicateSelectedOption(SelectedOption option) {
        switch (option) {
            case SELECT_NAMES: {
                setColors(
                        ContextCompat.getColor(getApplicationContext(), R.color.selected_green),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_blue),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_red),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_gray)
                );
                break;
            }
            case SELECT_TOTAL_PRICE: {
                setColors(
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_green),
                        ContextCompat.getColor(getApplicationContext(), R.color.selected_blue),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_red),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_gray)
                );
                break;
            }
            case SELECT_UNIT_PRICE: {
                setColors(
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_green),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_blue),
                        ContextCompat.getColor(getApplicationContext(), R.color.selected_red),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_gray)
                );
                break;
            }
            case SELECT_AS_GARBAGE: {
                setColors(
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_green),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_blue),
                        ContextCompat.getColor(getApplicationContext(), R.color.unselected_red),
                        ContextCompat.getColor(getApplicationContext(), R.color.selected_gray)
                );
                break;
            }
        }
    }

    private void setColors(int nameColor, int totalPriceColor, int unitPriceColor, int garbageColor) {
        markAsProductName.setBackgroundColor(nameColor);
        markAsProductTotalPrice.setBackgroundColor(totalPriceColor);
        markAsProductUnitPrice.setBackgroundColor(unitPriceColor);
        markAsGarbage.setBackgroundColor(garbageColor);
    }

    private void initView() {
        productList = (LinearLayout) findViewById(R.id.productList);
        acceptAll = (ImageButton) findViewById(R.id.acceptAll);
        takePhotoAgain = (ImageButton) findViewById(R.id.takePhotoAgain);
        markAsProductName = (Button) findViewById(R.id.selectName);
        markAsProductTotalPrice = (Button) findViewById(R.id.selectPrice);
        markAsProductUnitPrice = (Button) findViewById(R.id.selectUnitPrice);
        markAsGarbage = (Button) findViewById(R.id.selectAsNothing);

        acceptAll.setImageResource(R.drawable.ic_accept_white);
        takePhotoAgain.setImageResource(R.drawable.ic_take_photo_white);
    }

    public class LayoutHandle {
        public LinearLayout optionButtons;
        public LinearLayout productList;

        public LayoutHandle(View optionButtons, View productList) {
            this.optionButtons = (LinearLayout) optionButtons;
            this.productList = (LinearLayout) productList;
        }
    }

}
