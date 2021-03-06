package in.shekhar.congress;

/**
 * Created by Shekhar on 11/17/2016.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class Committee extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Committee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Committee.
     */
    // TODO: Rename and change types and number of parameters
    public static Committee newInstance(String param1, String param2) {
        Committee fragment = new Committee();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_committee, container, false);


        TabHost host = (TabHost) view.findViewById(R.id.commiteesTabHost);
        host.setup();

        AsyncTaskActivity ata;

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("HOUSE");
        spec.setContent(R.id.tab1);
        spec.setIndicator("HOUSE");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("SENATE");
        spec.setContent(R.id.tab2);
        spec.setIndicator("SENATE");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("JOINT");
        spec.setContent(R.id.tab3);
        spec.setIndicator("JOINT");
        host.addTab(spec);

        // fetch data for house
        ata = new AsyncTaskActivity(getActivity(), "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=comm_house", getActivity(), R.id.committeesListViewHouse, "Committees", 0);
        ata.execute();

        // fetch data for senate
        ata = new AsyncTaskActivity(getActivity(), "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=comm_senate", getActivity(), R.id.committeesListViewSeante, "Committees", 0);
        ata.execute();

        // fetch data for joint
        ata = new AsyncTaskActivity(getActivity(), "http://default-environment.vmdfp4m4zb.us-west-2.elasticbeanstalk.com/phpfunc.php?dbtype=comm_joint", getActivity(), R.id.committeesListViewJoint, "Committees", 0);
        ata.execute();



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
