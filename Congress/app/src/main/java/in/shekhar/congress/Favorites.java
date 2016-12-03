package in.shekhar.congress;

/**
 * Created by Shekhar on 11/17/2016.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Favorites.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Favorites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorites extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String PREFSTRING = "CongressFav" ;
    public static Activity activity;
    public static View acitivityView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Favorites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorites.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorites newInstance(String param1, String param2) {
        Favorites fragment = new Favorites();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        activity = getActivity();
        acitivityView = view;

        TabHost host = (TabHost) acitivityView.findViewById(R.id.favoritesTabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("LEGISLATORS");
        spec.setContent(R.id.tab1);
        spec.setIndicator("LEGISLATORS");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("BILLS");
        spec.setContent(R.id.tab2);
        spec.setIndicator("BILLS");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("COMMITTEES");
        spec.setContent(R.id.tab3);
        spec.setIndicator("COMMITTEES");
        host.addTab(spec);


        loadPageContents();
        view = acitivityView;
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static void loadPageContents(){

        acitivityView.invalidate();
        SharedPreferences sp;

        sp = activity.getSharedPreferences(PREFSTRING, Context.MODE_PRIVATE);
        Map<String,?> mData = new HashMap<>();

        mData = sp.getAll();

        List<String> legislatorsList = new ArrayList<>();
        List<String> billsList = new ArrayList<>();
        List<String> committeesList = new ArrayList<>();

        for(String key : mData.keySet()){
            String dataString = (String) mData.get(key);
            try {
                JSONObject jsonObject = new JSONObject(dataString);
                if(jsonObject.has("bioguide_id")) {
                    String id = jsonObject.getString("bioguide_id");
                    legislatorsList.add(dataString);
                }else if(jsonObject.has("bill_id")){
                    String id = jsonObject.getString("bill_id");
                    billsList.add(dataString);
                }else if(jsonObject.has("committee_id")){
                    String id = jsonObject.getString("committee_id");
                    committeesList.add(dataString);
                }else{
                    Log.d("", "Abey ye kahan se aa gaya : " + dataString);
                }


            }catch (org.json.JSONException je){
                je.printStackTrace();
            }
        }

        LegislatorsAdapter legislatorAdapter;
        // Legislators
        legislatorAdapter = new LegislatorsAdapter(activity, legislatorsList.toArray(new String[0]));
        ListView legislatorsListView = (ListView) acitivityView.findViewById(R.id.favoritesListViewLegislators);
        LinearLayout linearLayout = (LinearLayout) acitivityView.findViewById(R.id.favoritesListViewLegislatorsIndex);

        legislatorsListView.setAdapter(legislatorAdapter);

        try {
            JSONArray jArray = new JSONArray(legislatorsList);
            JSONArray sorted = sortList(jArray, "last_name");

            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                list.add(sorted.getString(i));
            }
            ArrayList<String> names = new ArrayList<>();
            // indexer for legislators
            for (int i = 0; i < sorted.length(); i++) {
                names.add(sorted.getJSONObject(i).getString("last_name"));
            }

            final Map<String, Integer> indexMap = new LinkedHashMap();
            for (int i = 0; i < list.size(); i++) {
                if (indexMap.get(names.get(i).substring(0, 1)) == null) {
                    indexMap.put(names.get(i).substring(0, 1), i);
                }
            }

            List<String> indexList = new ArrayList<String>(indexMap.keySet());
            for (String index : indexList) {
                TextView textView = new TextView(activity);
                textView.setText(index);
                textView.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView selectedIndex = (TextView) view;
                        ListView lv = (ListView) activity.findViewById(R.id.favoritesListViewLegislators);
                        lv.setSelection(indexMap.get(selectedIndex.getText()));
                    }
                });
                linearLayout.addView(textView);

            }
        }catch (Exception e){
            e.printStackTrace();
        };




        // Bills
        BillsAdapter billsAdapter = new BillsAdapter(activity, billsList.toArray(new String[0]));
        ListView billsListView = (ListView) acitivityView.findViewById(R.id.favoritesListViewBills);
        billsListView.setAdapter(billsAdapter);


        // Committees
        CommitteesAdapter committeesAdapter = new CommitteesAdapter(activity, committeesList.toArray(new String[0]));
        ListView committeesListView = (ListView) acitivityView.findViewById(R.id.favoritesListViewCommittees);
        committeesListView.setAdapter(committeesAdapter);


    }

    public static JSONArray sortList(JSONArray jArray, String tag){
        try {
            ArrayList<JSONObject> listJson = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                listJson.add(new JSONObject(jArray.getString(i)));
            }
            Collections.sort(listJson, new JsonComp(tag, true));

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

