package in.shekhar.congress;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainPage extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
                    Legislator.OnFragmentInteractionListener,
                    Committee.OnFragmentInteractionListener,
                    Bills.OnFragmentInteractionListener,
                    Favorites.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Load Navigation Panel and drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Load Legislator view by default
        toolbar.setTitle("Legislators");
        Fragment newFragment = new Legislator();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_page, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();


//        AsyncTaskActivity ata;
//
//        // fetch data for house
//        ata = new AsyncTaskActivity(this, "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=comm_house", this, R.id.committeesHouse);
//        ata.execute();
//
//        // fetch data for senate
//        ata = new AsyncTaskActivity(this, "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=comm_senate", this, R.id.committeesSenate);
//        ata.execute();
//
//        // fetch data for joint
//        ata = new AsyncTaskActivity(this, "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=comm_joint", this, R.id.committeesJoint);
//        ata.execute();
//

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        int id = item.getItemId();

        if (id == R.id.abt_me) {
            intent = new Intent(this, AboutMe.class);
            startActivity(intent);
        } else if (id == R.id.legs) {
            toolbar.setTitle("Legislators");
            Fragment newFragment = new Legislator();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_page, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.bills) {
            Fragment newFragment = new Bills();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_page, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            toolbar.setTitle("Bills");
        } else if (id == R.id.comm) {
            Fragment newFragment = new Committee();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_page, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            toolbar.setTitle("Committees");
        } else if (id == R.id.fav) {
            Fragment newFragment = new Favorites();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_page, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            toolbar.setTitle("Favorites");
        }

        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(new String("Legis:"),"Something here");
    }
}
