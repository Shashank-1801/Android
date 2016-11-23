package in.shekhar.congress;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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

    SharedPreferences sp;
    public static final String PREFSTRING = "CongressFav" ;

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


        TabHost host = (TabHost) view.findViewById(R.id.favoritesTabHost);
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

        sp = getActivity().getSharedPreferences(PREFSTRING, Context.MODE_PRIVATE);
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
                    committeesList.add(dataString);
                }else if(jsonObject.has("committee_id")){
                    String id = jsonObject.getString("committee_id");
                    billsList.add(dataString);
                }else{
                    Log.d("", "Abey ye kahan se aa gaya : " + dataString);
                }


            }catch (org.json.JSONException je){

            }
        }

        ArrayAdapter adapter;
        // Legislators
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, legislatorsList);
        ListView legislatorsListView = (ListView) view.findViewById(R.id.favoritesListViewLegislators);
        legislatorsListView.setAdapter(adapter);

        // Bills
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, billsList);
        ListView billsListView = (ListView) view.findViewById(R.id.favoritesListViewBills);
        billsListView.setAdapter(adapter);


        // Committees
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, committeesList);
        ListView committeesListView = (ListView) view.findViewById(R.id.favoritesListViewCommittees);
        committeesListView.setAdapter(adapter);

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
