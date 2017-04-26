package com.example.tanvi.NTrusted.Source.Utilities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.tanvi.NTrusted.R;
public class ThreeFragment extends Fragment {

    String[] AndroidOS = new String[] { "Camera","Table","Camera","Books"};


    public ThreeFragment() {
        // Required empty public constructor
    }

    public ThreeFragment(int categoryId) {
        System.out.println("Category is : "+categoryId);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_one, container, false);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, AndroidOS);
        //setListAdapter(adapter);

        return view;
    }


}
