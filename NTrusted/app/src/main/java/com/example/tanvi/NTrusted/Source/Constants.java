package com.example.tanvi.NTrusted.Source;

/**
 * Created by tanvi on 4/24/2017.
 */
public interface Constants {

    //Server IP Address
    //public static final String serverHost = "10.0.0.58";
    public static final String serverHost = "10.250.99.119";
    //public static final String serverHost = "10.250.99.119";

    //APIs
    public static final String getAllCategories = "http://"+ Constants.serverHost+":8080/category/fetchAllCategory";
    public static final String postAdvertisement = "http://"+serverHost+":8080/advertisement/addLendingProduct";
    public static final String signUpUser ="http://"+serverHost+":8080/user/signup";
    public static final String getUserByEmail ="http://"+serverHost+":8080/user/get-by-email?email=";
    public static final String getRentees="http://"+serverHost+":8080/search/fetchRentees";
    public static final String getRenters="http://"+serverHost+":8080/search/fetchRenters";
    public static final String getAllAds = "http://"+serverHost+":8080/advertisement/getAllAds";
    public static final String getAllBorrowAds = "http://"+serverHost+":8080/advertisement/getAllBorrowAds";
    public static final String getAllLendingAds = "http://"+serverHost+":8080/advertisement/getAllLendingAds";

    public static final String sendBorrowRequest = "http://"+serverHost+":8080/request/addBorrowRequest";
    public static final String sendLendingRequest = "http://"+serverHost+":8080/request/addLendingRequest";

    public static final String getBorrowRequestsforCat = "http://"+serverHost+":8080/request/getBorrowRequestsforCat";

    public static final String acceptDeclineRequest = "http://"+serverHost+":8080/request/AcceptDeclineRequest";

    //Shared Preferences
    public static final String UserID = "UserID";

}
