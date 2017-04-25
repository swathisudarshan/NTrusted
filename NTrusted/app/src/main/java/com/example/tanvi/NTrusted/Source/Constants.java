package com.example.tanvi.NTrusted.Source;

/**
 * Created by tanvi on 4/24/2017.
 */
public interface Constants {

    //Server IP Address
    //public static final String serverHost = "10.0.0.58";
    public static final String serverHost = "10.250.149.52";

    //APIs
    public static final String getAllCategories = "http://"+ Constants.serverHost+":8080/category/fetchAllCategory";
    public static final String postAdvertisement = "http://"+serverHost+":8080/advertisement/addLendingProduct";
    public static final String signUpUser ="http://"+serverHost+":8080/user/signup";
    public static final String getUserByEmail ="http://"+serverHost+":8080/user/get-by-email?email=";
    public static final String getRentees="http://"+serverHost+":8080/search/fetchRentees";

    //Shared Preferences
    public static final String UserID = "UserID";

}
