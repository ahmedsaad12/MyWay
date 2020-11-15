package com.endpoint.myway.preferences;

import android.content.Context;
import android.content.SharedPreferences;


public class Preferences {

    private static Preferences instance = null;

    private Preferences() {
    }

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }


    public void create_update_country(Context context, String country) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("country", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state", country);
        editor.apply();


    }

    public String getCountry(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("country", Context.MODE_PRIVATE);
        String country = preferences.getString("state", "0");
        return country;
    }


 /*   public void createUpdateAppSetting(Context context, DefaultSettings settings) {
        SharedPreferences preferences = context.getSharedPreferences("settingsRef", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = gson.toJson(settings);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("settings", data);
        editor.apply();
    }*/
/*
    public DefaultSettings getAppSetting(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settingsRef", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(preferences.getString("settings",""),DefaultSettings.class);
    }*/






}
