package com.example.tanvi.NTrusted.Source.Activities;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.ULocale;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Utilities.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.VolleyCallback;
import com.example.tanvi.NTrusted.Source.Utilities.VolleyGetCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PostAdvActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private Spinner categorySpinner;
    private EditText productName, productDesc, productPrice;
    private List<Category> categories = new ArrayList<Category>();

    private ArrayAdapter<Category> adapter;

    private Button postAdv;

    private GETOperation getOperation;

    private POSTOperation postOperation;

    private Advertisement advertisement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_adv);

        categorySpinner = (Spinner) findViewById(R.id.spinner);
        productName = (EditText) findViewById(R.id.productName);
        productDesc = (EditText) findViewById(R.id.productDesc);
        productPrice = (EditText) findViewById(R.id.productPrice);
        postAdv = (Button) findViewById(R.id.send);

        postAdv.setOnClickListener(this);
        categorySpinner.setOnItemSelectedListener(this);
        getCategories();


    }

    private void getCategories() {

        System.out.println("In get categories");
        getOperation = new GETOperation("http://10.250.99.119:8080/category/fetchAllCategory", getApplicationContext());
        getOperation.getData(new VolleyGetCallBack(){
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


                adapter = new ArrayAdapter<Category>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,categories);

                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

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
        advertisement.setStatus("Active");
        Category category = (Category) categorySpinner.getSelectedItem();
        advertisement.setProductCategory(category.getCategoryID());
        advertisement.setPostDate(DateFormat.getDateTimeInstance().format(new Date()));

        makePost();
    }

    private void makePost() {

        HashMap<String,String> parameters = new HashMap<String,String>();

        parameters.put("productName",advertisement.getProductName());
        parameters.put("productDescription",advertisement.getProductDesc());
        parameters.put("productPrice",advertisement.getProductPrice());
        parameters.put("postDate","2017-04-04");
        parameters.put("categoryId",String.valueOf(advertisement.getProductCategory()));
        parameters.put("active", Boolean.toString(true));
        parameters.put("userId","1310902888949199");


        System.out.println("Parameters: "+parameters.get("productName")+" "+parameters.get(productDesc)+" "+parameters.get("productPrice"));



        postOperation = new POSTOperation("http://10.250.99.119:8080/advertisement/addProduct", parameters, getApplicationContext());
        postOperation.postData(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {

             System.out.println("Success !! Ad posted");

            }
        });


    }
}
