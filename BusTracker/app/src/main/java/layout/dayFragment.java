package layout;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Time;

import java.util.ArrayList;

import juniordesignteam25.bustracker.R;
import juniordesignteam25.bustracker.singleClassNotUsedAnymore;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dayFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ListView classList;
    private static final String ARG_PARAM1 = "day";
    private static int index = 0;


    private String day;
    private static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private OnFragmentInteractionListener mListener;



    public dayFragment() {

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param day Parameter 1.
     * @return A new instance of fragment dayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static dayFragment newInstance(int day) {
        dayFragment fragment = new dayFragment();
        Bundle args = new Bundle();
        index = day;
        args.putString(ARG_PARAM1, days[day]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getString(ARG_PARAM1);
        }
        System.out.println(ARG_PARAM1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<singleClassNotUsedAnymore> classes = new ArrayList<singleClassNotUsedAnymore>();
        classes.add(new singleClassNotUsedAnymore("Systems and Networks", 2200, "MWF", new Time(12, 5, 0), new Time(1, 5, 0), "CULC 152"));
        classes.add(new singleClassNotUsedAnymore("Mobiles Apps and Services", 4261, "TT", new Time(12, 5, 0), new Time(1, 25, 0), "CoC 16"));
        classes.add(new singleClassNotUsedAnymore("Intro to Info Security", 4235, "TT", new Time(3, 5, 0), new Time(4, 25, 0), "CoC 16"));

        System.out.println("Number of classes: " + classes.size());
        ArrayList<singleClassNotUsedAnymore> list = new ArrayList<singleClassNotUsedAnymore>();
        for (singleClassNotUsedAnymore c : classes) {
            System.out.println("Class days: " + c.getDays());
            System.out.println("Tab: " + Character.toString(days[index].charAt(0)));
            if (c.getDays().contains(Character.toString(days[index].charAt(0)))) {
                list.add(c);
            }
        }
        System.out.println("Number of classes on that day: " + list.size());

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_day,
                container, false);
        classList = (ListView) rootView.findViewById(R.id.export_class_list);
        exportClassListAdapter adapter = new exportClassListAdapter(getActivity(), list);
        classList.setAdapter(adapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
