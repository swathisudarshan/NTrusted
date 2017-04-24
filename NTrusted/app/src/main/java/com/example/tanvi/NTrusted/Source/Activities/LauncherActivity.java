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


        if(isLoggedIn()){

             intent= new Intent(LauncherActivity.this,UserHomepageActivity.class);

        }else{

            intent = new Intent(LauncherActivity.this,MainActivity.class);
        }

        startActivity(intent);


    }

    public boolean isLoggedIn(){

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken!=null;
    }
}
