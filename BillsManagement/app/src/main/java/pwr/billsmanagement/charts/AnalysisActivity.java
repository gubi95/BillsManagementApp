package pwr.billsmanagement.charts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.BillEntity;
import pwr.billsmanagement.bills.edition.EditBillActivity;

import pwr.billsmanagement.bills.edition.products.FinalProduct;
import pwr.billsmanagement.billslist.BillsList;
import pwr.billsmanagement.db.creators.DBHandler;
import pwr.billsmanagement.ocr.OCRActivity;

public class AnalysisActivity extends AppCompatActivity {

    private String selectedCategory;
    private String login = "Przykładowy Login"; // z bazy
    private String[] mMenuOptions;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerList;
    private ActionBarDrawerToggle drawerListener;
    private Toolbar toolbar;
    private TextView tvDesc, tvDate, tvDateFrom, tvDateTo, tvUsername;
    private EditText etDateFrom, etDateTo;

    private int mStartYear, mEndYear, mStartMonth, mEndMonth, mStartDay, mEndDay;
    static final int DATE_DIALOG_FROM = 0;
    static final int DATE_DIALOG_TO = 1;

    // charts data
    Data mData = new Data();
    BarChart mBarChart;
    private OnChartValueSelectedListener chartListener;

    public DBHandler mydb;
    public AnalysisActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        mydb = new DBHandler(this);


        //drawer menu
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (NavigationView) findViewById(R.id.drawer_list);
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        mMenuOptions = getResources().getStringArray(R.array.menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (NavigationView) findViewById(R.id.drawer_list);
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Analiza wydatków");
        View header = mDrawerList.getHeaderView(0);
        tvUsername = (TextView) header.findViewById(R.id.username);
        tvUsername.setText(login);

        mDrawerList.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent mIntent = null;
                switch (item.getItemId()) {
                    case R.id.menu_list:
                        mIntent = new Intent(AnalysisActivity.this, BillsList.class);
                        break;
                    case R.id.menu_add_pic:
                        mIntent = new Intent(AnalysisActivity.this, OCRActivity.class);
                        break;
                    case R.id.menu_add_man:
                        mIntent = new Intent(AnalysisActivity.this, EditBillActivity.class);
                        break;
                    case R.id.menu_charts:
                        mIntent = new Intent(AnalysisActivity.this, AnalysisActivity.class);
                        break;
                    case R.id.menu_sync:
                        break;
                    case R.id.menu_logout:
                        mIntent = new Intent(Intent.ACTION_MAIN);
                        mIntent.addCategory(Intent.CATEGORY_HOME);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        break;
                    default:
                        mIntent = new Intent(AnalysisActivity.this, BillsList.class); // Activity_0 as default
                        break;
                }
                mDrawerLayout.closeDrawers();
                startActivity(mIntent);
                return false;
            }
        });

        drawerListener = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO
                // action performed when drawer closed
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerOpened(View drawerView) {
                //TODO
                // action performed when drawer opened
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(drawerListener);


        // CHART

        mBarChart = (BarChart) findViewById(R.id.myBarChart);
        mData.testData(); // zmienić na pobieranie z bazy

        mBarChart.setData(mData.data);

        mBarChart.setDescription("");
        mBarChart.animateY(3000);
        mBarChart.getLegend().setEnabled(false);
        mBarChart.setVisibleXRangeMaximum(4);
        //mBarChart.setDescriptionPosition(3f, 3f);

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


        // DATE PICKERS
        tvDesc = (TextView) findViewById(R.id.text_desc);
        tvDate = (TextView) findViewById(R.id.text_range);
        tvDateFrom = (TextView) findViewById(R.id.text_range_start);
        tvDateTo = (TextView) findViewById(R.id.text_range_end);
        etDateFrom = (EditText) findViewById(R.id.etDate1);
        etDateTo = (EditText) findViewById(R.id.etDate2);
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
                                        }
                                    }
        );

        // get the current date
        final Calendar c = Calendar.getInstance();
        mStartYear = c.get(Calendar.YEAR);
        mStartMonth = c.get(Calendar.MONTH) - 1;
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
    }

    ;

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

    //końcówka drawera
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }
/*
    public void example(){

        ArrayList<FinalProduct> testList =new ArrayList();
        FinalProduct f1=new FinalProduct("chomik","1","2","12","zwierzeta");
        FinalProduct f2=new FinalProduct("królik","1","2","12","jedzenie");
        FinalProduct f3=new FinalProduct("kot","1","2","12","ubrania");
        FinalProduct f4=new FinalProduct("skarpetki","1","2","12","zwierzeta");
        FinalProduct f5=new FinalProduct("costam","1","2","12","jedzenie");
        FinalProduct f6=new FinalProduct("kot","1","2","12","ubrania");
        testList.add(f1);
        testList.add(f2);
        testList.add(f3);
        testList.add(f4);
        testList.add(f5);
        testList.add(f6);

        BillEntity b1 = new BillEntity("TENSKLEP",testList);


        mydb.insertProducts(b1);


    }
*/
}