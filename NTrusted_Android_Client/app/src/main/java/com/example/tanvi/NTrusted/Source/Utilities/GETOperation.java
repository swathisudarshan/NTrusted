package com.example.tanvi.NTrusted.Source.Utilities;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanvi on 4/22/2017.
 */


/*Class for HTTP GET call to the server*/
public class GETOperation {


    private String url;
    private Context context;


    public GETOperation(String url, Context context) {
        this.url = url;
        this.context = context;
    }


    public void getData(final VolleyGetCallBack volleyGetCallBack){

        RequestQueue queue = Volley.newRequestQueue(context);


        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url ,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                System.out.println("Response in get operation :" +response.toString());

                volleyGetCallBack.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("ERROR IN GET REQUEST : "+error.toString());

            }
        });

        queue.add(getRequest);

    }
}
