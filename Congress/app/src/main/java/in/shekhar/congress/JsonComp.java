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
    boolean isAsc;
    public JsonComp(String tag, boolean asc){
        tagName = tag;
        isAsc = asc;
    }
    @Override
    public int compare(JSONObject jsonObject, JSONObject t1) {
        try{
            if(tagName == "introduced_on"){
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date d1 = format.parse(jsonObject.getString(tagName));
                    Date d2 = format.parse(t1.getString(tagName));
                    int factor = -1;
                    if(isAsc == false){
                        factor = 1;
                    }
                    return (d1.compareTo(d2))*(factor);

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
