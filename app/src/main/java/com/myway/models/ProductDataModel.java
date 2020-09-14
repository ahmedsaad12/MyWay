package com.myway.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }


    public static class  Data implements Serializable {
        private List<SingleProductModel> products;

        public List<SingleProductModel> getProducts() {
            return products;
        }
    }
}
