package com.example.tanvi.NTrusted.Source.Utilities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Request;

import java.util.List;

/**
 * Created by tanvi on 4/26/2017.
 */

public class WithRankAdapter extends BaseAdapter {

    static boolean isRequest=false;

    private Context context;
    List<Advertisement> advertisementList;
    List<Request> requestList;

    public WithRankAdapter() {

    }

    public WithRankAdapter(Context context, List<Advertisement> advertisementList) {
        this.context = context;
        this.advertisementList = advertisementList;
        this.isRequest=false;
    }

    public WithRankAdapter(Context context, List<Request> requestList, int req) {
        this.context = context;
        this.requestList= requestList;
        this.isRequest=true;
    }

    private class ViewHolder{

        TextView userName;
        TextView category;
        TextView adType;
        RatingBar ratingBar;
    }

    @Override
    public int getCount() {

        if(this.isRequest){

            return requestList.size();

        }
        return advertisementList.size();
    }

    @Override
    public Object getItem(int i) {

        if(isRequest)
            return requestList.get(i);

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
            holder.adType = (TextView) view.findViewById(R.id.adType);
            holder.category = (TextView) view.findViewById(R.id.category);
            holder.userName = (TextView) view.findViewById(R.id.userName);
            holder.ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        if(isRequest){

           Request request = (Request) getItem(i);

            holder.userName.setText(request.getSender().getName());
            holder.category.setText(request.getAd().getProductCategory().getCategoryName());
            System.out.println("Rank in adapter is ------>" + request.getRank());
            holder.ratingBar.setRating(request.getRank());



        }

        else {
            Advertisement advertisement = (Advertisement) getItem(i);
            System.out.println("Advertisment is--------------->" + advertisement.getProductName());

            holder.userName.setText(advertisement.getAdPostedby().getName());
            holder.category.setText(advertisement.getProductCategory().getCategoryName());
            System.out.println("Rank in adapter is ------>" + advertisement.getRank());
            holder.ratingBar.setRating(advertisement.getRank());


            int ad = advertisement.getAdType();
            if (ad == 1)
                holder.adType.setText("lend");
            else if (ad == 2)
                holder.adType.setText("borrow");

        }

        return view;
    }
}
