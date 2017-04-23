package com.example.tanvi.NTrusted.Source.Models;

/**
 * Created by tanvi on 4/22/2017.
 */

public class Category {

    private int categoryID;
    private String categoryName;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return getCategoryName();
    }
}
