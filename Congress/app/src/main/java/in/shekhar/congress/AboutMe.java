package in.shekhar.congress;

/**
 * Created by Shekhar on 11/17/2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("About Me");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return false;
    }
}
