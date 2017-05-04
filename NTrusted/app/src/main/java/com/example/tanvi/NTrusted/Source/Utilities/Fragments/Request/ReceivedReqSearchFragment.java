package com.example.tanvi.NTrusted.Source.Utilities.Fragments.Request;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.CustomSpinnerAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanvi on 5/1/2017.
 */
public class ReceivedReqSearchFragment extends Fragment {

    private List<Category> categoryList;
    private List<String> adTypeList;
    private GETOperation getOperation;
    private Spinner categorySpinner;
    private Spinner adTypeSpinner;
    private ArrayAdapter categoryArrayAdapter, adTypeArrayAdapter;
    private CustomSpinnerAdapter customSpinnerAdapter;
    private Button button;
    private int categoryId;
    private String adType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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

                //categoryArrayAdapter = new ArrayAdapter<Category>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,categoryList);

                categorySpinner = (Spinner) rootView.findViewById(R.id.appCompatSpinner);
                customSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(),categoryList);
                categorySpinner.setAdapter(customSpinnerAdapter);

                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
        adTypeList.add("Borrow Products Requests");
        adTypeList.add("Lend Products Advertisements");
        adTypeSpinner = (Spinner) rootView.findViewById(R.id.appCompatSpinner1);
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(),categoryList);
        adTypeSpinner.setAdapter(customSpinnerAdapter);

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

                System.out.println("In *************************** On click");

                Category category = (Category)categorySpinner.getSelectedItem();
                categoryId = category.getCategoryID();

                if(adTypeSpinner.getSelectedItem().equals("Borrow Products Requests"))
                {
                    System.out.println("In *************************** On click borrow selected");
                    ReceivedReqFragment receivedReqFragment = new ReceivedReqFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("categoryId",categoryId);
                    bundle.putString("adType","borrow");
                    receivedReqFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.containerView, receivedReqFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();


                }
                if(adTypeSpinner.getSelectedItem().toString().equals("Lend Products Requests")){

                    ReceivedReqFragment borrowAdFragment = new ReceivedReqFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("categoryId",categoryId);
                    bundle.putString("adType","lend");
                    borrowAdFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.containerView,borrowAdFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();




                }


            }
        });

        return rootView;


    }
}
