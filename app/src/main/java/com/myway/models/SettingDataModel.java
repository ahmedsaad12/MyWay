package com.myway.models;

import java.io.Serializable;
import java.util.List;

public class SettingDataModel implements Serializable {

    private List<SingleSettingModel> Client_system;

    public List<SingleSettingModel> getClient_system() {
        return Client_system;
    }
}
