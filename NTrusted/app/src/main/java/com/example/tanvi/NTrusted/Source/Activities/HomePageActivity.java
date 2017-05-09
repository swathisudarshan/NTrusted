package com.example.tanvi.NTrusted.Source.Activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Transaction;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Advertisement.BorrowingAdDetailFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Advertisement.LendingAdDetailFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Transaction.MyAlertDialogFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Request.MyReqTabFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Transaction.MyTransactionsFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.MISC.PostAdvFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.MISC.SearchFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.MISC.TabFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.Transaction.TransactionDetailFragment;
import com.example.tanvi.NTrusted.Source.Utilities.JSONParser.JSONParser;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONObject;


public class HomePageActivity extends AppCompatActivity implements TransactionDetailFragment.OnFragmentInteractionListener, LendingAdDetailFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener,PostAdvFragment.OnFragmentInteractionListener, BorrowingAdDetailFragment.OnFragmentInteractionListener{

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    GETOperation getOperation;

    Transaction transaction;

    JSONParser jsonParser;

    private String userId;

    MenuItem userName;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This is the main activity home page layout
        setContentView(R.layout.activity_home_page);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        this.userId = pref.getString(Constants.UserID,null);
        this.user = pref.getString(Constants.UserName,null);

        checkTransactionClosedReq();



        //Setup the DrawerLayout and NavigationView
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
        Menu menu =mNavigationView.getMenu();
        userName=menu.getItem(0);
        userName.setTitle(user);

        /* Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        //This is the main Container View in the activity layout
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();


         //Setup click events on the Navigation View Items.
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                //If home item selected , then replace the container view with TabFragment.
                //This Fragment has three tabs in it.
                if (menuItem.getItemId() == R.id.nav_item_home) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                    xfragmentTransaction.addToBackStack(null);
                }

                //MyRequest Fragment
                if (menuItem.getItemId() == R.id.nav_item_myreq) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new MyReqTabFragment()).commit();
                    xfragmentTransaction.addToBackStack(null);
                }

                //MyTransaction Fragment
                if (menuItem.getItemId() == R.id.nav_item_transac) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new MyTransactionsFragment()).commit();
                    xfragmentTransaction.addToBackStack(null);
                }

                // If post ad selected the  redirect to Post Adv Fragment
                if (menuItem.getItemId() == R.id.nav_item_post) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new PostAdvFragment()).commit();
                    fragmentTransaction.addToBackStack(null);

                }

                //If search selected, then redirect to search by category Fragment
                if (menuItem.getItemId() == R.id.nav_item_search) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new SearchFragment()).commit();
                    xfragmentTransaction.addToBackStack(null);
                }

                //If clicked on logout
                if (menuItem.getItemId() == R.id.nav_item_logout) {

                    //Facebook's loginManger.logout
                    LoginManager.getInstance().logOut();

                    //Redirect to the sign in page
                    Intent intent = new Intent(HomePageActivity.this,MainActivity.class);
                    startActivity(intent);
                }

                 return false;
            }

        });


        /**
         * Setup Drawer Toggle of the Toolbar
         */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();




    }

    private void checkTransactionClosedReq() {

System.out.println("In check transaction close requests");
        getOperation = new GETOperation(Constants.getIncomingRenteeCloseReq+"?userId="+userId, getApplicationContext());
        getOperation.getData(new VolleyGETCallBack() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onSuccess(JSONArray result) {
                jsonParser = new JSONParser();

                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                MyAlertDialogFragment editNameDialogFragment;

                    for (int i = 0; i < result.length(); i++) {

                        try {

                            System.out.println("Advertisement in success is ------>" + result.getJSONObject(i).toString());
                            JSONObject object = result.getJSONObject(i);
                            transaction= jsonParser.parseTransactionJSON(object);
                            editNameDialogFragment = MyAlertDialogFragment.newInstance("Rate your transaction",transaction);
                            editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                            editNameDialogFragment.show(fm, "fragment_edit_name");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }



                }

        });



        //getrenter close req
        getOperation = new GETOperation(Constants.getIncomingRenterCloseReq+"?userId="+userId, getApplicationContext());
        getOperation.getData(new VolleyGETCallBack() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onSuccess(JSONArray result) {
                jsonParser = new JSONParser();

                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                MyAlertDialogFragment editNameDialogFragment;

                for (int i = 0; i < result.length(); i++) {

                    try {

                        System.out.println("Advertisement in success is ------>" + result.getJSONObject(i).toString());
                        JSONObject object = result.getJSONObject(i);
                        transaction= jsonParser.parseTransactionJSON(object);
                        editNameDialogFragment = MyAlertDialogFragment.newInstance("Rate your transaction",transaction);
                        editNameDialogFragment.setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
                        editNameDialogFragment.show(fm, "fragment_edit_name");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }



            }

        });


    }


    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
