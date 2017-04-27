package com.example.tanvi.NTrusted.Source.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.User;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyPOSTCallBack;

import org.json.JSONException;

import java.util.HashMap;

public class UserInformationActivity extends AppCompatActivity {

    private User user;
    private POSTOperation postOperation;
    EditText phoneText, addressText;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        //Get user info from previous activity
        Bundle bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("User");

        //initialize layout
        phoneText = (EditText) findViewById(R.id.phoneNo);
        addressText = (EditText) findViewById(R.id.Address);
        send = (Button) findViewById(R.id.send);

        //send onclick action
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set other user fields in the user object
                user.setPhone(String.valueOf(phoneText.getText()));
                user.setAddress(String.valueOf(addressText.getText()));

                try {
                    //make a post call to signup user
                    signUpUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("In User Information Activity: ID : "+user.getId()+ " Name : "+user.getName()+ " Email : "+user.getEmail());

    }

    //This method makes a HTTP Post request to the server to add new user
    private void signUpUser() throws JSONException {

        //Add request parameters
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("id",user.getId());
        params.put("email",user.getEmail());
        params.put("name",user.getName());
        params.put("phone",user.getPhone());
        params.put("address",user.getAddress());

        System.out.println("In Sign Up User: ID = "+params.get("id")+" Email = "+params.get("email")+" Name : "+params.get("name")+" Phone : "+params.get("phone"));

        //Execute POST call
        postOperation = new POSTOperation(Constants.signUpUser, params, getApplicationContext());
        postOperation.postData(new VolleyPOSTCallBack() {
            @Override
            public void onSuccess(Object result) {
                //On success of the insert operation, redirect user to homepage
                Intent intent = new Intent(UserInformationActivity.this,HomePageActivity.class);
                startActivity(intent);

            }
        });
    }


}
