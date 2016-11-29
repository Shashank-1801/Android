package in.shekhar.congress;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shekhar on 11/22/2016.
 */

public class BillsAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] data;

    public BillsAdapter(Activity context, String[] info) {
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
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_bill_adapter, null,true);
        Log.d("", "populating...");
        TextView billOfficialTitle = (TextView) rowView.findViewById(R.id.billOfficialTitle);
        TextView billId = (TextView) rowView.findViewById(R.id.billId);
        TextView billIntroducedOn = (TextView) rowView.findViewById(R.id.billIntroduedOn);

        try{
            String d = data[position];
            JSONObject jsonObject = new JSONObject(d);
            String id = null ;
            String title = null;
            Date intro = null;

            if(jsonObject.has("bill_id")){
                id = jsonObject.getString("bill_id");
            }

            if(jsonObject.has("official_title")){
                title = jsonObject.getString("official_title");
            }

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

            if(jsonObject.has("introduced_on")){
                intro = format.parse(jsonObject.getString("introduced_on"));
            }

            billId.setText(id);
            billOfficialTitle.setText(title);
            billIntroducedOn.setText(outputFormat.format(intro));


        }catch (Exception e){
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details;
                details = new Intent(getContext(), BillDetailsPage.class);
                String title = "Bills Info";
                details.putExtra("id", ((TextView) view.findViewById(R.id.billId)).getText().toString());
                details.putExtra("title", title);
                getContext().startActivity(details);
            }
        });

        return rowView;

    };
}
