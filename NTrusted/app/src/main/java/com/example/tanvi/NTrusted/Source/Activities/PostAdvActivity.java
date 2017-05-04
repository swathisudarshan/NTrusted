package com.example.tanvi.NTrusted.Source.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.CustomSpinnerAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyPOSTCallBack;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostAdvActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private Spinner categorySpinner;
    private EditText productName, productDesc, productPrice;
    private List<Category> categories = new ArrayList<Category>();

    private CustomSpinnerAdapter adapter;

    private Button postAdv;

    private GETOperation getOperation;

    private POSTOperation postOperation;

    private Advertisement advertisement;

    private RadioGroup radioGroup;

    private RadioButton radioBorrow, radioLend;

    private TextView productPriceTitle;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_adv);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        userId = pref.getString(Constants.UserID,null);

        categorySpinner = (Spinner) findViewById(R.id.spinner);
        productName = (EditText) findViewById(R.id.productName);
        productDesc = (EditText) findViewById(R.id.productDesc);
        productPrice = (EditText) findViewById(R.id.productPrice);
        postAdv = (Button) findViewById(R.id.send);
        radioGroup = (RadioGroup) findViewById(R.id.radioAdType);
        radioBorrow = (RadioButton) findViewById(R.id.radioBorrow);
        radioLend = (RadioButton) findViewById(R.id.radioLend);
        productPriceTitle = (TextView) findViewById(R.id.productPriceText);
        productPrice.setVisibility(View.INVISIBLE);
        productPriceTitle.setVisibility(View.INVISIBLE);

        radioLend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productPriceTitle.setVisibility(View.VISIBLE);
                productPrice.setVisibility(View.VISIBLE);

            }
        });

        radioBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productPriceTitle.setVisibility(View.INVISIBLE);
                productPrice.setVisibility(View.INVISIBLE);

            }
        });

        postAdv.setOnClickListener(this);
        categorySpinner.setOnItemSelectedListener(this);
        getCategories();


    }

    private void getCategories() {

        System.out.println("In get categories");
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


                adapter = new CustomSpinnerAdapter(getApplicationContext(),categories);
                categorySpinner.setAdapter(adapter);


                for(int i=0;i<categories.size();i++)
                    System.out.println(categories.get(i).getCategoryName());

        }

        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Category item = (Category) adapterView.getItemAtPosition(i);

        System.out.println("Item selected !!!!"+item.getCategoryName());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {

        advertisement = new Advertisement();

        advertisement.setProductName(String.valueOf(productName.getText()));
        advertisement.setProductDesc(String.valueOf(productDesc.getText()));
        advertisement.setProductPrice(String.valueOf(productPrice.getText()));
        advertisement.setStatus("1");
        Category category = (Category) categorySpinner.getSelectedItem();
        advertisement.setProductCategory(category);
        makePost();
    }

    private void makePost() {

        HashMap<String,String> parameters = new HashMap<String,String>();

        int radio= radioGroup.getCheckedRadioButtonId();
        System.out.println("Radio is -------------->"+radio);

        if(radioBorrow.getId()==radio)
            parameters.put("adType","2");
        if(radioLend.getId()==radio)
            parameters.put("adType","1");

        parameters.put("productName",advertisement.getProductName());
        parameters.put("productDescription",advertisement.getProductDesc());
        parameters.put("productPrice",advertisement.getProductPrice());
        parameters.put("categoryId",String.valueOf(advertisement.getProductCategory().getCategoryID()));
        parameters.put("active", "1");
        parameters.put("userId",userId);



        System.out.println("Parameters: Product Name: "+parameters.get("productName")+" Description:  "+parameters.get("productDescription")+" Price: "+parameters.get("productPrice"));
        System.out.println("Category :"+parameters.get("categoryId")+" User:"+parameters.get("userId")+" Active:" +parameters.get("active") +"Type:"+parameters.get("adType"));


        postOperation = new POSTOperation(Constants.postAdvertisement, parameters, getApplicationContext());
        postOperation.postData(new VolleyPOSTCallBack() {
            @Override
            public void onSuccess(Object result) {

             System.out.println("Success !! Ad posted");
                Toast.makeText(PostAdvActivity.this, "Ad Posted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PostAdvActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });


    }
}
