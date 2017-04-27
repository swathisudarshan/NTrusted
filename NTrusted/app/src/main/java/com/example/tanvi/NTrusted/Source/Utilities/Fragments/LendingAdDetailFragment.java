package com.example.tanvi.NTrusted.Source.Utilities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyPOSTCallBack;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LendingAdDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LendingAdDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LendingAdDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView product,description,category,postedBy,date,price;

    private Button send,cancel;

    private String userId;

    private POSTOperation postOperation;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LendingAdDetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LendingAdDetailFragment newInstance(String param1, String param2) {
        LendingAdDetailFragment fragment = new LendingAdDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lending_ad_detail, container, false);


        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref",0);
        userId = pref.getString(Constants.UserID,null);

        product = (TextView) view.findViewById(R.id.product);
        description = (TextView) view.findViewById(R.id.desciption);
        postedBy = (TextView) view.findViewById(R.id.by);
        category = (TextView) view.findViewById(R.id.cat);
        date = (TextView) view.findViewById(R.id.date);
        price= (TextView) view.findViewById(R.id.price);
        send= (Button) view.findViewById(R.id.send);
        cancel= (Button) view.findViewById(R.id.cancel);

        Bundle args = getArguments();

        final Advertisement advertisement= (Advertisement) args.getSerializable("advertisement");

        product.setText(advertisement.getProductName());
        description.setText(advertisement.getProductDesc());
        postedBy.setText(advertisement.getAdPostedby().getName());
        category.setText(advertisement.getProductCategory().getCategoryName());
        date.setText(String.valueOf(advertisement.getPostDate()));
        price.setText(advertisement.getProductPrice());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> parameters = new HashMap<String,String>();
                parameters.put("adId",advertisement.getAdId()); //Replace it by actual adId obtained from Extra bundle
                parameters.put("senderId",userId);

                System.out.println("******* Lend Ad Details Parameters: adId: "+parameters.get("adId")+" senderId:  "+ userId);

                postOperation = new POSTOperation(Constants.sendBorrowRequest, parameters, getActivity().getApplicationContext());
                postOperation.postData(new VolleyPOSTCallBack() {
                    @Override
                    public void onSuccess(Object result) {

                        System.out.println("Success !! Lending Request Sent");
                        //Navigate to Requests List page
                        Toast.makeText(getActivity().getApplicationContext(), "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(),HomePageActivity.class);
                        startActivity(intent);

                    }
                });



            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
                                  {
                                      public void onClick(View v) {
                                          Toast.makeText(getActivity().getApplicationContext(), "Going back to Home Page", Toast.LENGTH_SHORT).show();
                                          Intent intent = new Intent(getActivity().getApplicationContext(),HomePageActivity.class);
                                          startActivity(intent);
                                      }
                                  }
        );



        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
