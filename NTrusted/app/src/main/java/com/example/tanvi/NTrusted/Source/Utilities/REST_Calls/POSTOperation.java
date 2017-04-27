package com.example.tanvi.NTrusted.Source.Utilities.REST_Calls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanvi on 4/21/2017.
 */

public class POSTOperation {

    private String url;
    private HashMap<String,String> parameters;
    private Context context;

    public POSTOperation(String url, HashMap<String, String> parameters, Context applicationContext){
        this.url = url;
        this.parameters =parameters;
        this.context = applicationContext;
    }

    public void postData(final VolleyPOSTCallBack volleyCallback){

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Response is " + response.toString());

                        volleyCallback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("ERROR IN POST CALL "+error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return parameters;
            }
        };

//        int socketTimeout = 30000;//30 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);

        queue.add(stringRequest);

    }


}
