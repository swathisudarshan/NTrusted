package com.example.tanvi.NTrusted.Source.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Utilities.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.VolleyPOSTCallBack;

import java.util.HashMap;


//Input from Extra Bundle :
//item_Category
//ad posted by
//**Can add Product Description in future


//This ad is Borrowing ad...
//So when a request is sent  from this screen it will be for lending product from current user to the user who posted ad
//current user is (Reuqest Sender):Renter
//User to whom request is sent needs that item or who has posted this ad is -->Rentee
//Call request/addLendingRequest(AdId,SenderId)

public class BorrowingAdDetailsActivity extends AppCompatActivity {

    private Button req;
    private Button cancel;
    private TextView item_category;
    private TextView ad_poster;

    private POSTOperation postOperation;

    private String senderId;
    private String adId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowing_ad_details);

        req = (Button) findViewById(R.id.sendLendRequest);
        item_category = (TextView) findViewById(R.id.item_category);
        ad_poster = (TextView) findViewById(R.id.ad_poster);
        cancel = (Button) findViewById(R.id.cancel);

        Bundle bundle = this.getIntent().getExtras();
        adId = bundle.getString("adId");
        int catId = bundle.getInt("catId");
        String postOwner = bundle.getString("Postedby");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        senderId = pref.getString(Constants.UserID,null);

        System.out.println("******* Borrow Ad Details Parameters: adId: "+adId+" senderId:  "+ pref.getString(Constants.UserID,null));
        //Set item_category and ad_poster to details obtained from PutExtra to bundle from previous activity
        item_category.setText(String.valueOf(catId));
        ad_poster.setText(postOwner);

        req.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HashMap<String,String> parameters = new HashMap<String,String>();
                parameters.put("adId",adId); //Replace it by actual adId obtained from Extra bundle
                parameters.put("senderId",senderId);


                System.out.println("Parameters: adId: "+parameters.get("adId")+" senderId:  "+parameters.get("senderId"));

                postOperation = new POSTOperation(Constants.sendLendingRequest, parameters, getApplicationContext());
                postOperation.postData(new VolleyPOSTCallBack() {
                    @Override
                    public void onSuccess(Object result) {

                        System.out.println("Success !! Lending Request Sent");
                        //Navigate to Requests List page
                        Toast.makeText(BorrowingAdDetailsActivity.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BorrowingAdDetailsActivity.this,UserHomepageActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
                                  {
                                      public void onClick(View v) {
                                          Toast.makeText(BorrowingAdDetailsActivity.this, "Going back to Home Page", Toast.LENGTH_SHORT).show();
                                          Intent intent = new Intent(BorrowingAdDetailsActivity.this,UserHomepageActivity.class);
                                          startActivity(intent);
                                      }
                                  }
        );

    }
}
