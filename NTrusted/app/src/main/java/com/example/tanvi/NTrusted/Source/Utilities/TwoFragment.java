package com.example.tanvi.NTrusted.Source.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Activities.BorrowingAdDetailsActivity;
import com.example.tanvi.NTrusted.Source.Activities.LendingAdDetailsActivity;
import com.example.tanvi.NTrusted.Source.Activities.MainActivity;
import com.example.tanvi.NTrusted.Source.Activities.UserInformationActivity;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TwoFragment extends ListFragment {


    private int categoryId;
    private String userId;
    private Context context;

    private GETOperation getOperation;

    private List<Advertisement> advertisements = new ArrayList<Advertisement>();

//    String[] AndroidOS = new String[] { "Cupcake","Donut","Eclair","Froyo","Gingerbread","Honeycomb","Ice Cream SandWich","Jelly Bean","KitKat" };
//    String[] Version = new String[]{"1.5","1.6","2.0-2.1","2.2","2.3","3.0-3.2","4.0","4.1-4.3","4.4"};


    public TwoFragment() {
        // Required empty public constructor
    }

    public TwoFragment(int categoryId, Context context) {

        this.categoryId=categoryId;
        this.context = context;
        SharedPreferences pref = context.getSharedPreferences("MyPref",0);
        this.userId = pref.getString(Constants.UserID,null);


        System.out.println("Category is : "+this.categoryId+" User is:"+this.userId);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.advertisements.clear();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_one, container, false);

        System.out.println("**********Category ID is "+this.categoryId+" Renter Id is "+this.userId);

        //getOperation = new GETOperation(Constants.getRentees+"?catId="+this.categoryId+"&RenterId="+this.userId,context);
        /*getOperation.getData(new VolleyGETCallBack() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onSuccess(JSONArray result) {

                System.out.println("In volley call back !!!!!!!!!!!!!!!!!!" + result.toString());
                for (int i = 0; i < result.length(); i++) {

                    Advertisement adv = new Advertisement();
                    try {
                        System.out.print("************** Object of Result is "+result.getJSONObject(i));
                        adv.setAdId(String.valueOf(result.getJSONObject(i).get("adId")));
                        adv.setAdPostedby((String)result.getJSONObject(i).get("user"));
                        adv.setProductName((String) result.getJSONObject(i).get("productName"));
                        adv.setProductDesc((String) result.getJSONObject(i).get("productDescription"));
                        adv.setProductPrice(String.valueOf(result.getJSONObject(i).get("productPrice")) );
                        adv.setAdType((int)result.getJSONObject(i).get("adType"));
                        adv.setProductCategory((int) result.getJSONObject(i).get("category"));
                        adv.setPostDate(String.valueOf(result.getJSONObject(i).getString("postDate")));
                        adv.setStatus(String.valueOf(result.getJSONObject(i).get("active")) );
                        advertisements.add(adv);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                ArrayAdapter<Advertisement> adapter = new ArrayAdapter<Advertisement>(getActivity(), android.R.layout.simple_list_item_1, advertisements);
                setListAdapter(adapter);


            }

        });*/
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    Advertisement ad = advertisements.get(position);

        System.out.println("Ad clicked is : " + ad.toString());
        //Ad type 1 = Lend product , 2= Borrow product
        if(ad.getAdType() == 1)
        {
            Intent intent = new Intent(getActivity().getApplicationContext(),LendingAdDetailsActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("adId",ad.getAdId());
            bundle.putInt("catId",ad.getProductCategory());
            bundle.putString("Postedby",ad.getAdPostedby());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getActivity().getApplicationContext(), BorrowingAdDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("adId",ad.getAdId());
            bundle.putInt("catId",ad.getProductCategory());
            bundle.putString("Postedby",ad.getAdPostedby());
            intent.putExtras(bundle);
            startActivity(intent);
        }



    }



}
