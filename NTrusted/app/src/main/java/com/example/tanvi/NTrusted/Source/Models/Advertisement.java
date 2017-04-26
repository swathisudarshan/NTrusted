package com.example.tanvi.NTrusted.Source.Models;

/**
 * Created by tanvi on 4/22/2017.
 */

public class Advertisement {

    private String adId;
    private String productName;
    private int productCategory;
    private String productDesc;
    private String productPrice;
    private String postDate;
    private String status;
    private int adType;
    private String user;

    public String getAdId() {
        return this.adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdPostedby() {
        return user;
    }

    public void setAdPostedby(String adPostedby) {
        this.user = adPostedby;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }

    @Override
    public String toString(){return getProductName();}
}
