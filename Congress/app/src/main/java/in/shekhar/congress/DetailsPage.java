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
            JSONObject jsonObject = new JSONObject(info);
            String id = jsonObject.getString("bioguide_id");
            img = "https://theunitedstates.io/images/congress/original/" + id + ".jpg";


            TextView tv = (TextView) findViewById(R.id.info);
//            ImageView iv = (ImageView) findViewById(R.id.imageOfLegislator);
//            iv.setImageBitmap(bmp);
//            iv.setImageBitmap(getBitmapFromURL(img));
//            iv.setImageURI(Uri.parse(img));
//            tv.setText(img);
            Log.i("", img);

            GetImage gi = new GetImage(img, id, this);
            gi.execute("");

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
