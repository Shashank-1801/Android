package in.shekhar.congress;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Shekhar on 11/22/2016.
 */

public class LegislatorsAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] data;

    public LegislatorsAdapter(Activity context, String[] info) {
        super(context, R.layout.layout_bill_adapter);
        // TODO Auto-generated constructor stub

        this.context=context;
        data = info;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_legislator_adapter, null,true);
        Log.d("", "populating...");

        // modify according to the Legislators UI
        TextView legislatorName = (TextView) rowView.findViewById(R.id.legislatorsFullName);
        TextView legislatorInfo = (TextView) rowView.findViewById(R.id.legislatorsOtherData);
        TextView legislatorId = (TextView) rowView.findViewById(R.id.legislatorsId);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.legislatorsImage);



        try{
            String d = data[position];
            JSONObject jsonObject = new JSONObject(d);

            String id = null;

            if(jsonObject.has("bioguide_id")){
                id = jsonObject.getString("bioguide_id");
            }

//            GetData getData = new GetData(id, context, imageView);
//            getData.execute("");

            String lname = "NA";
            if(jsonObject.has("last_name")){
                lname = jsonObject.getString("last_name");
            }

            String fname = "NA";
            if(jsonObject.has("first_name")){
                fname = jsonObject.getString("first_name");
            }

            String party = null;
            if(jsonObject.has("party")){
                party = jsonObject.getString("party");
            }

            String state = "NA";
            if(jsonObject.has("state_name")){
                state = jsonObject.getString("state_name");
            }

            String district = "";
            if(jsonObject.has("district")){
                district = jsonObject.getString("district");
            }


            String name = lname + ", " + fname;
            String info = "(" + party + ") " + state;
            if(!district.equalsIgnoreCase("null")){
                info += " - District " + district;
            }

            legislatorName.setText(name);
            legislatorInfo.setText(info);
            legislatorId.setText(id);
            legislatorId.setVisibility(View.GONE);


            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent details;
                    details = new Intent(getContext(), LegislatorDetailsPage.class);
                    String title = "Legislators Info";
                    details.putExtra("id", ((TextView) view.findViewById(R.id.legislatorsId)).getText().toString());
                    details.putExtra("title", title);
                    getContext().startActivity(details);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



        return rowView;

    };

    public class GetData extends AsyncTask<String, String, String>
    {
        Bitmap b;
        String dataUrl;
        String imageUrl;
        String id;
        Activity activity;
        ImageView imv;
        //String imagePath;

        public  GetData(String legislatorId, Activity activity, ImageView imageView){
            id = legislatorId;
            this.activity = activity;
            dataUrl = "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=legislators-details&bio_id=" + id;
            imageUrl = "https://theunitedstates.io/images/congress/original/" + id + ".jpg";
            imv = imageView;
            //imagePath = imv.getTag().toString();
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

            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            imv.setImageBitmap(b);
        }


    }

}
