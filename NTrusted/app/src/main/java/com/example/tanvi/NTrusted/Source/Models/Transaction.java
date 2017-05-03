package com.example.tanvi.NTrusted.Source.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tanvi on 5/2/2017.
 */

public class Transaction implements Serializable{

    private int transactionId;
    private Advertisement ad;
    private int request;
    private User renter;
    private User rentee;
    private float renterRating;
    private float renteeRating;
    private Date startDate;
    private Date endDate;
    private int renterClose;
    private int renteeClose;
    private int status;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Advertisement getAd() {
        return ad;
    }

    public void setAd(Advertisement ad) {
        this.ad = ad;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public User getRentee() {
        return rentee;
    }

    public void setRentee(User rentee) {
        this.rentee = rentee;
    }

    public float getRenterRating() {
        return renterRating;
    }

    public void setRenterRating(float renterRating) {
        this.renterRating = renterRating;
    }

    public float getRenteeRating() {
        return renteeRating;
    }

    public void setRenteeRating(float renteeRating) {
        this.renteeRating = renteeRating;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRenterClose() {
        return renterClose;
    }

    public void setRenterClose(int renterClose) {
        this.renterClose = renterClose;
    }

    public int getRenteeClose() {
        return renteeClose;
    }

    public void setRenteeClose(int renteeClose) {
        this.renteeClose = renteeClose;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }





}
