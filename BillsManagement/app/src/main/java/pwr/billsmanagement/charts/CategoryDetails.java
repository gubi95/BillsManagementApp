package pwr.billsmanagement.charts;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import java.util.Locale;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.ocr.OCRActivity;
import pwr.billsmanagement.menu.Menu;

public class CategoryDetails extends AppCompatActivity implements OnItemClickListener{


    private Toolbar toolbar;
    private TextView tvSpendLabel;
    private TextView tvSpendText;
    private TextView tvLimitIndicator;
    private TextView tvCategoryLabel;
    private TextView tvLimit;
    private EditText etLimit;

    public CategoryDetails() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        tvLimitIndicator = (TextView) findViewById(R.id.limit_indicator);
        tvCategoryLabel = (TextView) findViewById(R.id.text_category);
        TextView tvCategoryText = (TextView) findViewById(R.id.text_category_name);
        tvSpendLabel = (TextView) findViewById(R.id.text_spend);
        tvSpendText = (TextView) findViewById(R.id.spend_value);
        tvLimit = (TextView) findViewById(R.id.text_spend_limit);
        etLimit = (EditText) findViewById(R.id.limit_value);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO
    }
}
