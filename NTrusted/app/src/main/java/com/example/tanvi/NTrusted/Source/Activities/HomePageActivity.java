package com.example.tanvi.NTrusted.Source.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.BorrowingAdDetailFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.LendingAdDetailFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.PostAdvFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.SearchFragment;
import com.example.tanvi.NTrusted.Source.Utilities.Fragments.TabFragment;
import com.facebook.login.LoginManager;


public class HomePageActivity extends AppCompatActivity implements LendingAdDetailFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener,PostAdvFragment.OnFragmentInteractionListener, BorrowingAdDetailFragment.OnFragmentInteractionListener{

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This is the main activity home page layout
        setContentView(R.layout.activity_home_page);


        //Setup the DrawerLayout and NavigationView
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

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
                }

                // If post ad selected the  redirect to Post Adv Fragment
                if (menuItem.getItemId() == R.id.nav_item_post) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new PostAdvFragment()).commit();

                }

                //If search selected, then redirect to search by category Fragment
                if (menuItem.getItemId() == R.id.nav_item_search) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new SearchFragment()).commit();
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
