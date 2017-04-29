package com.example.tanvi.NTrusted.Source.Utilities.Fragments;

import android.content.Context;
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
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Models.User;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.AdverAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.AdverWithoutRankAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.JSONParser.AdvJSONParser;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//This Fragments represents a list view
public class AllAdsFragment extends ListFragment {

    private String userId;
    private Context context;

    private GETOperation getOperation;

    private List<Advertisement> advertisements = new ArrayList<Advertisement>();

    private AdverWithoutRankAdapter adverWithoutRankAdapter;

    private AdvJSONParser advJSONParser;

    private Advertisement advertisement;


    public AllAdsFragment() {
        // Required empty public constructor
    }

    public AllAdsFragment(int categoryId) {
        System.out.println("Category is : "+categoryId);
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_one, container, false);

        this.advertisements.clear();

        //getAllads
        getOperation = new GETOperation(Constants.getAllAds,context);
        getOperation.getData(new VolleyGETCallBack() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onSuccess(JSONArray result) {

                if(result.length()==0)
                    Toast.makeText(getActivity().getApplicationContext(), "No advertisements available", Toast.LENGTH_SHORT).show();

                System.out.println("In volley call back !!!!!!!!!!!!!!!!!!" + result.toString());

                advJSONParser = new AdvJSONParser();

                for (int i = 0; i < result.length(); i++) {

                    try {
                        JSONObject advObj = result.getJSONObject(i);
                        advertisement = advJSONParser.parseJSONWithoutRank(advObj);

                        if(advertisement.getAdPostedby().getId().equals(userId))
                            continue;
                        else
                        advertisements.add(advertisement);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                adverWithoutRankAdapter = new AdverWithoutRankAdapter(getActivity().getApplicationContext(),advertisements);
                setListAdapter(adverWithoutRankAdapter);

            }

        });

        return view;
    }

    //On click of row item
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Advertisement ad = advertisements.get(position);

        System.out.println("Ad clicked is : " + ad.toString());
        //Ad type 1 = Lend product , 2= Borrow product

        //If a lend product adv
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

        //If a borrow product adv
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
