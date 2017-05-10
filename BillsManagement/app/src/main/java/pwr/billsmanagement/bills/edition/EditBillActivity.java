package pwr.billsmanagement.bills.edition;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.adapters.ShopProductAdapter;
import pwr.billsmanagement.ocr.parsers.ShopProduct;

/**
 * Created by Squier on 08.05.2017.
 */

public class EditBillActivity extends Activity {

    private ListView productList;
    private ImageButton addNewProduct, acceptAll, takePhotoAgain;

    private ArrayList<ShopProduct> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_main_products);

        initView();
        setListeners();
        readPassedData();
        setListAdapter();
    }

    private void setListAdapter() {
        productList.setAdapter(new ShopProductAdapter(getApplicationContext(), 0, products));
    }

    private void readPassedData() {
        Bundle extras = getIntent().getExtras();
        String productJson = extras.getString("products_json");
        Gson gson = new Gson();
        products = gson.fromJson(productJson, new TypeToken<ArrayList<ShopProduct>>(){}.getType());
    }

    private void setListeners() {
        addNewProduct.setOnClickListener(v -> {
            //TODO expanding list view
        });

        acceptAll.setOnClickListener(v -> {
            //TODO accepting products from list, create new objects and save'em in local/external db
        });

        takePhotoAgain.setOnClickListener(v -> {
            //TODO go back to camera activity and repeat ocr stuff
        });
    }

    private void initView() {
        productList     = (ListView)    findViewById(R.id.productList);
        addNewProduct   = (ImageButton) findViewById(R.id.addNewProduct);
        acceptAll       = (ImageButton) findViewById(R.id.acceptAll);
        takePhotoAgain  = (ImageButton) findViewById(R.id.takePhotoAgain);

        addNewProduct.setImageResource(R.drawable.ic_add_new_product_white);
        acceptAll.setImageResource(R.drawable.ic_accept_white);
        takePhotoAgain.setImageResource(R.drawable.ic_take_photo_white);
    }
}
