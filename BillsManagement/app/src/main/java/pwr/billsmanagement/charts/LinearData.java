package pwr.billsmanagement.charts;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class LinearData {

    ArrayList<Entry> costs;
    ArrayList<String> months;
    LineDataSet dataset;
    LineData data;
    LineDataSet dataSet;

    public LinearData () {
        this.costs = new ArrayList<Entry>();
        this.months = new ArrayList<String>();
        this.dataset = new LineDataSet(this.costs, "Wydano:");
        this.data = new LineData(this.months, dataset);
    }

    public LinearData (int usrID, int dayFrom, int monthFrom, int yearFrom, int dayTo, int monthTo, int yearTo) {
        // TODO get data from database
    }

    public void testData() {
        this.months.add("I");
        this.months.add("II");
        this.months.add("III");
        this.months.add("IV");
        this.months.add("V");
        this.months.add("VI");
        this.months.add("VII");
        this.months.add("VII");
        this.months.add("IX");
        this.months.add("X");
        this.months.add("XI");
        this.months.add("XII");

        this.costs.add(new BarEntry(500f, 0));
        this.costs.add(new BarEntry(400f, 1));
        this.costs.add(new BarEntry(655f, 2));
        this.costs.add(new BarEntry(441.55f, 3));
        this.costs.add(new BarEntry(333f, 4));
        this.costs.add(new BarEntry(232f , 5));
        this.costs.add(new BarEntry(231f, 6));
        this.costs.add(new BarEntry(440.55f, 7));
        this.costs.add(new BarEntry(99f, 8));
        this.costs.add(new BarEntry(299f, 9));
        this.costs.add(new BarEntry(399f, 10));
        this.costs.add(new BarEntry(23f, 11));

        this.dataSet = new LineDataSet(this.costs, "Wydano");
        this.dataset.setColors(ColorTemplate.PASTEL_COLORS);
        this.data = new LineData(this.months, this.dataset);
    }
}
