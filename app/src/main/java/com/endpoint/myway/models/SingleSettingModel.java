package com.endpoint.myway.models;

import java.io.Serializable;

public class SingleSettingModel implements Serializable {

    private int id;
    private String title;
    private String image;
    private String content;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }
}
