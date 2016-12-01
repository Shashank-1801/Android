package in.shekhar.congress;

/**
 * Created by Shekhar on 11/17/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class AsyncTaskActivity extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private String mUrl;
    Activity activity;
    int idOfElement;
    String source;
    int indexerID;


    public AsyncTaskActivity(Context context, String url, Activity ac, int id, String type, int indexID) {
        mContext = context;
        mUrl = url;
        activity = ac;
        idOfElement = id;
        source = type;
        indexerID = indexID;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String resultString = null;
        resultString = getData(mUrl);
        return resultString;
    }

    @Override
    protected void onPostExecute(final String strings) {
        super.onPostExecute(strings);
        ArrayList<String> list = null;
        try {

            if(source.equalsIgnoreCase("Bills")){
                Log.d("","List View for Bills populating...");
                JSONObject jsonString = new JSONObject(strings);

                JSONArray jArray = jsonString.getJSONArray("results");

                JSONArray sorted = sortList(jArray, "introduced_on");
                list = new ArrayList<>();
                for (int i = 0; i < jArray.length(); i++) {
                    list.add(sorted.getString(i));
                }

                ListView lv = (ListView) activity.findViewById(idOfElement);
                BillsAdapter billsAdapter = new BillsAdapter(activity, list.toArray(new String[0]));
                lv.setAdapter(billsAdapter);
                Log.d("","List View for Bills populated");
            }
            else if(source.equalsIgnoreCase("Committees")) {
                Log.d("", "List View for Committees populating...");
                JSONObject jsonString = new JSONObject(strings);
                JSONArray jArray = jsonString.getJSONArray("results");

                JSONArray sorted = sortList(jArray, "name");
                list = new ArrayList<>();
                for (int i = 0; i < jArray.length(); i++) {
                    list.add(sorted.getString(i));
                }

                ListView lv = (ListView) activity.findViewById(idOfElement);
                CommitteesAdapter commAdapter = new CommitteesAdapter(activity, list.toArray(new String[0]));
                lv.setAdapter(commAdapter);
                Log.d("", "List View for Committees populated");
            }
            else if(source.equalsIgnoreCase("Legislators")) {
                Log.d("", "List View for Legislators populating...");
                JSONObject jsonString = new JSONObject(strings);
                JSONArray jArray = jsonString.getJSONArray("results");

                JSONArray sorted = sortList(jArray, "last_name");
                list = new ArrayList<>();
                for (int i = 0; i < jArray.length(); i++) {
                    list.add(sorted.getString(i));
                }


                ArrayList<String> names = new ArrayList<>();
                for(int i=0; i<sorted.length(); i++){
                    names.add(sorted.getJSONObject(i).getString("last_name"));
                }

                final Map<String, Integer> indexMap = new LinkedHashMap();
                for(int i=0; i<list.size(); i++){
                    if(indexMap.get(names.get(i).substring(0,1))==null) {
                        indexMap.put(names.get(i).substring(0,1), i);
                    }
                }

                LinearLayout linearLayout = (LinearLayout)  activity.findViewById(indexerID);
                List<String> indexList = new ArrayList<String>(indexMap.keySet());
                for (String index : indexList) {
                    TextView textView = new TextView(activity);
                    textView.setText(index);
                    textView.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView selectedIndex = (TextView) view;
                            ListView lv = (ListView) activity.findViewById(idOfElement);
                            lv.setSelection(indexMap.get(selectedIndex.getText()));
                        }
                    });
                    linearLayout.addView(textView);

                }



                ListView lv = (ListView) activity.findViewById(idOfElement);
                LegislatorsAdapter commAdapter = new LegislatorsAdapter(activity, list.toArray(new String[0]));
                lv.setAdapter(commAdapter);
                Log.d("", "List View for Legislators populated");
            }
            else {
                JSONObject jsonString = new JSONObject(strings);
                Log.d("", jsonString.toString());
                Log.d("", "we should not be here!");
                Toast.makeText(mContext, "We should not be here!", Toast.LENGTH_SHORT).show();
            }

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

    public Bitmap getImage(){
        Bitmap bmp = null;
        try {
            URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bmp;
    }


    public JSONArray sortList(JSONArray jArray, String tag){
        try {
            ArrayList<JSONObject> listJson = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                listJson.add(jArray.getJSONObject(i));
            }
            Collections.sort(listJson, new JsonComp(tag));

            JSONArray x = new JSONArray();
            for (int i = 0; i < listJson.size(); i++) {
                x.put((JSONObject)listJson.get(i));
            }
            return  x;
        }catch (JSONException je){
            je.printStackTrace();
        }

        return jArray;
    }
}
