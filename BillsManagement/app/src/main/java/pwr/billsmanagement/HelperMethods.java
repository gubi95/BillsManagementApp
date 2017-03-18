package pwr.billsmanagement;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HelperMethods {
    public static void CreateMenu(
            AppCompatActivity objAppCompatActivity,
            final DrawerLayout objDrawerLayout,
            final ListView objListView,
            final ActionBarDrawerToggle objActionBarDrawerToggle) {

        // populate menu list view with categories
        objListView.setAdapter(new ArrayAdapter<String>(objAppCompatActivity, R.layout.main_menu_item_template, objAppCompatActivity.getResources().getStringArray(R.array.menu_items)));

        // menu list view click handler
        objListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objDrawerLayout.closeDrawer(objListView);
                // TO DO: go to selected activity based on int position
            }
        });

        // set top bar menu icon
        objDrawerLayout.setDrawerListener(objActionBarDrawerToggle);
        objAppCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        objAppCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
    }
}
