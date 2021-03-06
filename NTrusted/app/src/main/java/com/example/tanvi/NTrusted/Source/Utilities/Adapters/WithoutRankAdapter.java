package com.example.tanvi.NTrusted.Source.Utilities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Request;
import com.example.tanvi.NTrusted.Source.Models.Transaction;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleySingleton;

import java.util.List;


public class WithoutRankAdapter extends BaseAdapter {

        private Context context;
        List<Advertisement> advertisementList;
        List<Transaction> transactionList;
        List<Request> requestList;
        boolean isTransaction = false;
        boolean isRequest=false;


        public WithoutRankAdapter(Context context, List<Advertisement> advertisementList) {
            this.context = context;
            this.advertisementList = advertisementList;
            this.isTransaction = false;
            this.isRequest=false;

        }

        public WithoutRankAdapter(Context context, List<Transaction> transactionList, int transaction) {
        this.context = context;
        this.transactionList = transactionList;
            this.isTransaction = true;
            this.isRequest=false;
        }

    public WithoutRankAdapter(Context context, List<Request> requestList, boolean  isRequest) {
        this.context = context;
        this.requestList = requestList;
        this.isRequest = isRequest;
        this.isTransaction=false;
    }




    private class ViewHolderWithoutRank{

            TextView userName;
            TextView category;
            TextView adType;
            TextView postedAdFor;
        NetworkImageView productImage;
      }


        @Override
        public int getCount() {

            if(isTransaction)
                return transactionList.size();
            if(isRequest)
                return requestList.size();

            return advertisementList.size();
        }

        @Override
        public Object getItem(int i) {

            if(isTransaction)
                    return transactionList.get(i);
            if(isRequest)
                return requestList.get(i);

            else
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

                holder.productImage= (NetworkImageView) view.findViewById(R.id.productImage);
                view.setTag(holder);
            }
            else{
                holder = (ViewHolderWithoutRank) view.getTag();
            }

            if(isTransaction){

                System.out.println("In here isn trans");

                Transaction transaction = (Transaction) getItem(i);

                holder.userName.setText(transaction.getRentee().getName());
                if(holder.adType!=null)
                {
                    if(transaction.getAd().getAdType()==1)
                        holder.adType.setText("Lend");
                    if(transaction.getAd().getAdType()==2)
                        holder.adType.setText("Borrow");

                }

                holder.category.setText(transaction.getAd().getProductName());
                holder.postedAdFor.setVisibility(View.INVISIBLE);
                ImageLoader mImageLoader = VolleySingleton.getInstance(context).getImageLoader();
                holder.productImage.setImageUrl(transaction.getAd().getImageURL(),mImageLoader);



            }

            else if(isRequest){

                Request request = (Request) getItem(i);

                holder.userName.setText(request.getReceiver().getName());
                if(holder.adType!=null)
                {
                    if(request.getAd().getAdType()==1)
                        holder.adType.setText("Lend");
                    if(request.getAd().getAdType()==2)
                        holder.adType.setText("Borrow");

                }

                holder.category.setText(request.getAd().getProductName());
                holder.postedAdFor.setVisibility(View.INVISIBLE);
                ImageLoader mImageLoader = VolleySingleton.getInstance(context).getImageLoader();
                holder.productImage.setImageUrl(request.getAd().getImageURL(),mImageLoader);





            }

            else {
                Advertisement advertisement = (Advertisement) getItem(i);
                System.out.println("Advertisment is--------------->" + advertisement.getProductName());

                holder.userName.setText(advertisement.getAdPostedby().getName());
                holder.category.setText(advertisement.getProductCategory().getCategoryName());

                int ad = advertisement.getAdType();
                if(ad == 1)
                    holder.adType.setText("lend");
                else if(ad==2)
                    holder.adType.setText("borrow");

                ImageLoader mImageLoader = VolleySingleton.getInstance(context).getImageLoader();
                holder.productImage.setImageUrl(advertisement.getImageURL(),mImageLoader);


            }

            return view;
        }
    }





