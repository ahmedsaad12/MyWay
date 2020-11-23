package com.endpoint.myway.models;

import java.io.Serializable;

public class SingleProductModel implements Serializable {

    private int id;
    private String title;
    private String image;
    private String details;
    private String price;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDetails() {
        return details;
    }

    public String getPrice() {
        return price;
    }
}
