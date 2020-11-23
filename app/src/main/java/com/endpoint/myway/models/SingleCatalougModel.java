package com.endpoint.myway.models;

import java.io.Serializable;
import java.util.List;

public class SingleCatalougModel implements Serializable {

    private int id;
    private String title;
    private String image;
    private String content;
    private String file;
    private List<Images> images;

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

    public String getFile() {
        return file;
    }

    public List<Images> getImages() {
        return images;
    }

    public class Images implements Serializable {

        private String image;

        public String getImage() {
            return image;
        }
    }
}
