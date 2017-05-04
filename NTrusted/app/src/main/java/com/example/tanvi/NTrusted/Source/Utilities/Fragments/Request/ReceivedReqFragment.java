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
import android.widget.Toast;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Request;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.WithRankAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.WithoutRankAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.JSONParser.JSONParser;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanvi on 5/1/2017.
 */
public class ReceivedReqFragment extends ListFragment {


    private int categoryId;
    private String userId;
    private Context context;

    private GETOperation getOperation;

    private List<Request> requests = new ArrayList<Request>();

    private WithRankAdapter withRankAdapter;

    private WithoutRankAdapter withoutRankAdapter;

    private JSONParser JSONParser = new JSONParser();

    //private Advertisement advertisement;

    private Request request;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyPref",0);
        this.userId = pref.getString(Constants.UserID,null);


        System.out.println(" User is:"+this.userId);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.fragment_one, container, false);

        Bundle args = getArguments();
        if(args.getString("adType").equals("borrow")) {

            System.out.println("Has arguments !!" + args.getInt("categoryId") + "and user id is " + userId);
            getOperation = new GETOperation(Constants.getBorrowRequestsforCat + "?receiverId=" + userId + "&catId=" + args.getInt("categoryId"), context);
            getOperation.getData(new VolleyGETCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onSuccess(JSONArray result) {

                    if (result.length() == 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "No requests available for this selection", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    } else {
                        for (int i = 0; i < result.length(); i++) {

                            try {

                                System.out.println("Request in success is ------>" + result.getJSONObject(i).toString());
                                JSONObject object = result.getJSONObject(i);
                                request = JSONParser.parseRequestJSONWithRank(object);

                                requests.add(request);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                        withRankAdapter = new WithRankAdapter(getActivity().getApplicationContext(), requests,0);
                        setListAdapter(withRankAdapter);


                    }

                }

            });


        }

        if(args.getString("adType").equals("lend")){

            System.out.println("Has arguments !!" + args.getInt("categoryId") + "and user id is " + userId);
            getOperation = new GETOperation(Constants.getLendRequestsforCat + "?receiverId=" + userId + "&catId=" + args.getInt("categoryId"), context);
            getOperation.getData(new VolleyGETCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onSuccess(JSONArray result) {

                    if (result.length() == 0) {
                        Toast.makeText(getActivity().getApplicationContext(), "No requests available for this selection", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    } else {
                        for (int i = 0; i < result.length(); i++) {

                            try {

                                System.out.println("Request in success is ------>" + result.getJSONObject(i).toString());
                                JSONObject object = result.getJSONObject(i);
                                request = JSONParser.parseRequestJSONWithRank(object);

                                requests.add(request);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                        withRankAdapter = new WithRankAdapter(getActivity().getApplicationContext(), requests,0);
                        setListAdapter(withRankAdapter);


                    }

                }

            });


        }





        return view;


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
            ReqDetailFragment reqDetailFragment = new ReqDetailFragment();
            Request request1 = (Request) l.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("request", (Serializable)request1 );
            reqDetailFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.containerView, reqDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
    }
}
