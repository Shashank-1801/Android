package in.shekhar.congress;

/**
 * Created by Shekhar on 11/17/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BillDetailsPage extends AppCompatActivity {

    SharedPreferences sp;
    public static final String PREFSTRING = "CongressFav" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page_bill_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp = getSharedPreferences(PREFSTRING, Context.MODE_PRIVATE);


        // details from different fragments
        toolbar.setTitle(getIntent().getStringExtra("title"));

        String id = getIntent().getStringExtra("id");

        String info = getIntent().getStringExtra("info");

        try {
            JSONObject jsonObject = new JSONObject(info);

            final ImageView favImg = (ImageView) findViewById(R.id.billFavoritesIcon);
            favImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    try {
                        String info = getIntent().getStringExtra("info");
                        JSONObject jsonObject = new JSONObject(info);
                        String id = jsonObject.getString("bill_id");

                        if(sp.contains(id)){
                            // is already saved
                            Log.d("", "Already saved, removing it... " + id);
                            editor.remove(id);
                            editor.commit();
                            Toast.makeText(BillDetailsPage.this, "Was already saved, removed it", Toast.LENGTH_SHORT).show();
                            favImg.setImageResource(R.mipmap.star_blank);
                        }else{
                            // not saved... save the data
                            editor.putString(id, info);
                            editor.commit();
                            Toast.makeText(BillDetailsPage.this, "Saved", Toast.LENGTH_SHORT).show();
                            Log.d("", "Information Saved : " + info);
                            favImg.setImageResource(R.mipmap.star_yellow);
                        }

                    }catch (Exception e){

                    }
                }
            });



            String billID = "NA";
            if(jsonObject.has("bill_id")) {
                billID = jsonObject.getString("bill_id");
            }

            String title = "NA";
            if(jsonObject.has("official_title")){
                title = jsonObject.getString("official_title");
            }

            String billType = "NA";
            if(jsonObject.has("bill_type")){
                billType = jsonObject.getString("bill_type");
            }

            String sponsor = "NA";
            String lname = "";
            String fname = "";
            String stitle = "";
            if(jsonObject.has("sponsor")){
                JSONObject jo = jsonObject.getJSONObject("sponsor");
                if(jo.has("last_name")){
                    lname = jo.getString("last_name");
                }
                if(jo.has("first_name")){
                    fname = jo.getString("first_name");
                }
                if(jo.has("title")){
                    stitle = jo.getString("title");
                }

                sponsor = stitle + ". " + lname + ", " + fname;
            }

            String chamber = "NA";
            if(jsonObject.has("chamber")){
                chamber = jsonObject.getString("chamber");
            }

            String status = "NA";
            if(jsonObject.has("history")){
                JSONObject jo = jsonObject.getJSONObject("history");
                if(jo.has("active")) {
                    boolean st;
                    st = jo.getBoolean("active");
                    if(st){
                        status = "Active";
                    }else{
                        status = "New";
                    }
                }
            }

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            //Date date = format.parse(string);
            Date introducedOn = null;
            if(jsonObject.has("introduced_on")) {
                introducedOn = format.parse(jsonObject.getString("introduced_on"));
            }

            String congressUrl = "NA";
            if(jsonObject.has("urls")){
                JSONObject jo = jsonObject.getJSONObject("urls");
                if(jo.has("congress")) {
                    congressUrl = jo.getString("congress");
                }
            }

            String versionStatus = "NA";
            if(jsonObject.has("last_version")){
                JSONObject jo = jsonObject.getJSONObject("last_version");
                if(jo.has("version_name")){
                    versionStatus = jo.getString("version_name");
                }
            }

            String billURL = "NA";
            if(jsonObject.has("urls")){
                JSONObject jo = jsonObject.getJSONObject("urls");
                if(jo.has("pdf")) {
                    billURL = jsonObject.getString("pdf");
                }
            }

            // setting the values in text fields

            TextView billDetailsID = (TextView) findViewById(R.id.billDetailsId);
            billDetailsID.setText(billID.toUpperCase());

            TextView billDetailsTitle = (TextView) findViewById(R.id.billDetailsTitle);
            billDetailsTitle.setText(title);

            TextView billDetailsType = (TextView) findViewById(R.id.billDetailsType);
            billDetailsType.setText(billType.toUpperCase());

            TextView billDetailsSponsor = (TextView) findViewById(R.id.billDetailsSponsor);
            billDetailsSponsor.setText(sponsor);

            TextView billDetailsChamber = (TextView) findViewById(R.id.billDetailsChamber);
            billDetailsChamber.setText(chamber);

            TextView billDetailsStatus = (TextView) findViewById(R.id.billDetailsStatus);
            billDetailsStatus.setText(status);

            TextView billDetailsIntro = (TextView) findViewById(R.id.billDetailsIntroducedOn);
            billDetailsIntro.setText(outputFormat.format(introducedOn));

            TextView billDetailsCongressURL = (TextView) findViewById(R.id.billDetailsURL);
            billDetailsCongressURL.setText(congressUrl);

            TextView billDetailsVersionStatus = (TextView) findViewById(R.id.billDetailsVersion);
            billDetailsVersionStatus.setText(versionStatus);

            TextView billDetailsBillURL = (TextView) findViewById(R.id.billDetailsBillURL);
            billDetailsBillURL.setText(billURL);


        }catch(Exception ex){
            ex.printStackTrace();
        }

        // end of details
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return false;
    }

}
