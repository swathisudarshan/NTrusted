package com.example.tanvi.NTrusted.Source.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Utilities.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.OneFragment;
import com.example.tanvi.NTrusted.Source.Utilities.ThreeFragment;
import com.example.tanvi.NTrusted.Source.Utilities.TwoFragment;
import com.example.tanvi.NTrusted.Source.Utilities.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class UserHomepageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GETOperation getOperation;

    private List<Category> categories = new ArrayList<Category>();

    private ArrayAdapter<Category> categoryArrayAdapter;

    private  Spinner categorySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_homepage);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor = pref.edit();
        System.out.println("In UHA, USER is "+pref.getString(Constants.UserID,null));

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setupViewPager(viewPager,0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    //Tab Layout
    private void setupViewPager(ViewPager viewPager, int categoryId) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(categoryId), "ALL");
        adapter.addFragment(new TwoFragment(categoryId,getApplicationContext()), "Borrow");
        adapter.addFragment(new ThreeFragment(categoryId), "Lend");
        viewPager.setAdapter(adapter);
    }

    //For Tab Layout
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    //App Bar Spinner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinnerActionBar);
       categorySpinner =(Spinner) MenuItemCompat.getActionView(item);

        getCategories();


       // spinner.setAdapter(adapter);
        return true;
    }


    //Get call to get categories in spinner data

    private void getCategories() {

        getOperation = new GETOperation(Constants.getAllCategories, getApplicationContext());
        getOperation.getData(new VolleyGETCallBack(){
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onSuccess(JSONArray result) {

                System.out.println("In volley call back !!!!!!!!!!!!!!!!!!"+result.toString());
                for(int i=0;i<result.length();i++) {


                    Category category = new Category();
                    try {
                        category.setCategoryID((Integer) result.getJSONObject(i).get("categoryId"));
                        category.setCategoryName((String) result.getJSONObject(i).get("categoryName"));
                        categories.add(category);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Category categoryFirst = new Category();
                categoryFirst.setCategoryID(0);
                categoryFirst.setCategoryName("Select Category");
                categories.add(0,categoryFirst);

                categoryArrayAdapter = new ArrayAdapter<Category>(getApplicationContext(), android.R.layout.simple_spinner_item,categories);
                categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                categorySpinner.setAdapter(categoryArrayAdapter);

              categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                      view.setBackgroundColor(Color.WHITE);

                     if(i>0)

                     {
                         Category item = (Category) adapterView.getItemAtPosition(i);
                         System.out.println("Item selected !!!!" + item.getCategoryName() + " ID " + item.getCategoryID());
                         setupViewPager(viewPager,item.getCategoryID());
                     }




                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> adapterView) {


                  }
              });


            }

        });

    }





}
