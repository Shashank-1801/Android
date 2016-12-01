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

public class CommitteeDetailsPage extends AppCompatActivity {

    SharedPreferences sp;
    public static final String PREFSTRING = "CongressFav" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page_committee_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp = getSharedPreferences(PREFSTRING, Context.MODE_PRIVATE);


        // details from different fragments
        toolbar.setTitle(getIntent().getStringExtra("title"));

        String id = getIntent().getStringExtra("id");

        String info = getIntent().getStringExtra("info");

        try {

            final ImageView favImg = (ImageView) findViewById(R.id.committeeFavoritesIcon);
            favImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    try {
                        String info = getIntent().getStringExtra("info");
                        JSONObject jsonObject = new JSONObject(info);
                        String id = jsonObject.getString("committee_id");

                        if(sp.contains(id)){
                            // is already saved
                            Log.d("", "Already saved, removing it... " + id);
                            editor.remove(id);
                            editor.commit();
                            Toast.makeText(CommitteeDetailsPage.this, "Was already saved, removed it", Toast.LENGTH_SHORT).show();
                            favImg.setImageResource(R.mipmap.star_blank);
                        }else{
                            // not saved... save the data
                            editor.putString(id, info);
                            editor.commit();
                            Toast.makeText(CommitteeDetailsPage.this, "Saved", Toast.LENGTH_SHORT).show();
                            Log.d("", "Information Saved : " + info);
                            favImg.setImageResource(R.mipmap.star_yellow);
                        }

                    }catch (Exception e){

                    }
                }
            });


            JSONObject jsonObject = new JSONObject(info);

            String committeID = "NA";
            if(jsonObject.has("committee_id")) {
                committeID = jsonObject.getString("committee_id");
            }

            String name = "NA";
            if(jsonObject.has("name")){
                name = jsonObject.getString("name");
            }

            String chamber = "NA";
            if(jsonObject.has("chamber")){
                chamber = jsonObject.getString("chamber");
            }


            String parentCommittee = "NA";
            if(jsonObject.has("parent_committee_id")){
                parentCommittee = jsonObject.getString("parent_committee_id");
            }

            String contact = "NA";
            if(jsonObject.has("phone")){
                contact = jsonObject.getString("phone");
            }

            String office = "NA";
            if(jsonObject.has("office")){
                contact = jsonObject.getString("office");
            }

            // setting the values in text fields


            TextView committeeID = (TextView) findViewById(R.id.committeeDetailsId);
            committeeID.setText(committeID);

            TextView committeeName = (TextView) findViewById(R.id.committeeDetailsName);
            committeeName.setText(name);

            TextView chamberText = (TextView) findViewById(R.id.committeeDetailsChamber);
            ImageView chamberImage = (ImageView) findViewById(R.id.committeeDetailsChamberImage);
            chamberText.setText(chamber);
            if(chamber.equalsIgnoreCase("house")){
                chamberImage.setImageResource(R.mipmap.h);
            }else{
                chamberImage.setImageResource(R.mipmap.s);
            }


            TextView committeeParent = (TextView) findViewById(R.id.committeeDetailsParent);
            committeeParent.setText(parentCommittee);

            TextView committeeContact = (TextView) findViewById(R.id.committeeDetailsContact);
            committeeContact.setText(contact);

            TextView committeeOffice = (TextView) findViewById(R.id.committeeDetailsOffice);
            committeeOffice.setText(office);




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
