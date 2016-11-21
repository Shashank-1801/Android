package in.shekhar.congress;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.LinkedHashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Legislator.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Legislator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Legislator extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Legislator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Legislator.
     */
    // TODO: Rename and change types and number of parameters
    public static Legislator newInstance(String param1, String param2) {
        Legislator fragment = new Legislator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //legis
        // tabs here

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_legislator, container, false);


        TabHost host = (TabHost) view.findViewById(R.id.legislatorsTabHost);
        host.setup();

        AsyncTaskActivity ata;

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("BY STATE");
        spec.setContent(R.id.tab1);
        spec.setIndicator("BY STATE");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("HOUSE");
        spec.setContent(R.id.tab2);
        spec.setIndicator("HOUSE");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("SENATE");
        spec.setContent(R.id.tab3);
        spec.setIndicator("SENATE");
        host.addTab(spec);

        // fetch data for all legislators
        ata = new AsyncTaskActivity(getActivity(), "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=legislators-all", getActivity(), R.id.legislatorsListViewByState, "Legislators");
        ata.execute();

        // fetch data for legislators house
        ata = new AsyncTaskActivity(getActivity(), "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=legislators-house", getActivity(), R.id.legislatorsListViewHouse, "Legislators");
        ata.execute();

        // fetch data for legislators senate
        ata = new AsyncTaskActivity(getActivity(), "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=legislators-senate", getActivity(), R.id.legislatorsListViewSenate, "Legislators");
        ata.execute();

//        ListView fruitList = (ListView)   getActivity().findViewById(R.id.Legis)
//        fruitList.setSelection(mapIndex.get(selectedIndex.getText()));

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

    private void getIndexList(String[] fruits) {
        LinkedHashMap<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < fruits.length; i++) {
            String fruit = fruits[i];
            String index = fruit.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }
}
