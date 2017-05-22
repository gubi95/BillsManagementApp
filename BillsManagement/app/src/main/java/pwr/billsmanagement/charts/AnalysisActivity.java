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

public class AnalysisActivity extends AppCompatActivity implements OnItemClickListener{

    private String[] mMenuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerListener;
    private Toolbar toolbar;
    private TextView tvDesc, tvDate, tvDateFrom, tvDateTo;
    private EditText etDateFrom, etDateTo;


    // charts data
    Data mData = new Data();
    BarChart mBarChart;

    public AnalysisActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        mMenuOptions = getResources().getStringArray(R.array.menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        tvDesc = (TextView) findViewById(R.id.text_desc);
        tvDate = (TextView) findViewById(R.id.text_range);
        tvDateFrom = (TextView) findViewById(R.id.text_range_start);
        tvDateTo = (TextView) findViewById(R.id.text_range_end);
        etDateFrom = (EditText) findViewById(R.id.etDate1);
        etDateTo = (EditText) findViewById(R.id.etDate2);





        // CHART
        mBarChart = (BarChart) findViewById(R.id.myBarChart);
        mData.testData();

        if(mData.data == null) Log.d("datanull", " data to null");
        if(mBarChart == null) Log.d("chartnull", " chart to null");
        Log.d("chart id", Integer.toString(R.id.myBarChart));

        mBarChart.setData(mData.data);
        mBarChart.setDescription("Sumy wydatków w podziale na kategorie"); //no ale działa
        mBarChart.animateY(3000);



        mDrawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMenuOptions));
        mDrawerList.setOnItemClickListener(this);

        drawerListener = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView){
                // TODO
                // action performed when drawer closed
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerOpened(View drawerView){
                //TODO
                // action performed when drawer opened
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(drawerListener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO
        //actions performed when menu items are clicked

        selectItem(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void selectItem(int position) {
        Intent mIntent = null;
        //FragmentActivity fragmentActivity = null;
        switch(position) {
            case 0:
                mIntent = new Intent(this, Menu.class);
                break;
            case 1:
                mIntent = new Intent(this, OCRActivity.class);
                break;
            case 2:
                mIntent = new Intent(this, EditBillActivity.class);
                break;
            case 3:
                mIntent = new Intent(this, AnalysisActivity.class);
                break;
            case 4:
                mIntent = new Intent(Intent.ACTION_MAIN);
                mIntent.addCategory(Intent.CATEGORY_HOME);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            default :
                mIntent = new Intent(this, Menu.class); // Activity_0 as default
                break;
        }
        startActivity(mIntent);
    }
}
