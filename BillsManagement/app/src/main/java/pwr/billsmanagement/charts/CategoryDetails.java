package pwr.billsmanagement.charts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import pwr.billsmanagement.R;

public class CategoryDetails extends AppCompatActivity implements OnItemClickListener{


    private Toolbar toolbar;
    private TextView tvSpendLabel;
    private TextView tvSpendText;
    private TextView tvLimitIndicator;
    private TextView tvCategoryLabel;
    private TextView tvLimit;
    private EditText etLimit;
    private Button save;

    LinearData mData = new LinearData();
    LineChart mLineChart;

    String cathegoryName;
    float moneySpend = 23; // później pobierane z bazy
    float spendLimit = 0; // j.w.

    public CategoryDetails() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        Intent thisIntent = getIntent();
        cathegoryName = thisIntent.getStringExtra("category");

        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Szczegóły kategorii");

        tvLimitIndicator = (TextView) findViewById(R.id.limit_indicator);
        checkLimit();

        tvCategoryLabel = (TextView) findViewById(R.id.text_category);

        TextView tvCategoryText = (TextView) findViewById(R.id.text_category_name);
        tvCategoryText.setText(cathegoryName);
        //Toast.makeText(getApplicationContext(), cathegoryName, Toast.LENGTH_SHORT).show();

        tvSpendLabel = (TextView) findViewById(R.id.text_spend);
        tvSpendText = (TextView) findViewById(R.id.spend_value);
        tvSpendText.setText(String.valueOf(moneySpend));

        tvLimit = (TextView) findViewById(R.id.text_spend_limit);
        etLimit = (EditText) findViewById(R.id.limit_value);
        etLimit.setText(String.valueOf(spendLimit));

        save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spendLimit = Float.parseFloat(etLimit.getText().toString());
                checkLimit();
                Toast.makeText(CategoryDetails.this, "Zapisano zmiany!", Toast.LENGTH_SHORT).show();
            }
        });

        // CHART
        mLineChart = (LineChart) findViewById(R.id.myLineChart);
        mData.testData(); // zmienić na pobieranie z bazy

        mLineChart.setData(mData.data);
        mLineChart.setDescription("Wydatki w ostatnich 12 m-cach");
        mLineChart.animateY(3000);
        mLineChart.getLegend().setEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CategoryDetails.this, AnalysisActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void checkLimit() {
        if(spendLimit >= moneySpend) {
            tvLimitIndicator.setTextColor(getResources().getColor(R.color.green));
            tvLimitIndicator.setText("Nie przekroczono limitu wydatków!");
        } else {
            tvLimitIndicator.setTextColor(getResources().getColor(R.color.red));
            tvLimitIndicator.setText("Przekroczono limit wydatków!");
        }
    }
}
