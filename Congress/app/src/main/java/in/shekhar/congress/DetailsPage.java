package in.shekhar.congress;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;

public class DetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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


            String title = jsonObject.getString("title");
            String lname = jsonObject.getString("last_name");
            String fname = jsonObject.getString("first_name");

            String email = jsonObject.getString("oc_email");
            String chamber = jsonObject.getString("chamber");
            String contact = jsonObject.getString("phone");
            String startTerm = jsonObject.getString("term_start");
            String endTerm = jsonObject.getString("term_end");
            String office = jsonObject.getString("office");
            String state = jsonObject.getString("state");
            String fax = jsonObject.getString("fax");
            String bDay = jsonObject.getString("birthday");

            String facebook = jsonObject.getString("facebook_id");
            String twitter = jsonObject.getString("twitter_id");
            String website = jsonObject.getString("website");

            String party = jsonObject.getString("party");

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
            startTermText.setText(startTerm);

            TextView endTermText = (TextView) findViewById(R.id.termEnd);
            endTermText.setText(endTerm);


            int max = 100;
            int progress = 70;
            ProgressBar pb = (ProgressBar) findViewById(R.id.term);
            pb.setMax(max);
            pb.setProgress(progress);


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
