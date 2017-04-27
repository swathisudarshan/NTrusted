package com.example.tanvi.NTrusted.Source.Utilities.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Models.User;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.AdverAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LendAdFragment extends ListFragment {

    private String userId;
    private Context context;

    private GETOperation getOperation;

    private List<Advertisement> advertisements = new ArrayList<Advertisement>();

    private AdverAdapter adverAdapter;


    public LendAdFragment() {
        // Required empty public constructor
    }

    public LendAdFragment(int categoryId) {
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
        this.advertisements.clear();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_one, container, false);

        Bundle args = getArguments();

        if(args==null) {
            getOperation = new GETOperation(Constants.getAllLendingAds, context);
            getOperation.getData(new VolleyGETCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onSuccess(JSONArray result) {

                    System.out.println("In volley call back !!!!!!!!!!!!!!!!!!" + result.toString());
                    for (int i = 0; i < result.length(); i++) {

                        Advertisement adv = new Advertisement();
                        try {
                            //System.out.print("************** Object of Result is "+result.getJSONObject(i));

                            JSONArray array = result.getJSONArray(i);
                            System.out.println("*************************" + i + "*********************");
                            System.out.println("0 ------> " + array.get(0));
                            System.out.println("1 ------> " + array.get(1));
                            System.out.println("2 ------> " + array.get(2));

                            JSONObject advObj = array.getJSONObject(0);

                            JSONObject userObj = array.getJSONObject(2);

                            JSONObject catObj = array.getJSONObject(1);

                            adv.setAdId(String.valueOf(advObj.get("adId")));
                            adv.setProductName(String.valueOf(advObj.get("productName")));
                            adv.setProductDesc(String.valueOf(advObj.get("productDescription")));
                            adv.setProductPrice(String.valueOf(advObj.get("productPrice")));
                            adv.setAdType((Integer) advObj.get("adType"));
                            Date date = new Date();
                            date.setTime((Long) advObj.get("postDate"));
                            adv.setPostDate(date);
                            adv.setStatus(String.valueOf(advObj.get("active")));


                            User user = new User();
                            user.setId((String) userObj.get("fbId"));
                            user.setName((String) userObj.get("name"));
                            user.setPhone((String) userObj.get("phoneNumber"));
                            user.setAddress((String) userObj.get("address"));

                            adv.setAdPostedby(user);

                            Category category = new Category();
                            category.setCategoryID(catObj.getInt("categoryId"));
                            category.setCategoryName(catObj.getString("categoryName"));

                            adv.setProductCategory(category);


                            if (adv.getAdPostedby().getId().equals(userId))
                                continue;
                            else
                                advertisements.add(adv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    adverAdapter = new AdverAdapter(getActivity().getApplicationContext(), advertisements);
                    setListAdapter(adverAdapter);

                }

            });
        }
        else{

            System.out.println("Has arguments !!"+args.getInt("categoryId")+"and user id is "+userId);

            getOperation = new GETOperation(Constants.getRenters+"?catId="+args.getInt("categoryId")+"&RenteeId="+userId, context);
            getOperation.getData(new VolleyGETCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onSuccess(JSONArray result) {

                    for (int i = 0; i < result.length(); i++) {

                        Advertisement adv = new Advertisement();
                        try {

                            System.out.print("************** Lend Ad Fragment "+result.getJSONObject(i));
                            adv.setAdId(String.valueOf(result.getJSONObject(i).get("adId")));

                            User user = new User();
                            user.setId((String) result.getJSONObject(i).get("user"));
                            adv.setAdPostedby(user);
                            adv.setProductName((String) result.getJSONObject(i).get("productName"));
                            adv.setProductDesc((String) result.getJSONObject(i).get("productDescription"));
                            adv.setProductPrice(String.valueOf(result.getJSONObject(i).get("productPrice")) );
                            adv.setAdType((int)result.getJSONObject(i).get("adType"));

                            Category category = new Category();
                            category.setCategoryID(Integer.parseInt(String.valueOf(result.getJSONObject(i).get("category"))));
                            adv.setProductCategory(category);
                            Date date = new Date();
                            date.setTime((Long) result.getJSONObject(i).get("postDate"));
                            adv.setPostDate(date);
                            adv.setStatus(String.valueOf(result.getJSONObject(i).get("active")) );


                            if(adv.getAdPostedby().getId().equals(userId))
                            {
                                System.out.println("ITS MEEEE !!!!!!!!!!!!!!!!!!!!!!!!!");
                                continue;


                            }

                            else {
                                advertisements.add(adv);
                                System.out.println("Advertisment "+adv.getAdId()+" added to the list !!");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    ArrayAdapter<Advertisement> adapter = new ArrayAdapter<Advertisement>(getActivity(), android.R.layout.simple_list_item_1, advertisements);
                    setListAdapter(adapter);


                }

            });
        }


        return view;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

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
