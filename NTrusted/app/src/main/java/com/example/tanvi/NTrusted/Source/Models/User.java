package com.example.tanvi.NTrusted.Source.Models;

import android.os.Parcelable;
import android.text.Editable;

import java.io.Serializable;

/**
 * Created by tanvi on 4/21/2017.
 */

public class User implements Serializable{

    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
