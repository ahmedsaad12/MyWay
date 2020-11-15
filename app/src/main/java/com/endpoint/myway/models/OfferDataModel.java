package com.endpoint.myway.models;

import java.io.Serializable;
import java.util.List;

public class OfferDataModel implements Serializable {

    private List<SingleOfferModel> sale;

    public List<SingleOfferModel> getSale() {
        return sale;
    }
}
