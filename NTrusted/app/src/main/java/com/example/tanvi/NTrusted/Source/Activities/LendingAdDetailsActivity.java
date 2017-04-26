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
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Utilities.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.VolleyPOSTCallBack;

import java.util.HashMap;
//Input from Extra Bundle :
//item_Category
//ad posted by
//**Can add Product Description in future


//This ad is Lending ad...So when a request is sent  from this screen it will be for getting that product on rent for current user
//current user is (Reuqest Sender):Rentee
//User to whom request is sent or who has posted this ad is Renter
//Call request/addBorrowRequest(AdId,SenderId)

public class LendingAdDetailsActivity extends AppCompatActivity {


    private Button req;
    private Button cancel;
    private TextView item_category;
    private TextView ad_poster;


    private POSTOperation postOperation;


    private Advertisement advertisement;
    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending_ad_details);

        req = (Button) findViewById(R.id.sendLendRequest);
        item_category = (TextView) findViewById(R.id.item_category);
        ad_poster = (TextView) findViewById(R.id.ad_poster);
        cancel = (Button) findViewById(R.id.cancel);

        //Set item_category and ad_poster to details obtained from PutExtra to bundle from previous activity
        item_category.setText("Camera");
        ad_poster.setText("Me");

        req.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HashMap<String,String> parameters = new HashMap<String,String>();
                parameters.put("adId",advertisement.getAdId()); //Replace it by actual adId obtained from Extra bundle
                parameters.put("senderId",pref.getString(Constants.UserID,null));


                System.out.println("Parameters: adId: "+parameters.get("adId")+" senderId:  "+parameters.get("senderId"));

                postOperation = new POSTOperation(Constants.sendBorrowRequest, parameters, getApplicationContext());
                postOperation.postData(new VolleyPOSTCallBack() {
                    @Override
                    public void onSuccess(Object result) {

                        System.out.println("Success !! Borrowing Request Added");
                        //Navigate to Requests List page
                        Toast.makeText(LendingAdDetailsActivity.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LendingAdDetailsActivity.this,UserHomepageActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Toast.makeText(LendingAdDetailsActivity.this, "Going back to Home Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LendingAdDetailsActivity.this,UserHomepageActivity.class);
                startActivity(intent);
            }
        }
        );
    }
}
