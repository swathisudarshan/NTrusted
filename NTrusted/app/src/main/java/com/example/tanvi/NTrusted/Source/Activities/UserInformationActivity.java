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
import com.example.tanvi.NTrusted.Source.Utilities.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.VolleyPOSTCallBack;

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

        phoneText = (EditText) findViewById(R.id.phoneNo);
        addressText = (EditText) findViewById(R.id.Address);

        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setPhone(String.valueOf(phoneText.getText()));
                user.setAddress(String.valueOf(addressText.getText()));

                try {
                    signUpUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        user = (User) bundle.getSerializable("User");

        System.out.println("In User Information Activity: ID : "+user.getId()+ " Name : "+user.getName()+ " Email : "+user.getEmail());

    }

    private void signUpUser() throws JSONException {

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("id",user.getId());
        params.put("email",user.getEmail());
        params.put("name",user.getName());
        params.put("phone",user.getPhone());
        params.put("address",user.getAddress());

        System.out.println("In Sign Up User: ID = "+params.get("id")+" Email = "+params.get("email")+" Name : "+params.get("name")+" Phone : "+params.get("phone"));
        /*TODO add phone number and address to params*/

        postOperation = new POSTOperation(Constants.signUpUser, params, getApplicationContext());
        postOperation.postData(new VolleyPOSTCallBack() {
            @Override
            public void onSuccess(Object result) {

                Intent intent = new Intent(UserInformationActivity.this,HomePageActivity.class);
                startActivity(intent);

            }
        });
    }


}
