package com.endpoint.myway.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel implements Serializable {

        private List<SingleProductModel> product;

    public List<SingleProductModel> getProduct() {
        return product;
    }
}
