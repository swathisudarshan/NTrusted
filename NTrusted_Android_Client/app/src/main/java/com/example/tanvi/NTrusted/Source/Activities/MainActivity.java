package com.example.tanvi.NTrusted.Source.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Models.User;
import com.example.tanvi.NTrusted.Source.Utilities.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.VolleyCallback;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import static com.facebook.GraphRequest.newMeRequest;

public class MainActivity extends AppCompatActivity {


    CallbackManager callbackManager;
    LoginButton facebookSignIn;
    private String userID="";
    private String userName="";
    private String userEmail="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*Set Layout of the sign up page*/
        setContentView(R.layout.activity_signup);

        /*Initialize the sign in button*/
        facebookSignIn = (LoginButton) findViewById(R.id.login_button);

        /*Initialize Facebook SDK*/
        FacebookSdk.sdkInitialize(getApplicationContext());

        /*Create Facebook Callback Manager*/
        callbackManager = CallbackManager.Factory.create();

        /*Set email and user friends permissions while sign in*/
       facebookSignIn.setReadPermissions(Arrays.asList("user_friends","email"));

        /*Registers callback method for sign in*/
        facebookSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            /*After successful sign in*/
            @Override
            public void onSuccess(LoginResult loginResult) {

                /*Make a request to get email, friends and FB ID of the signed in user*/
                new GraphRequest(
                loginResult.getAccessToken(),
                "/me?fields=email,name",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        try {
                            System.out.println(response.getJSONObject());
                            userID = response.getJSONObject().getString("id");
                            userName = response.getJSONObject().getString("name");
                            userEmail = response.getJSONObject().getString("email");

                            System.out.println("In Main Activity : ID = "+userID+" Name = "+userName+" UserEmail = "+userEmail);

                            User user = new User();
                            user.setId(userID);
                            user.setName(userName);
                            user.setEmail(userEmail);

                            Intent intent = new Intent(MainActivity.this,UserInformationActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("User",user);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }



    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}