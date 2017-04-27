package com.example.tanvi.NTrusted.Source.Utilities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;

import java.util.List;

/**
 * Created by tanvi on 4/26/2017.
 */

public class AdverAdapter extends BaseAdapter {

    private Context context;
    List<Advertisement> advertisementList;

    public AdverAdapter(Context context, List<Advertisement> advertisementList) {
        this.context = context;
        this.advertisementList = advertisementList;
    }

    private class ViewHolder{

        TextView productName;
        TextView userName;
        TextView category;
        TextView datePosted;
        TextView adType;


    }


    @Override
    public int getCount() {
        return advertisementList.size();
    }

    @Override
    public Object getItem(int i) {

        return advertisementList.get(i);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder =null;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if(view == null){

            view = layoutInflater.inflate(R.layout.list_row,null);

            holder = new ViewHolder();
            holder.productName = (TextView) view.findViewById(R.id.productName);
            holder.adType = (TextView) view.findViewById(R.id.adType);
            holder.category = (TextView) view.findViewById(R.id.category);
            holder.datePosted = (TextView) view.findViewById(R.id.dateTime);
            holder.userName = (TextView) view.findViewById(R.id.userName);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        Advertisement advertisement = (Advertisement) getItem(i);
        System.out.println("Advertisment is--------------->"+advertisement.getProductName());
        holder.productName.setText(advertisement.getProductName());
        holder.userName.setText(advertisement.getAdPostedby().getName());
        int ad = advertisement.getAdType();
        if(ad == 1)
        holder.adType.setText("lend");
        else if(ad==2)
            holder.adType.setText("borrow");
        holder.datePosted.setText(advertisement.getPostDate().toString());
        holder.category.setText(advertisement.getProductCategory().getCategoryName());

        return view;
    }
}
