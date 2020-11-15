package com.endpoint.myway.models;

import java.io.Serializable;

public class SingleProductModel implements Serializable {

    private int id;
    private String title;
    private String image;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
