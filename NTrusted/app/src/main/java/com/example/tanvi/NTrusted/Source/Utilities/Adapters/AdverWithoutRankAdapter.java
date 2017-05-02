package com.example.tanvi.NTrusted.Source.Utilities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Request;

import java.util.List;


public class AdverWithoutRankAdapter extends BaseAdapter {



        private Context context;
        List<Advertisement> advertisementList;


        public AdverWithoutRankAdapter(Context context, List<Advertisement> advertisementList) {
            this.context = context;
            this.advertisementList = advertisementList;
        }


        private class ViewHolderWithoutRank{


            TextView userName;
            TextView category;
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

             ViewHolderWithoutRank holder =null;

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


            if(view == null){

                view = layoutInflater.inflate(R.layout.listrowwithoutrank,null);

                holder = new ViewHolderWithoutRank();
                holder.adType = (TextView) view.findViewById(R.id.adType);
                holder.category = (TextView) view.findViewById(R.id.category);
                holder.userName = (TextView) view.findViewById(R.id.userName);


                view.setTag(holder);
            }
            else{
                holder = (ViewHolderWithoutRank) view.getTag();
            }

            Advertisement advertisement = (Advertisement) getItem(i);
            System.out.println("Advertisment is--------------->"+advertisement.getProductName());

            holder.userName.setText(advertisement.getAdPostedby().getName());
            holder.category.setText(advertisement.getProductCategory().getCategoryName());





            int ad = advertisement.getAdType();
            if(ad == 1)
                holder.adType.setText("lend");
            else if(ad==2)
                holder.adType.setText("borrow");



            return view;
        }
    }





