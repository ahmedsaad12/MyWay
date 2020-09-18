package com.myway.models;

import java.io.Serializable;
import java.util.List;

public class Slider_Model implements Serializable {
    private List<Data> slider;

    public List<Data> getSlider() {
        return slider;
    }

    public class Data implements Serializable {
        private int id;

        private String image;


        public int getId() {
            return id;
        }


        public String getImage() {
            return image;
        }


    }
}
