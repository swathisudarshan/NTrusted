package com.example.tanvi.NTrusted.Source.Utilities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Models.Category;

import java.util.List;

/**
 * Created by tanvi on 5/4/2017.
 */

public class CustomSpinnerAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<Category> categoryList;
    List<String> adTypeList;
    Category category;
    boolean isAdType=false;

    public CustomSpinnerAdapter(Context context,List<Category> categoryList){
        this.context = context;
        this.categoryList=categoryList;
        layoutInflater = LayoutInflater.from(context);

    }

    public CustomSpinnerAdapter(Context context,List<String> adTypeList,boolean isADType){

        this.context = context;
        this.adTypeList=adTypeList;
        layoutInflater = LayoutInflater.from(context);
        this.isAdType = isADType;
    }

    @Override
    public int getCount() {

        if(isAdType)
            return adTypeList.size();

        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        if(isAdType)
            return adTypeList.get(i);
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= layoutInflater.inflate(R.layout.custom_spinner_layout,null);
        TextView category = (TextView) view.findViewById(R.id.category);

        if(!isAdType)
        category.setText(categoryList.get(i).getCategoryName());

        else
        category.setText(adTypeList.get(i));

        return view;
    }
}
