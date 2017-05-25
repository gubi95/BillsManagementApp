package pwr.billsmanagement.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.R.drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;


import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.ocr.OCRActivity;
import pwr.billsmanagement.charts.AnalysisActivity;


public class Menu extends AppCompatActivity implements OnItemClickListener{

    private String[] mMenuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerListener;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mMenuOptions = getResources().getStringArray(R.array.menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

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
        //actions performed when menu items clicked

        selectItem(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void selectItem(int position) {
        Intent mIntent = null;
        FragmentActivity fragmentActivity = null;
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
