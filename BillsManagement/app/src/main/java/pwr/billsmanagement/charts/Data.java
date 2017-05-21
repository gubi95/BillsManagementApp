package pwr.billsmanagement.charts;

import android.util.Log;

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

    public void testData() {
        this.categories.add("Jedzenie");
        this.categories.add("Odzież");
        this.categories.add("Kosmetyki");
        this.categories.add("Alkohol");
        this.categories.add("Detergenty");
        this.categories.add("Rozrywka");
        this.categories.add("Usługi");

        this.costs.add(new BarEntry(500f, 0));
        this.costs.add(new BarEntry(400f, 1));
        this.costs.add(new BarEntry(3f, 2));
        this.costs.add(new BarEntry(655f, 3));
        this.costs.add(new BarEntry(22.22f, 4));
        this.costs.add(new BarEntry(441.55f, 5));
        this.costs.add(new BarEntry(124.66f, 6));

        this.dataset = new BarDataSet(this.costs, "Wydano");
        this.dataset.setColors(ColorTemplate.PASTEL_COLORS);
        this.data = new BarData(this.categories, this.dataset);
        Log.d("koszta", "arr: " + this.costs.toString());
        Log.d("kategorie", "arr: " + this.categories.toString());
    }
}




