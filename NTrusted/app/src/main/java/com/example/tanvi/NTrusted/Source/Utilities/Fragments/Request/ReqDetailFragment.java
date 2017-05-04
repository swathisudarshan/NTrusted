package com.example.tanvi.NTrusted.Source.Utilities.Fragments.Request;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Activities.HomePageActivity;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Request;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Advertisement.BorrowingAdDetailFragment;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;

/**
 * Created by tanvi on 5/1/2017.
 */
public class ReqDetailFragment extends Fragment {


    private TextView product, description, category, postedBy, date;

    private Button send, cancel;

    private String userId;

    private GETOperation getOperation;

    String type;
    private String mParam1;
    private String mParam2;

    private BorrowingAdDetailFragment.OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_borrowing_ad_details, container, false);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        userId = pref.getString(Constants.UserID, null);

        product = (TextView) view.findViewById(R.id.product);
        description = (TextView) view.findViewById(R.id.desciption);
        postedBy = (TextView) view.findViewById(R.id.by);
        category = (TextView) view.findViewById(R.id.cat);
        date = (TextView) view.findViewById(R.id.date);

        send = (Button) view.findViewById(R.id.send);
        cancel = (Button) view.findViewById(R.id.cancel);
        send.setText("Accept");
        cancel.setText("Decline");

        Bundle args = getArguments();
        type = args.getString("sent");


        if(type.equals("true")){

            send.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);

        }

        final Request request = (Request) args.getSerializable("request");
        product.setText(request.getAd().getProductName());
        description.setText(request.getAd().getProductDesc());
        postedBy.setText(request.getSender().getName());
        category.setText(request.getAd().getProductCategory().getCategoryName());
        date.setText(String.valueOf(request.getRequestDate()));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOperation = new GETOperation(Constants.acceptDeclineRequest+"?requestId="+request.getRequestId()+"&response=2", getActivity().getApplicationContext());
                getOperation.getStringData(new VolleyGETCallBack() {
                    @Override
                    public void onSuccess(String result) {

                        System.out.println("Success !! Request Accepted");
                        //Navigate to Requests List page
                        Toast.makeText(getActivity().getApplicationContext(), "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), HomePageActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onSuccess(JSONArray result) {



                    }
                });


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          Toast.makeText(getActivity().getApplicationContext(), "Going back to Home Page", Toast.LENGTH_SHORT).show();
                                          Intent intent = new Intent(getActivity().getApplicationContext(), HomePageActivity.class);
                                          startActivity(intent);
                                      }
                                  }
        );


        return view;


    }
}