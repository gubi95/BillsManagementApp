package pwr.billsmanagement;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout objDrawerLayout = null;
    private ListView objDrawerList = null;
    private ActionBarDrawerToggle objActionBarDrawerToggle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        objDrawerList = (ListView) findViewById(R.id.left_drawer);
        objActionBarDrawerToggle = new ActionBarDrawerToggle(this, objDrawerLayout, 0, 0);

        HelperMethods.CreateMenu(this, objDrawerLayout, objDrawerList, objActionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        objActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        objActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (objActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
