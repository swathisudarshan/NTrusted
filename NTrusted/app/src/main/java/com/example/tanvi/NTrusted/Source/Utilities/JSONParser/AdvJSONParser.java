package com.example.tanvi.NTrusted.Source.Utilities.JSONParser;

import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by tanvi on 4/29/2017.
 */

public class AdvJSONParser {

    private Advertisement adv;

    public Advertisement parseJSONWithoutRank(JSONObject advObj) throws JSONException {

        adv = new Advertisement();

        JSONObject userObj = (JSONObject) advObj.get("user");
        JSONObject catObj = (JSONObject) advObj.get("category");


        adv.setAdId(String.valueOf(advObj.get("adId")));
        adv.setProductName(String.valueOf(advObj.get("productName")));
        adv.setProductDesc(String.valueOf(advObj.get("productDescription")));
        adv.setProductPrice(String.valueOf(advObj.get("productPrice")));
        adv.setAdType((Integer) advObj.get("adType"));
        Date date = new Date();
        date.setTime((Long) advObj.get("postDate"));
        adv.setPostDate(date);
        adv.setStatus(String.valueOf(advObj.get("active")));


        User user = new User();
        user.setId((String) userObj.get("fbId"));
        user.setName((String) userObj.get("name"));
        user.setPhone((String) userObj.get("phoneNumber"));
        user.setAddress((String) userObj.get("address"));

        adv.setAdPostedby(user);

        Category category = new Category();
        category.setCategoryID(catObj.getInt("categoryId"));
        category.setCategoryName(catObj.getString("categoryName"));

        adv.setProductCategory(category);

        return adv;
    }


    public Advertisement parseJSONWithRank(JSONObject object) throws JSONException {


        adv = new Advertisement();
        JSONObject advObj = (JSONObject) object.get("advertisement");
        JSONObject userObj = (JSONObject) advObj.get("user");
        JSONObject catObj = (JSONObject) advObj.get("category");

        double rank = (double) object.get("rank");
        System.out.println("Rank is : " + rank);

        if (rank == -0.2)
            adv.setRank(1);
        if (rank == -0.1)
            adv.setRank(2);
        if (rank == 0.0)
            adv.setRank(3);
        if (rank == 0.1)
            adv.setRank(4);
        if (rank == 0.2)
            adv.setRank(5);


        //Set ad details
        adv.setAdId(String.valueOf(advObj.get("adId")));
        adv.setProductName(String.valueOf(advObj.get("productName")));
        adv.setProductDesc(String.valueOf(advObj.get("productDescription")));
        adv.setProductPrice(String.valueOf(advObj.get("productPrice")));
        adv.setAdType((Integer) advObj.get("adType"));
        Date date = new Date();
        date.setTime((Long) advObj.get("postDate"));
        adv.setPostDate(date);
        adv.setStatus(String.valueOf(advObj.get("active")));

        //set user details
        User user = new User();
        user.setId((String) userObj.get("fbId"));
        user.setName((String) userObj.get("name"));
        user.setPhone((String) userObj.get("phoneNumber"));
        user.setAddress((String) userObj.get("address"));
        adv.setAdPostedby(user);

        //set category details
        Category category = new Category();
        category.setCategoryID(catObj.getInt("categoryId"));
        category.setCategoryName(catObj.getString("categoryName"));
        adv.setProductCategory(category);

          return adv;


    }
}
