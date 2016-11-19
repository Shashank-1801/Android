package in.shekhar.congress;

/**
 * Created by Shekhar on 11/17/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AsyncTaskActivity extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private String mUrl;
    Activity activity;
    int idOfElement;


    public AsyncTaskActivity(Context context, String url, Activity ac, int id) {
        mContext = context;
        mUrl = url;
        activity = ac;
        idOfElement = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String resultString = null;
        resultString = getJSON(mUrl);

        return resultString;
    }

    @Override
    protected void onPostExecute(String strings) {
        super.onPostExecute(strings);
        TextView dynamictext = (TextView) activity.findViewById(idOfElement);
        dynamictext.setText(strings);
    }


    public String getJSON(String url) {
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
