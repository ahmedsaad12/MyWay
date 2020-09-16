package com.myway.models;

import java.io.Serializable;
import java.util.List;

public class NewsDataModel implements Serializable {

    private List<SingleNewsModel> news;

    public List<SingleNewsModel> getNews() {
        return news;
    }
}
