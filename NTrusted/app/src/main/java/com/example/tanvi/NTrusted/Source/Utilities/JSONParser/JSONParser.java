package com.example.tanvi.NTrusted.Source.Utilities.JSONParser;

import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Models.Request;
import com.example.tanvi.NTrusted.Source.Models.Transaction;
import com.example.tanvi.NTrusted.Source.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

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

        /*TODO*/
        adv.setImageURL((String) advObj.get("ImageURL"));
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

        HashMap<Float,Float> rankMapping= new HashMap<Float,Float>();

        rankMapping.put(-2f,1f);
        rankMapping.put(-1.9f,1.1f);
        rankMapping.put(-1.8f,1.2f);
        rankMapping.put(-1.7f,1.3f);
        rankMapping.put(-1.6f,1.4f);
        rankMapping.put(-1.5f,1.5f);
        rankMapping.put(-1.4f,1.6f);
        rankMapping.put(-1.3f,1.7f);
        rankMapping.put(-1.2f,1.8f);
        rankMapping.put(-1.1f,1.9f);
        rankMapping.put(-1.0f,2.0f);
        rankMapping.put(-0.9f,2.1f);
        rankMapping.put(-0.8f,2.2f);
        rankMapping.put(-0.7f,2.3f);
        rankMapping.put(-0.6f,2.4f);
        rankMapping.put(-0.5f,2.5f);
        rankMapping.put(-0.4f,2.6f);
        rankMapping.put(-0.3f,2.7f);
        rankMapping.put(-0.2f,2.8f);
        rankMapping.put(-0.1f,2.9f);
        rankMapping.put(0.0f,3.0f);
        rankMapping.put(0.1f,3.1f);
        rankMapping.put(0.2f,3.2f);
        rankMapping.put(0.3f,3.3f);
        rankMapping.put(0.4f,3.4f);
        rankMapping.put(0.5f,3.5f);
        rankMapping.put(0.6f,3.6f);
        rankMapping.put(0.7f,3.7f);
        rankMapping.put(0.8f,3.8f);
        rankMapping.put(0.9f,3.9f);
        rankMapping.put(1.0f,4.0f);
        rankMapping.put(1.1f,4.1f);
        rankMapping.put(1.2f,4.2f);
        rankMapping.put(1.3f,4.3f);
        rankMapping.put(1.4f,4.4f);
        rankMapping.put(1.5f,4.5f);
        rankMapping.put(1.6f,4.6f);
        rankMapping.put(1.7f,4.7f);
        rankMapping.put(1.8f,4.8f);
        rankMapping.put(1.9f,4.9f);
        rankMapping.put(2.0f,5.0f);


        Double rank = (Double) object.get("rank");
        DecimalFormat df = new DecimalFormat("#.#");
        float key = Float.valueOf(df.format(rank));//rank = Math.floor(rank);
        //float rank = Float.valueOf(String.valueOf(d));

        //rank = Math.floor(rank);

        if (rankMapping.containsKey(key))
            adv.setRank(rankMapping.get(key));
