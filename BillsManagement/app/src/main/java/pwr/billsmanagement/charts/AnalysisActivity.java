package pwr.billsmanagement.charts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.Calendar;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.menu.Menu;
import pwr.billsmanagement.ocr.OCRActivity;

public class AnalysisActivity extends AppCompatActivity implements OnItemClickListener{

    private String selectedCategory;

    private String[] mMenuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerListener;
    private Toolbar toolbar;
    private TextView tvDesc, tvDate, tvDateFrom, tvDateTo;
    private EditText etDateFrom, etDateTo;

    private int mStartYear, mEndYear, mStartMonth, mEndMonth, mStartDay, mEndDay;
    static final int DATE_DIALOG_FROM = 0;
    static final int DATE_DIALOG_TO = 1;

    // charts data
    Data mData = new Data();
    BarChart mBarChart;
    private OnChartValueSelectedListener chartListener;

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
        getSupportActionBar().setTitle("Analiza wydatków");

        tvDesc = (TextView) findViewById(R.id.text_desc);
        tvDate = (TextView) findViewById(R.id.text_range);
        tvDateFrom = (TextView) findViewById(R.id.text_range_start);
        tvDateTo = (TextView) findViewById(R.id.text_range_end);
        etDateFrom = (EditText) findViewById(R.id.etDate1);
        etDateTo = (EditText) findViewById(R.id.etDate2);

        // CHART
        mBarChart = (BarChart) findViewById(R.id.myBarChart);
        mData.testData(); // zmienić na pobieranie z bazy

        mBarChart.setData(mData.data);


        mBarChart.setDescription("Sumy wydatków w podziale na kategorie");
        mBarChart.animateY(3000);
        mBarChart.getLegend().setEnabled(false);

        chartListener = new OnChartValueSelectedListener() {
            // click on the chart's bar
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                selectedCategory = mBarChart.getData().getXVals().get(e.getXIndex());
                //Toast.makeText(getApplicationContext(), selectedCateghory, Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(AnalysisActivity.this, CategoryDetails.class);
                mIntent.putExtra("category", selectedCategory);
                startActivity(mIntent);
            }

            @Override
            public void onNothingSelected() {
            }
        };
        mBarChart.setOnChartValueSelectedListener(chartListener);

        // DRAWER
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

        // date pickers

        etDateFrom = (EditText) findViewById(R.id.etDate1);
        etDateTo = (EditText) findViewById(R.id.etDate2);

        etDateFrom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_FROM);
            }
        }
        );

        etDateTo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_TO);
            }}
        );

        // get the current date
        final Calendar c = Calendar.getInstance();
        mStartYear = c.get(Calendar.YEAR);
        mStartMonth = c.get(Calendar.MONTH) -1;
        mStartDay = c.get(Calendar.DAY_OF_MONTH);

        mEndYear = c.get(Calendar.YEAR);
        mEndMonth = c.get(Calendar.MONTH);
        mEndDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date
        updateDisplay();

    }

    private void updateDisplay() {
        this.etDateFrom.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mStartDay).append("-")
                        .append(mStartMonth + 1).append("-")
                        .append(mStartYear).append(" "));
        this.etDateTo.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mEndDay).append("-")
                        .append(mEndMonth + 1).append("-")
                        .append(mEndYear).append(" "));
    };

    private DatePickerDialog.OnDateSetListener mFromDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mStartYear = year;
                    mStartMonth = monthOfYear;
                    mStartDay = dayOfMonth;
                    updateDisplay();
                }
            };

    private DatePickerDialog.OnDateSetListener mToDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mEndYear = year;
                    mEndMonth = monthOfYear;
                    mEndDay = dayOfMonth;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_FROM:
                return new DatePickerDialog(this,
                        mFromDateSetListener,
                        mStartYear, mStartMonth, mStartDay);
            case DATE_DIALOG_TO:
                return new DatePickerDialog(this,
                        mToDateSetListener,
                        mEndYear, mEndMonth, mEndDay);
        }
        return null;
    }


    // drawer
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
                mIntent = new Intent(this, Menu.class);
                break;
        }
        startActivity(mIntent);
    }
}