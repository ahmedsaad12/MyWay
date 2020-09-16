package com.myway.models;

import java.io.Serializable;
import java.util.List;

public class CatalougDataModel implements Serializable {

    private List<SingleCatalougModel> catalog;

    public List<SingleCatalougModel> getCatalog() {
        return catalog;
    }
}
