package in.shekhar.congress;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsPage extends AppCompatActivity {

    SharedPreferences sp;
    public static final String PREFSTRING = "CongressFav" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp = getSharedPreferences(PREFSTRING, Context.MODE_PRIVATE);


        // details from different fragments
        toolbar.setTitle(getIntent().getStringExtra("title"));

        String info = getIntent().getStringExtra("info");
        String img = "";
        try {

            Log.d("", "all info" + info);

            JSONObject jsonObject = new JSONObject(info);
            String id = jsonObject.getString("bioguide_id");

            img = "https://theunitedstates.io/images/congress/original/" + id + ".jpg";
            Log.d("", "Image URL" + img);
            GetImage gi = new GetImage(img, id, this);
            gi.execute("");



            final ImageView favImg = (ImageView) findViewById(R.id.favoritesIcon);
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
                            Toast.makeText(DetailsPage.this, "Was already saved, removed it", Toast.LENGTH_SHORT).show();
                            favImg.setImageResource(R.mipmap.star_blank);
                        }else{
                            // not saved... save the data
                            editor.putString(id, info);
                            editor.commit();
                            Toast.makeText(DetailsPage.this, "Saved", Toast.LENGTH_SHORT).show();
                            Log.d("", "Information Saved : " + info);
                            favImg.setImageResource(R.mipmap.star_yellow);
                        }

                    }catch (Exception e){

                    }
                }
            });



            String title = "NA";
            if(jsonObject.has("title")) {
                title = jsonObject.getString("title");
            }

            String lname = "NA";
            if(jsonObject.has("last_name")){
                lname = jsonObject.getString("last_name");
            }

            String fname = "NA";
            if(jsonObject.has("first_name")){
                fname = jsonObject.getString("first_name");
            }

            String email = "NA";
            if(jsonObject.has("oc_email")){
                email = jsonObject.getString("oc_email");
            }

            String chamber = "NA";
            if(jsonObject.has("chamber")){
                chamber = jsonObject.getString("chamber");
            }

            String contact = "NA";
            if(jsonObject.has("phone")){
                contact = jsonObject.getString("phone");
            }

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat outputFormat = new SimpleDateFormat("MMM DD, yyyy", Locale.ENGLISH);
            //Date date = format.parse(string);
            Date startTerm = null;
            if(jsonObject.has("term_start")) {
                startTerm = format.parse(jsonObject.getString("term_start"));
            }
            Date endTerm = null;
            if(jsonObject.has("term_end")){
                endTerm = format.parse(jsonObject.getString("term_end"));
            }


            // TODO : change the start and end term string to dates and then calculate the progress
            long completion = 0;
            long total = 0;
            int percent = 50;
            if(!(startTerm == null || endTerm == null)){
                total = endTerm.getTime() - startTerm.getTime();
                completion = new Date().getTime()- startTerm.getTime();
                percent = (int)(completion/total)*100;
            }

            String office = "NA";
            if(jsonObject.has("")){
                office = jsonObject.getString("office");
            }

            String state = "NA";
            if(jsonObject.has("state")){
                state = jsonObject.getString("state");
            }

            String fax = "NA";
            if(jsonObject.has("fax")){
                fax = jsonObject.getString("fax");
            }

            String bDay = "NA";
            if(jsonObject.has("birthday")){
                bDay = jsonObject.getString("birthday");
            }

            String facebook = null;
            if(jsonObject.has("")){
                facebook = jsonObject.getString("facebook_id");
            }

            String twitter = null;
            if(jsonObject.has("twitter_id")){
                twitter = jsonObject.getString("twitter_id");
            }

            String website = null;
            if(jsonObject.has("")){
                website = jsonObject.getString("website");
            }

            String party = null;
            if(jsonObject.has("party")){
                party = jsonObject.getString("party");
            }

            // setting the values in text fields

            TextView partyText = (TextView) findViewById(R.id.nameOfParty);
            ImageView partyLogo = (ImageView) findViewById(R.id.partyLogo);
            String partyValue = "Republican";
            if(party.equalsIgnoreCase("d")){
                partyValue = "Democratic";
                partyLogo.setImageResource(R.mipmap.d);
            }
            partyText.setText(partyValue);

            TextView nameText = (TextView) findViewById(R.id.nameOfLegislator);
            nameText.setText(title + ". " + lname + ", " + fname );

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
            birthText.setText(bDay);




        }catch(Exception ex){
            ex.printStackTrace();
//            Log.d("", ex.getMessage());
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


    public class GetImage extends AsyncTask<String, String, String>
    {
        Bitmap b;
        String url;
        String id;
        Activity activity;

        public  GetImage(String imgUrl, String imgId, Activity activity){
            url = imgUrl;
            id = imgId;
            this.activity = activity;
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
                URL realUrl = new URL(url);
                b = BitmapFactory.decodeStream(realUrl.openConnection().getInputStream());
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
            iv.setImageBitmap(b);        }
    }
}
