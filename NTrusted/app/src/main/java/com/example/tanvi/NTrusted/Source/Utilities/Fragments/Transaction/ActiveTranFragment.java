package com.example.tanvi.NTrusted.Source.Utilities.Fragments.Transaction;

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
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Transaction;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.WithoutRankAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Advertisement.LendingAdDetailFragment;
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
 * Created by tanvi on 5/2/2017.
 */
public class ActiveTranFragment extends ListFragment {


    private String userId;
    private Context context;

    private GETOperation getOperation;

    private List<Transaction> transactions = new ArrayList<Transaction>();

    private WithoutRankAdapter withoutRankAdapter;

    private JSONParser JSONParser;

    private Transaction transaction;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        this.userId = pref.getString(Constants.UserID, null);

        System.out.println(" User is:" + this.userId);
        this.transactions.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        this.transactions.clear();

        //get all active transactions
        getOperation = new GETOperation(Constants.activeTransactionsWhenUserIsRenter + "?userId=" + userId, context);
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
                        transaction = JSONParser.parseTransactionJSON(object);
                        transactions.add(transaction);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                //Make a call to get all transactions where user is rentee


                getOperation = new GETOperation(Constants.activeTransactionsWhenUserIsRentee + "?userId=" + userId, context);
                getOperation.getData(new VolleyGETCallBack() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onSuccess(JSONArray result) {

                        //transactions.clear();

                        System.out.println("In volley call back TWOOO!!!!!!!!!!!!!!!!!!" + result.toString());

                        JSONParser = new JSONParser();

                        for (int i = 0; i < result.length(); i++) {

                            try {
                                JSONObject object = result.getJSONObject(i);
                                transaction = JSONParser.parseTransactionJSON(object);
                                transactions.add(transaction);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        withoutRankAdapter = new WithoutRankAdapter(getActivity().getApplicationContext(), transactions, 1);
                        setListAdapter(withoutRankAdapter);

                    }
                });

            }

        });


        return view;


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {


        //Transaction transaction = transactions.get(position);

        System.out.println("Transaction clicked is : " + transaction.toString());
        //Ad type 1 = Lend product , 2= Borrow product


        TransactionDetailFragment transactionDetailFragment = new TransactionDetailFragment();
        //Advertisement adver = (Advertisement) l.getItemAtPosition(position);

        Transaction transaction = (Transaction) l.getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("transaction", (Serializable) transaction);
        transactionDetailFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transactionFM = getActivity().getSupportFragmentManager().beginTransaction();
        transactionFM.replace(R.id.containerView, transactionDetailFragment);
        transactionFM.addToBackStack(null);
        transactionFM.commit();


    }
}