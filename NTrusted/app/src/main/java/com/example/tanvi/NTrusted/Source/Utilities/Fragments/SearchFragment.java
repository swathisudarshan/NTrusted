package com.example.tanvi.NTrusted.Source.Utilities.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Category> categoryList;
    private List<String> adTypeList;
    private GETOperation getOperation;
    private Spinner categorySpinner;
    private Spinner adTypeSpinner;
    private ArrayAdapter categoryArrayAdapter, adTypeArrayAdapter;
    private Button button;
    private int categoryId;
    private String adType;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_searchadv, container, false);

       categoryList = new ArrayList<Category>();

        //For Category
        getOperation = new GETOperation(Constants.getAllCategories, getActivity().getApplicationContext());
        getOperation.getData(new VolleyGETCallBack(){
                                 @Override
                                 public void onSuccess(String result) {

                                 }

                                 @Override
                                 public void onSuccess(JSONArray result) {

                                     System.out.println("In volley call back !!!!!!!!!!!!!!!!!!"+result.toString());
                                     for(int i=0;i<result.length();i++) {


                                         Category category = new Category();
                                         try {
                                             category.setCategoryID((Integer) result.getJSONObject(i).get("categoryId"));
                                             category.setCategoryName((String) result.getJSONObject(i).get("categoryName"));
                                             categoryList.add(category);
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }

                                     }

                                     Category categoryFirst = new Category();
                                     categoryFirst.setCategoryID(0);
                                     categoryFirst.setCategoryName("Select Category");
                                     categoryList.add(0,categoryFirst);

                                     categoryArrayAdapter = new ArrayAdapter<Category>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,categoryList);

                                     categorySpinner = (Spinner) rootView.findViewById(R.id.appCompatSpinner);
                                     categorySpinner.setAdapter(categoryArrayAdapter);

                                     categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                         @Override
                                         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                             view.setBackgroundColor(Color.WHITE);
                                             if(i>0)
                                             {
                                                 Category item = (Category) adapterView.getItemAtPosition(i);
                                                 System.out.println("Item selected !!!!" + item.getCategoryName() + " ID " + item.getCategoryID());
                                             } }

                                         @Override
                                         public void onNothingSelected(AdapterView<?> adapterView) {

                                         }
                                     });

                                 }




    });


        //For Ad Type
        adTypeList = new ArrayList<String>();
        adTypeList.add("Borrow Products Advertisements");
        adTypeList.add("Lend Products Advertisements");
        adTypeSpinner = (Spinner) rootView.findViewById(R.id.appCompatSpinner1);
        adTypeArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,adTypeList);
        adTypeSpinner.setAdapter(adTypeArrayAdapter);

        adTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button = (Button) rootView.findViewById(R.id.getMeAds);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Category category = (Category)categorySpinner.getSelectedItem();
                categoryId = category.getCategoryID();

                if(adTypeSpinner.getSelectedItem().toString().equals("Borrow Products Advertisements"))
                {
                    BorrowAdFragment borrowAdFragment = new BorrowAdFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("categoryId",categoryId);
                    borrowAdFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.containerView,borrowAdFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    adType = "/fetchRentees";
                }
                if(adTypeSpinner.getSelectedItem().toString().equals("Lend Products Advertisements")){

                    LendAdFragment borrowAdFragment = new LendAdFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("categoryId",categoryId);
                    borrowAdFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.containerView,borrowAdFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    adType = "/fetchRenters";



                }


            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
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
