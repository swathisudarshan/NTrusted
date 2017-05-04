package com.example.tanvi.NTrusted.Source.Utilities.Fragments.Advertisement;

import  android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.WithRankAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.WithoutRankAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.JSONParser.JSONParser;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BorrowAdFragment extends ListFragment {


    private int categoryId;
    private String userId;
    private Context context;

    private GETOperation getOperation;

    private List<Advertisement> advertisements = new ArrayList<Advertisement>();

    private WithRankAdapter withRankAdapter;

    private WithoutRankAdapter withoutRankAdapter;

    private JSONParser JSONParser = new JSONParser();

    private Advertisement advertisement;

    public BorrowAdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.context = getActivity().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyPref",0);
        this.userId = pref.getString(Constants.UserID,null);


        System.out.println(" User is:"+this.userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.advertisements.clear();
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_one, container, false);

        Bundle args = getArguments();
        if(args==null) {

            getOperation = new GETOperation(Constants.getAllBorrowAds, context);
            getOperation.getData(new VolleyGETCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onSuccess(JSONArray result) {

                    if(result.length()==0)
                    //Toast.makeText(getActivity().getApplicationContext(), "No borrowing advertisements available", Toast.LENGTH_SHORT).show();


                    for (int i = 0; i < result.length(); i++) {

                        try {

                            JSONObject advObj = result.getJSONObject(i);
                            advertisement = JSONParser.parseAdvJSONWithoutRank(advObj);

                            if(advertisement.getAdPostedby().getId().equals(userId))
                                continue;
                            else
                                advertisements.add(advertisement);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    withoutRankAdapter = new WithoutRankAdapter(getActivity().getApplicationContext(),advertisements);
                    setListAdapter(withoutRankAdapter);



                }

            });

        }

        else{

            System.out.println("Has arguments !!"+args.getInt("categoryId")+"and user id is "+userId);

            getOperation = new GETOperation(Constants.getRentees+"?catId="+args.getInt("categoryId")+"&RenterId="+userId, context);
            getOperation.getData(new VolleyGETCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onSuccess(JSONArray result) {

                    if (result.length() == 0){
                        Toast.makeText(getActivity().getApplicationContext(), "No advertisements available for this selection", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    }


                    else {
                        for (int i = 0; i < result.length(); i++) {

                            try {

                                System.out.println("Advertisement in success is ------>" + result.getJSONObject(i).toString());
                                JSONObject object = result.getJSONObject(i);
                                advertisement = JSONParser.parseAdvJSONWithRank(object);


                                if (advertisement.getAdPostedby().getId().equals(userId)) {
                                    System.out.println("ITS MEEEE !!!!!!!!!!!!!!!!!!!!!!!!!");
                                    continue;


                                } else {
                                    advertisements.add(advertisement);
                                    System.out.println("Advertisment " + advertisement.getAdId() + " added to the list !!");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                        withRankAdapter = new WithRankAdapter(getActivity().getApplicationContext(), advertisements);
                        setListAdapter(withRankAdapter);


                    }

                }

            });



        }




        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    Advertisement ad = advertisements.get(position);

        System.out.println("Ad clicked is : " + ad.toString());
        //Ad type 1 = Lend product , 2= Borrow product
        if(ad.getAdType() == 1)
        {

            LendingAdDetailFragment lendingAdDetailFragment = new LendingAdDetailFragment();
            Advertisement adver = (Advertisement) l.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("advertisement", (Serializable)adver );
            lendingAdDetailFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.containerView, lendingAdDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
        else
        {

            System.out.println("HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
            BorrowingAdDetailFragment borrowingAdDetailFragment = new BorrowingAdDetailFragment();
            Advertisement adver = (Advertisement) l.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("advertisement", (Serializable)adver );
            borrowingAdDetailFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.containerView, borrowingAdDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }



    }



}
