package in.shekhar.congress;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shekhar on 11/29/2016.
 */

public class JsonComp implements Comparator<JSONObject> {
    String tagName;

    public JsonComp(String tag){
        tagName = tag;
    }
    @Override
    public int compare(JSONObject jsonObject, JSONObject t1) {
        try{
            if(tagName == "introduced_on"){
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date d1 = format.parse(jsonObject.getString(tagName));
                    Date d2 = format.parse(t1.getString(tagName));

                    return (d1.compareTo(d2))*(-1);

                }catch (ParseException pe){
                    String l1 = jsonObject.getString(tagName);
                    String l2 = t1.getString(tagName);

                    return l1.compareTo(l2);
                }

            }else {
                String l1 = jsonObject.getString(tagName);
                String l2 = t1.getString(tagName);

                return l1.compareTo(l2);

            }
        }catch (org.json.JSONException je){
            je.printStackTrace();
        }

        return 0;
    }

}
