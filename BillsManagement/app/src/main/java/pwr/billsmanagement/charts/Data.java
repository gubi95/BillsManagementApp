package pwr.billsmanagement.charts;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

public class Data {
    ArrayList<BarEntry> costs;
    ArrayList<String> categories;
    BarDataSet dataset;
    BarData data;

    public Data () {
        this.costs = new ArrayList<BarEntry>();
        this.categories = new ArrayList<String>();
        this.dataset = new BarDataSet(this.costs, "Wydano:");
        this.data = new BarData(this.categories, dataset);
    }

    public Data (int usrID, int dayFrom, int monthFrom, int yearFrom, int dayTo, int monthTo, int yearTo) {
        // TODO get data from database
    }

    public void testData() {
        this.categories.add("Jedzenie");
        this.categories.add("Odzież");
        this.categories.add("Alkohol");
        this.categories.add("Rozrywka");

        this.costs.add(new BarEntry(500f, 0));
        this.costs.add(new BarEntry(400f, 1));
        this.costs.add(new BarEntry(655f, 2));
        this.costs.add(new BarEntry(441.55f, 3));

        this.dataset = new BarDataSet(this.costs, "Wydano");
        this.dataset.setColors(ColorTemplate.PASTEL_COLORS);
        this.data = new BarData(this.categories, this.dataset);
    }
    public void testDataRangedCategory() {
        this.categories.add("Jedzenie");
        this.categories.add("Odzież");
        this.categories.add("Alkohol");
        this.categories.add("Rozrywka");

        this.costs.add(new BarEntry(500f, 0));
        this.costs.add(new BarEntry(400f, 1));
        this.costs.add(new BarEntry(655f, 2));
        this.costs.add(new BarEntry(441.55f, 3));

        this.dataset = new BarDataSet(this.costs, "Wydano");
        this.dataset.setColors(ColorTemplate.PASTEL_COLORS);
        this.data = new BarData(this.categories, this.dataset);
    }
}




