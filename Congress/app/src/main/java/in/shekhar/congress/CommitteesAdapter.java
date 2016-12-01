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

/**
 * Created by Shekhar on 11/22/2016.
 */

public class CommitteesAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] data;

    public CommitteesAdapter(Activity context, String[] info) {
        super(context, R.layout.layout_bill_adapter);

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
        View rowView=inflater.inflate(R.layout.layout_committee_adapter, null,true);
        Log.d("", "populating...");
        TextView commId = (TextView) rowView.findViewById(R.id.committeeId);
        TextView commName = (TextView) rowView.findViewById(R.id.committeeName);
        TextView commChamber = (TextView) rowView.findViewById(R.id.committeeChamber);
        TextView commInfoString = (TextView) rowView.findViewById(R.id.committeeInfoString);

        try{
            String d = data[position];
            JSONObject jsonObject = new JSONObject(d);
            String id = null ;
            String name = null;
            String chamber = null;

            if(jsonObject.has("committee_id")){
                id = jsonObject.getString("committee_id");
            }

            if(jsonObject.has("name")){
                name = jsonObject.getString("name");
            }

            if(jsonObject.has("chamber")){
                chamber = jsonObject.getString("chamber");
            }

            commId.setText(id);
            commName.setText(name);
            commChamber.setText(chamber);
            commInfoString.setText(d);

        }catch (Exception e){
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details;
                details = new Intent(getContext(), CommitteeDetailsPage.class);
                String title = "Committee Info";
                details.putExtra("id", ((TextView) view.findViewById(R.id.committeeId)).getText().toString());
                details.putExtra("title", title);
                details.putExtra("info", ((TextView) view.findViewById(R.id.committeeInfoString)).getText().toString());
                getContext().startActivity(details);
            }
        });


        return rowView;

    };
}
