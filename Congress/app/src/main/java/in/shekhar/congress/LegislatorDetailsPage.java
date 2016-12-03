package in.shekhar.congress;

/**
 * Created by Shekhar on 11/17/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LegislatorDetailsPage extends AppCompatActivity {

    SharedPreferences sp;
    public static final String PREFSTRING = "CongressFav" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page_legislator_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp = getSharedPreferences(PREFSTRING, Context.MODE_PRIVATE);


        // details from different fragments
        toolbar.setTitle(getIntent().getStringExtra("title"));

        String id = getIntent().getStringExtra("id");

        String info = getIntent().getStringExtra("info");
        String img = "";
        try {

            GetData gi = new GetData(id, this);
            gi.execute("");


            final ImageView favImg = (ImageView) findViewById(R.id.favoritesIcon);
            SharedPreferences.Editor editor = sp.edit();
            if(sp.contains(id)){
                favImg.setImageResource(R.mipmap.star_yellow );
            }else{
                favImg.setImageResource(R.mipmap.star_blank);
            }

            favImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    try {
                        String info = getIntent().getStringExtra("info");
                        JSONObject jsonObject = new JSONObject(info);
                        String id = jsonObject.getString("bioguide_id");

                        if(sp.contains(id)){
                            // is already saved
                            Log.d("", "Already saved, removing it... " + id);
                            editor.remove(id);
                            editor.commit();
                            Toast.makeText(LegislatorDetailsPage.this, "Was already saved, removed it", Toast.LENGTH_SHORT).show();
                            favImg.setImageResource(R.mipmap.star_blank);
                            Favorites.loadPageContents();
                        }else{
                            // not saved... save the data
                            editor.putString(id, info);
                            editor.commit();
                            Toast.makeText(LegislatorDetailsPage.this, "Saved", Toast.LENGTH_SHORT).show();
                            Log.d("", "Information Saved : " + info);
                            favImg.setImageResource(R.mipmap.star_yellow);
                            Favorites.loadPageContents();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            String facebook = null;
            ImageView facebookImage = (ImageView) findViewById(R.id.facebook);
            facebookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String info = getIntent().getStringExtra("info");
                        JSONObject jsonObject = new JSONObject(info);
                        String fbLink = jsonObject.getString("facebook_id");
                        if(!fbLink.equals("null")){
                            String link = "http://www.facebook.com/" + fbLink;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(link));
                            startActivity(i);
                        }else{
                            Toast.makeText(LegislatorDetailsPage.this, "Facebook link not available", Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e){
                        Toast.makeText(LegislatorDetailsPage.this, "Facebook link not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ImageView twitterImage = (ImageView) findViewById(R.id.twitter);
            twitterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String info = getIntent().getStringExtra("info");
                        JSONObject jsonObject = new JSONObject(info);
                        String twLink = jsonObject.getString("twitter_id");
                        if(!twLink.equals("null")){
                            String link = "http://www.twitter.com/" + twLink;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(link));
                            startActivity(i);
                        }else{
                            Toast.makeText(LegislatorDetailsPage.this, "Twitter link not available", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(LegislatorDetailsPage.this, "Twitter link not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ImageView websiteImage = (ImageView) findViewById(R.id.webpage);
            websiteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String info = getIntent().getStringExtra("info");
                        JSONObject jsonObject = new JSONObject(info);
                        String webLink = jsonObject.getString("website");
                        if(!webLink.equals("null")){
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(webLink));
                            startActivity(i);
                        }else{
                            Toast.makeText(LegislatorDetailsPage.this, "Webpage link not available", Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e){
                        Toast.makeText(LegislatorDetailsPage.this, "Website link not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

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


    public class GetData extends AsyncTask<String, String, String>
    {
        Bitmap b;
        String dataUrl;
        String imageUrl;
        String id;
        Activity activity;

        public  GetData(String legislatorId, Activity activity){
            id = legislatorId;
            this.activity = activity;
            dataUrl = "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=legislators-details&bio_id=" + id;
            imageUrl = "https://theunitedstates.io/images/congress/original/" + id + ".jpg";
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... arg0) {
            try
            {
                URL image = new URL(imageUrl);
                b = BitmapFactory.decodeStream(image.openConnection().getInputStream());

                String resultString = getData(dataUrl);
                return resultString;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ImageView iv = (ImageView) findViewById(R.id.imageOfLegislator);
            iv.setImageBitmap(b);

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray ja = jsonObject.getJSONArray("results");
                jsonObject = ja.getJSONObject(0);


                String title = "NA";
                if (jsonObject.has("title")) {
                    title = jsonObject.getString("title");
                }

                String lname = "NA";
                if (jsonObject.has("last_name")) {
                    lname = jsonObject.getString("last_name");
                }

                String fname = "NA";
                if (jsonObject.has("first_name")) {
                    fname = jsonObject.getString("first_name");
                }

                String email = "NA";
                if (jsonObject.has("oc_email")) {
                    email = jsonObject.getString("oc_email");
                }

                String chamber = "NA";
                if (jsonObject.has("chamber")) {
                    chamber = jsonObject.getString("chamber");
                    String temp = chamber.substring(0,1).toUpperCase();
                    temp += chamber.substring(1);
                    chamber = temp;
                }

                String contact = "NA";
                if (jsonObject.has("phone")) {
                    contact = jsonObject.getString("phone");
                }

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                Date startTerm = null;
                if (jsonObject.has("term_start")) {
                    startTerm = format.parse(jsonObject.getString("term_start"));
                }
                Date endTerm = null;
                if (jsonObject.has("term_end")) {
                    endTerm = format.parse(jsonObject.getString("term_end"));
                }


                // TODO : change the start and end term string to dates and then calculate the progress
                long completion = 0;
                long total = 0;
                int percent = 50;
                if (!(startTerm == null || endTerm == null)) {
                    total = endTerm.getTime() - startTerm.getTime();
                    completion = new Date().getTime() - startTerm.getTime();
                    percent = (int) ((completion *100 / total));
                }

                String office = "NA";
                if (jsonObject.has("")) {
                    office = jsonObject.getString("office");
                }

                String state = "NA";
                if (jsonObject.has("state")) {
                    state = jsonObject.getString("state");
                }

                String fax = "NA";
                if (jsonObject.has("fax")) {
                    fax = jsonObject.getString("fax");
                    if(fax.equals("null")){
                        fax = "NA";
                    }
                }

                Date bDay = null;
                if (jsonObject.has("birthday")) {
                    bDay = format.parse(jsonObject.getString("birthday"));
                }

                String facebook = null;
                if (jsonObject.has("")) {
                    facebook = jsonObject.getString("facebook_id");
                }

                String twitter = null;
                if (jsonObject.has("twitter_id")) {
                    twitter = jsonObject.getString("twitter_id");
                }

                String website = null;
                if (jsonObject.has("")) {
                    website = jsonObject.getString("website");
                }

                String party = null;
                if (jsonObject.has("party")) {
                    party = jsonObject.getString("party");
                }

                // setting the values in text fields

                TextView partyText = (TextView) findViewById(R.id.nameOfParty);
                ImageView partyLogo = (ImageView) findViewById(R.id.partyLogo);
                String partyValue = "Republican";
                if (party.equalsIgnoreCase("d")) {
                    partyValue = "Democratic";
                    partyLogo.setImageResource(R.mipmap.d);
                }
                partyText.setText(partyValue);

                TextView nameText = (TextView) findViewById(R.id.nameOfLegislator);
                nameText.setText(title + ". " + lname + ", " + fname);

                TextView emailText = (TextView) findViewById(R.id.email);
                emailText.setText(email);

                TextView chamberText = (TextView) findViewById(R.id.chamber);
                chamberText.setText(chamber);

                TextView contactText = (TextView) findViewById(R.id.contact);
                contactText.setText(contact);

                TextView startTermText = (TextView) findViewById(R.id.termStart);
                startTermText.setText(outputFormat.format(startTerm));

                TextView endTermText = (TextView) findViewById(R.id.termEnd);
                endTermText.setText(outputFormat.format(endTerm));


                int max = 100;
                int progress = percent;
                ProgressBar pb = (ProgressBar) findViewById(R.id.term);
                pb.setMax(max);
                pb.setProgress(progress);
                TextView per = (TextView) findViewById(R.id.percentageText);
                String percentText = String.valueOf(percent) + "%";
                per.setText(percentText);


                TextView officeText = (TextView) findViewById(R.id.office);
                officeText.setText(office);

                TextView stateText = (TextView) findViewById(R.id.state);
                stateText.setText(state);

                TextView faxText = (TextView) findViewById(R.id.fax);
                faxText.setText(fax);

                TextView birthText = (TextView) findViewById(R.id.bDay);
                birthText.setText(outputFormat.format(bDay));

            }catch (Exception e){
                e.printStackTrace();
            }
        }


        public String getData(String url) {
            HttpURLConnection c = null;
            try {
                URL u = new URL(url);
                c = (HttpURLConnection) u.openConnection();
                c.connect();
                int status = c.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (Exception ex) {
                return ex.toString();
            } finally {
                if (c != null) {
                    try {
                        c.disconnect();
                    } catch (Exception ex) {
                        //disconnect error
                    }
                }
            }
            return null;
        }

    }
}
