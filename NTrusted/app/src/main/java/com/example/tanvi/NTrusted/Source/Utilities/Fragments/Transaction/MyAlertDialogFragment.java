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

/**
 * Created by tanvi on 5/2/2017.
 */

public class MyAlertDialogFragment extends DialogFragment {

    private TextView tid,product,rentee,start,phone;
    private Transaction transaction;
    private String title,userId;
    private RatingBar ratingBar;
    private GETOperation getOperation;

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

                if(ratingView == 1)
                    rating=-2;
                if(ratingView == 2)
                    rating=-1;
                if(ratingView == 3)
                    rating=0;
                if(ratingView == 4)
                    rating=1;
                if(ratingView == 5)
                    rating=2;

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
//                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                TabFragment tabFragment = new TabFragment();
//                transaction.replace()

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


