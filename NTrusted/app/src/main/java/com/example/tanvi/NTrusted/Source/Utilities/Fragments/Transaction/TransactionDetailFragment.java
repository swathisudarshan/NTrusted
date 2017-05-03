package com.example.tanvi.NTrusted.Source.Utilities.Fragments.Transaction;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionDetailFragment extends Fragment {

    TextView product,tid,with,date;
    Button close;
    Transaction transaction;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TransactionDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionDetailFragment newInstance(String param1, String param2) {
        TransactionDetailFragment fragment = new TransactionDetailFragment();
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
        View view= inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        String userId = pref.getString(Constants.UserID, null);
        Bundle args = getArguments();
        transaction = new Transaction();

        product= (TextView) view.findViewById(R.id.product);
        tid= (TextView) view.findViewById(R.id.tid);
        with= (TextView) view.findViewById(R.id.with);
        date = (TextView) view.findViewById(R.id.date);
        close= (Button) view.findViewById(R.id.close);

        product.setText(transaction.getAd().getProductName());
        tid.setText(String.valueOf(transaction.getTransactionId()));

        if(userId.equals(transaction.getRentee().getName()))
            with.setText(transaction.getRenter().getName());
        else
            with.setText(transaction.getRentee().getName());

        date.setText(String.valueOf(transaction.getStartDate()));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                MyAlertDialogFragment editNameDialogFragment;
                editNameDialogFragment = MyAlertDialogFragment.newInstance("Rate your transaction",transaction);
                editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                editNameDialogFragment.show(fm, "fragment_edit_name");

            }
        });

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
