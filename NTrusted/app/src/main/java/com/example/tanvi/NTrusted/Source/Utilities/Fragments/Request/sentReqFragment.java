package com.example.tanvi.NTrusted.Source.Utilities.Fragments.Request;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Request;
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

/**
 * Created by tanvi on 5/1/2017.
 */
public class SentReqFragment extends ListFragment {



    private String userId;
    private Context context;

    private GETOperation getOperation;

    private List<Request> requests = new ArrayList<Request>();

    private WithoutRankAdapter withoutRankAdapter;

    private com.example.tanvi.NTrusted.Source.Utilities.JSONParser.JSONParser JSONParser;

    private Request request;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyPref",0);
        this.userId = pref.getString(Constants.UserID,null);

        System.out.println(" User is:"+this.userId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_one, container, false);
        this.requests.clear();

        //get all active transactions
        getOperation = new GETOperation(Constants.getAllSentRequests+"?senderId="+userId,context);
        getOperation.getData(new VolleyGETCallBack() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onSuccess(JSONArray result) {

                System.out.println("In volley call back ONEEE!!!!!!!!!!!!!!!!!!" + result.toString());

                JSONParser = new JSONParser();

                for (int i = 0; i < result.length(); i++) {

                    try {
                        JSONObject object = result.getJSONObject(i);
                        request = JSONParser.parseRequestJSONWithoutRank(object);
                        requests.add(request);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                        withoutRankAdapter = new WithoutRankAdapter(getActivity().getApplicationContext(),requests,true);
                        setListAdapter(withoutRankAdapter);


            }

        });



        return view;


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ReqDetailFragment reqDetailFragment = new ReqDetailFragment();
        Request request1 = (Request) l.getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("request", (Serializable)request1 );
        bundle.putString("sent","true");
        reqDetailFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerView, reqDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