//        rank = Math.floor(rank);
//        if (rank == -2.0)
//            adv.setRank(1);
//        if (rank == -1.0)
//            adv.setRank(2);
//        if (rank == 0.0)
//            adv.setRank(3);
//        if (rank == 1.0)
//            adv.setRank(4);
//        if (rank == 2.0)
//            adv.setRank(5);



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

    public Request parseRequestJSONWithoutRank(JSONObject object) throws JSONException{

        request= new Request();
        request.setRequestId((Integer) object.get("requestId"));
        request.setRequestDate(String.valueOf(object.get("requestDate")));
        request.setResponse((Integer) object.get("response"));
        request.setRequestType((Integer) object.get("requestType"));

        //set adv in req
        Advertisement ad = new Advertisement();
        ad.setAdId(String.valueOf(object.getJSONObject("advertisement").get("adId")));
        ad.setProductName((String) object.getJSONObject("advertisement").get("productName"));

        Category category = new Category();
        category.setCategoryName((String) object.getJSONObject("advertisement").getJSONObject("category").get("categoryName"));
        category.setCategoryID((Integer) object.getJSONObject("advertisement").getJSONObject("category").get("categoryId"));
        ad.setProductCategory(category);

        //set participants in req
        User receiver = new User();
        receiver.setId((String) object.getJSONObject("receiver").get("fbId"));
        receiver.setName((String) object.getJSONObject("receiver").get("name"));
        receiver.setAddress((String) object.getJSONObject("receiver").get("address"));
        receiver.setPhone((String) object.getJSONObject("receiver").get("phoneNumber"));
        receiver.setEmail((String) object.getJSONObject("receiver").get("email"));

        User sender = new User();
        sender.setId((String) object.getJSONObject("sender").get("fbId"));
        sender.setName((String) object.getJSONObject("sender").get("name"));
        sender.setAddress((String) object.getJSONObject("sender").get("address"));
        sender.setPhone((String) object.getJSONObject("sender").get("phoneNumber"));
        sender.setEmail((String) object.getJSONObject("sender").get("email"));

        request.setReceiver(receiver);
        request.setSender(sender);
        request.setAd(ad);

        return request;



    }


    public Request parseRequestJSONWithRank(JSONObject object) throws JSONException {

        request= new Request();
        //get request object
        JSONObject reqObject = (JSONObject) object.get("request");

        HashMap<Float,Float> rankMapping= new HashMap<Float,Float>();

        rankMapping.put(-2f,1f);
        rankMapping.put(-1.9f,1.1f);
        rankMapping.put(-1.8f,1.2f);
        rankMapping.put(-1.7f,1.3f);
        rankMapping.put(-1.6f,1.4f);
        rankMapping.put(-1.5f,1.5f);
        rankMapping.put(-1.4f,1.6f);
        rankMapping.put(-1.3f,1.7f);
        rankMapping.put(-1.2f,1.8f);
        rankMapping.put(-1.1f,1.9f);
        rankMapping.put(-1.0f,2.0f);
        rankMapping.put(-0.9f,2.1f);
        rankMapping.put(-0.8f,2.2f);
        rankMapping.put(-0.7f,2.3f);
        rankMapping.put(-0.6f,2.4f);
        rankMapping.put(-0.5f,2.5f);
        rankMapping.put(-0.4f,2.6f);
        rankMapping.put(-0.3f,2.7f);
        rankMapping.put(-0.2f,2.8f);
        rankMapping.put(-0.1f,2.9f);
        rankMapping.put(0.0f,3.0f);
        rankMapping.put(0.1f,3.1f);
        rankMapping.put(0.2f,3.2f);
        rankMapping.put(0.3f,3.3f);
        rankMapping.put(0.4f,3.4f);
        rankMapping.put(0.5f,3.5f);
        rankMapping.put(0.6f,3.6f);
        rankMapping.put(0.7f,3.7f);
        rankMapping.put(0.8f,3.8f);
        rankMapping.put(0.9f,3.9f);
        rankMapping.put(1.0f,4.0f);
        rankMapping.put(1.1f,4.1f);
        rankMapping.put(1.2f,4.2f);
        rankMapping.put(1.3f,4.3f);
        rankMapping.put(1.4f,4.4f);
        rankMapping.put(1.5f,4.5f);
        rankMapping.put(1.6f,4.6f);
        rankMapping.put(1.7f,4.7f);
        rankMapping.put(1.8f,4.8f);
        rankMapping.put(1.9f,4.9f);
        rankMapping.put(2.0f,5.0f);

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
        Double rank = (Double) object.get("rank");
        DecimalFormat df = new DecimalFormat("#.#");
        float key = Float.valueOf(df.format(rank));//rank = Math.floor(rank);


        if (rankMapping.containsKey(key))
            request.setRank(rankMapping.get(key));


        System.out.println("Rank is "+request.getRank());
        return request;

    }


    public Transaction parseTransactionJSON(JSONObject object) throws JSONException {

        transaction = new Transaction();

        System.out.println("In here !!!");

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
