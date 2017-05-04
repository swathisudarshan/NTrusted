package com.example.tanvi.NTrusted.Source.Utilities.Fragments.Transaction;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Activities.HomePageActivity;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Transaction;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.MISC.TabFragment;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tanvi on 5/2/2017.
 */

public class MyAlertDialogFragment extends DialogFragment {

    private TextView tid,product,rentee,start,phone;
    private Transaction transaction;
    private String title,userId;
    private RatingBar ratingBar;
    private GETOperation getOperation;
    private HashMap<Float,Float> rankMapping= new HashMap<Float,Float>();

    public MyAlertDialogFragment() {
        // Empty constructor required for DialogFragment
    }


    public static MyAlertDialogFragment newInstance(String title, Transaction transaction) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("transaction",transaction);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref",0);
        userId = pref.getString(Constants.UserID,null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);

        tid= (TextView) view.findViewById(R.id.tid);
        product= (TextView) view.findViewById(R.id.product);
        rentee= (TextView) view.findViewById(R.id.renteeName);
        start= (TextView) view.findViewById(R.id.start);
        phone= (TextView) view.findViewById(R.id.phone);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        Bundle bundle = getArguments();
        transaction = (Transaction) bundle.getSerializable("transaction");
        title = bundle.getString("title");
        getDialog().setTitle(title);

        System.out.println("Title is "+title);

        Dialog d = this.getDialog();
        d.setTitle(title);


        tid.setText(String.valueOf(transaction.getTransactionId()));
        product.setText(transaction.getAd().getProductName());
        rentee.setText(transaction.getRentee().getName());
        start.setText(String.valueOf(transaction.getStartDate()));
        phone.setText(transaction.getRentee().getPhone());


        System.out.println("IN DIALOG FRAGMENT ------->"+tid.getText()+" "+product.getText());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            float rating = 0;
            @Override
            public void onRatingChanged(RatingBar ratingBar, float ratingView, boolean b) {



                rankMapping.put(-2f,1f);
                rankMapping.put(-1.9f,1.1f);
                rankMapping.put(-1.8f,1.2f);
                rankMapping.put(-1.7f,1.3f);
                rankMapping.put(-1.6f,1.4f);
                rankMapping.put(-1.5f,1.5f);
                rankMapping.put(-1.4f,1.6f);
                rankMapping.put(-1.3f,1.7f);
                rankMapping.put(-1.2f,1.8f);
                rankMapping.put(-1.1f,1.9f);
                rankMapping.put(-1.0f,2.0f);
                rankMapping.put(-0.9f,2.1f);
                rankMapping.put(-0.8f,2.2f);
                rankMapping.put(-0.7f,2.3f);
                rankMapping.put(-0.6f,2.4f);
                rankMapping.put(-0.5f,2.5f);
                rankMapping.put(-0.4f,2.6f);
                rankMapping.put(-0.3f,2.7f);
                rankMapping.put(-0.2f,2.8f);
                rankMapping.put(-0.1f,2.9f);
                rankMapping.put(0.0f,3.0f);
                rankMapping.put(0.1f,3.1f);
                rankMapping.put(0.2f,3.2f);
                rankMapping.put(0.3f,3.3f);
                rankMapping.put(0.4f,3.4f);
                rankMapping.put(0.5f,3.5f);
                rankMapping.put(0.6f,3.6f);
                rankMapping.put(0.7f,3.7f);
                rankMapping.put(0.8f,3.8f);
                rankMapping.put(0.9f,3.9f);
                rankMapping.put(1.0f,4.0f);
                rankMapping.put(1.1f,4.1f);
                rankMapping.put(1.2f,4.2f);
                rankMapping.put(1.3f,4.3f);
                rankMapping.put(1.4f,4.4f);
                rankMapping.put(1.5f,4.5f);
                rankMapping.put(1.6f,4.6f);
                rankMapping.put(1.7f,4.7f);
                rankMapping.put(1.8f,4.8f);
                rankMapping.put(1.9f,4.9f);
                rankMapping.put(2.0f,5.0f);

                Iterator it = rankMapping.entrySet().iterator();
                while(it.hasNext()){

                    Map.Entry pair = (Map.Entry)it.next();
                    if(pair.getValue().equals(ratingView)){

                        rating = (float) pair.getKey();

                    }


                }


//                if(ratingView == 1)
//                    rating=-2;
//                if(ratingView == 2)
//                    rating=-1;
//                if(ratingView == 3)
//                    rating=0;
//                if(ratingView == 4)
//                    rating=1;
//                if(ratingView == 5)
//                    rating=2;

                int tid = transaction.getTransactionId();
                postRating(rating,tid);

            }
        });

        return view;
    }

    private void postRating(float rating, int tid) {

        getOperation = new GETOperation(Constants.addRating + "?trxId=" + tid + "&userId=" + userId + "&rating=" + rating, getActivity().getApplicationContext());
        getOperation.getStringData(new VolleyGETCallBack() {


            @Override
            public void onSuccess(String result) {

                Intent intent = new Intent(getActivity(), HomePageActivity.class);
                startActivity(intent);
                getDialog().dismiss();

            }

            @Override
            public void onSuccess(JSONArray result) {

            }

        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

}


