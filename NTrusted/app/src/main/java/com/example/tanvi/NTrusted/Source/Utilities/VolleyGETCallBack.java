package com.example.tanvi.NTrusted.Source.Utilities;

import org.json.JSONArray;

/**
 * Created by tanvi on 4/22/2017.
 */

public interface VolleyGETCallBack {

    void onSuccess(String result);

    void onSuccess(JSONArray result);

}
