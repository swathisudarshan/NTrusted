package com.example.tanvi.NTrusted.Source.Utilities.JSONParser;

import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Models.Request;
import com.example.tanvi.NTrusted.Source.Models.Transaction;
import com.example.tanvi.NTrusted.Source.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by tanvi on 4/29/2017.
 */

public class JSONParser {

    private Advertisement adv;

    private Request request;

    private Transaction transaction;

    public Advertisement parseAdvJSONWithoutRank(JSONObject advObj) throws JSONException {

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


    public Advertisement parseAdvJSONWithRank(JSONObject object) throws JSONException {


        adv = new Advertisement();
        JSONObject advObj = (JSONObject) object.get("advertisement");
        JSONObject userObj = (JSONObject) advObj.get("user");
        JSONObject catObj = (JSONObject) advObj.get("category");

        double rank = (double) object.get("rank");
        rank = Math.floor(rank);
        System.out.println("Rank is : " + rank);

        if (rank == -2.0)
            adv.setRank(1);
        if (rank == -1.0)
            adv.setRank(2);
        if (rank == 0.0)
            adv.setRank(3);
        if (rank == 1.0)
            adv.setRank(4);
        if (rank == 2.0)
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

    public Request parseJSONRequest(JSONObject object) throws JSONException {

        request= new Request();
        //get request object
        JSONObject reqObject = (JSONObject) object.get("request");

        //set req fields
        request.setRequestId((Integer) reqObject.get("requestId"));
        request.setRequestDate(String.valueOf(reqObject.get("requestDate")));
        request.setResponse((Integer) reqObject.get("response"));
        request.setRequestType((Integer) reqObject.get("requestType"));

        //set adv in req
        Advertisement ad = new Advertisement();
        ad.setAdId(String.valueOf(reqObject.getJSONObject("advertisement").get("adId")));
        ad.setProductName((String) reqObject.getJSONObject("advertisement").get("productName"));

        Category category = new Category();
        category.setCategoryName((String) reqObject.getJSONObject("advertisement").getJSONObject("category").get("categoryName"));
        category.setCategoryID((Integer) reqObject.getJSONObject("advertisement").getJSONObject("category").get("categoryId"));
        ad.setProductCategory(category);

        //set participants in req
        User receiver = new User();
        receiver.setId((String) reqObject.getJSONObject("receiver").get("fbId"));
        receiver.setName((String) reqObject.getJSONObject("receiver").get("name"));
        receiver.setAddress((String) reqObject.getJSONObject("receiver").get("address"));
        receiver.setPhone((String) reqObject.getJSONObject("receiver").get("phoneNumber"));
        receiver.setEmail((String) reqObject.getJSONObject("receiver").get("email"));

        User sender = new User();
        sender.setId((String) reqObject.getJSONObject("sender").get("fbId"));
        sender.setName((String) reqObject.getJSONObject("sender").get("name"));
        sender.setAddress((String) reqObject.getJSONObject("sender").get("address"));
        sender.setPhone((String) reqObject.getJSONObject("sender").get("phoneNumber"));
        sender.setEmail((String) reqObject.getJSONObject("sender").get("email"));

        request.setReceiver(receiver);
        request.setSender(sender);
        request.setAd(ad);


        //set rank
        double rank = (double) object.get("rank");
        rank = Math.floor(rank);


        if (rank == -2.0)
            request.setRank(1);
        if (rank == -1.0)
            request.setRank(2);
        if (rank == 0.0)
            request.setRank(3);
        if (rank == 1.0)
            request.setRank(4);
        if (rank == 2.0)
            request.setRank(5);


        System.out.println("Rank is "+request.getRank());
        return request;

    }


    public Transaction parseTransactionJSON(JSONObject object) throws JSONException {

        transaction = new Transaction();

        transaction.setTransactionId((Integer) object.get("transactionId"));
        Date date = new Date();
        date.setTime((Long) object.get("startDate"));
        transaction.setStartDate(date);

        transaction.setEndDate(null);
        transaction.setRenterClose((Integer) object.get("renterClose"));
        transaction.setRenteeClose((Integer) object.get("renteeClose"));
        transaction.setStatus((Integer) object.get("status"));

        JSONObject advObj = (JSONObject) object.get("ad");
        JSONObject userObj = (JSONObject) advObj.get("user");
        JSONObject catObj = (JSONObject) advObj.get("category");
        JSONObject reqObj = (JSONObject) object.get("request");
        JSONObject renterObj = (JSONObject) object.get("renter");
        JSONObject renteeObj = (JSONObject) object.get("rentee");


        Advertisement adv = new Advertisement();
        //Set ad details
        adv.setAdId(String.valueOf(advObj.get("adId")));
        adv.setProductName(String.valueOf(advObj.get("productName")));
        adv.setProductDesc(String.valueOf(advObj.get("productDescription")));
        adv.setProductPrice(String.valueOf(advObj.get("productPrice")));
        adv.setAdType((Integer) advObj.get("adType"));
        date = new Date();
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

        transaction.setAd(adv);


        //set req details
        transaction.setRequest((Integer) reqObj.get("requestId"));

        User renter = new User();
        renter.setId((String) renterObj.get("fbId"));
        renter.setEmail((String) renterObj.get("email"));
        renter.setName((String) renterObj.get("name"));
        renter.setAddress((String) renterObj.get("address"));
        renter.setPhone((String) renteeObj.get("phoneNumber"));

        User rentee = new User();
        rentee.setId((String) renteeObj.get("fbId"));
        rentee.setName((String) renteeObj.get("name"));
        rentee.setEmail((String) renteeObj.get("email"));
        rentee.setAddress((String) renteeObj.get("address"));
        rentee.setPhone((String) renteeObj.get("phoneNumber"));

        transaction.setRenter(renter);
        transaction.setRentee(rentee);
        return transaction;


    }
}