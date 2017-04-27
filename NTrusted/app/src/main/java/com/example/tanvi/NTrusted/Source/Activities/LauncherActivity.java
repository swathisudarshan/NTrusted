package com.example.tanvi.NTrusted.Source.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tanvi.NTrusted.R;
import com.facebook.AccessToken;

public class LauncherActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Check if user is logged in, if yes then go to HomePage
        if(isLoggedIn()){

             intent= new Intent(LauncherActivity.this,HomePageActivity.class);

        }
        //Else go to MainActivity where user can sign in using Facebook
        else{

            intent = new Intent(LauncherActivity.this,MainActivity.class);
        }

        startActivity(intent);


    }

    //Method which checks using Facebook access token whether the user has signed in
    public boolean isLoggedIn(){

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken!=null;
    }
}
