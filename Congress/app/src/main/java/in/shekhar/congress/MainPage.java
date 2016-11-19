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
import android.widget.TabHost;

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
//        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // tabs here

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("BY STATE");
        spec.setContent(R.id.tab1);
        spec.setIndicator("BY STATE");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("HOUSE");
        spec.setContent(R.id.tab2);
        spec.setIndicator("HOUSE");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("SENATE");
        spec.setContent(R.id.tab3);
        spec.setIndicator("SENATE");
        host.addTab(spec);





//         toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //-------------

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("House"));
//        tabLayout.addTab(tabLayout.newTab().setText("Senate"));
//        tabLayout.addTab(tabLayout.newTab().setText("Joint"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        final Committee adapter = new Committee(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//
//
//        });


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
            // Create new fragment and transaction
            toolbar.setTitle("Legislators");
            Fragment newFragment = new Legislator();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.main_page, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
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

       // TextView tv = findViewById(R.id.house_main);
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(new String("Legis:"),"Something here");
    }
}
