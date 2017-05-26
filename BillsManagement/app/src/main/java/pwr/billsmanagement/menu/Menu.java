package pwr.billsmanagement.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pwr.billsmanagement.R;
import pwr.billsmanagement.bills.edition.EditBillActivity;
import pwr.billsmanagement.ocr.OCRActivity;
import pwr.billsmanagement.charts.AnalysisActivity;


public class Menu extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerList;
    private ActionBarDrawerToggle drawerListener;
    private Toolbar toolbar;
    private String login="Przykładowy Login";
    private TextView tvUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (NavigationView) findViewById(R.id.drawer_list);
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        tvUsername = (TextView) findViewById(R.id.username);
        tvUsername.setText(login);

        mDrawerList.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent mIntent = null;
                switch (item.getItemId()){
                    case R.id.menu_list:
                        mIntent = new Intent(Menu.this, Menu.class);
                        break;
                    case R.id.menu_add_pic:
                        mIntent = new Intent(Menu.this, OCRActivity.class);
                        break;
                    case R.id.menu_add_man:
                        mIntent = new Intent(Menu.this, EditBillActivity.class);
                        break;
                    case R.id.menu_charts:
                        mIntent = new Intent(Menu.this, AnalysisActivity.class);
                        break;
                    case R.id.menu_sync:
                        break;
                    case R.id.menu_logout:
                        mIntent = new Intent(Intent.ACTION_MAIN);
                        mIntent.addCategory(Intent.CATEGORY_HOME);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        break;
                    default :
                        mIntent = new Intent(Menu.this, Menu.class); // Activity_0 as default
                        break;
                }
                mDrawerLayout.closeDrawers();
                startActivity(mIntent);
                return false;
            }
        });

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
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }
}
