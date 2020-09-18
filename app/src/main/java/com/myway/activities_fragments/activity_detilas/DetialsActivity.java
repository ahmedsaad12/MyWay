package com.myway.activities_fragments.activity_detilas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myway.R;
import com.myway.adapters.Cataloug_Adapter;
import com.myway.databinding.ActivityCatalougeBinding;
import com.myway.databinding.ActivityDetialsBinding;
import com.myway.interfaces.Listeners;
import com.myway.language.Language;
import com.myway.models.CatalougDataModel;
import com.myway.models.SingleCatalougModel;
import com.myway.preferences.Preferences;
import com.myway.remote.Api;
import com.myway.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetialsActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityDetialsBinding binding;
    private String lang;
    private Preferences preferences;
    private String title,content,image;


    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detials);
        getDataFromIntent();
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setTitle(title);
        binding.setContent(content);
        binding.setImage(image);
        binding.setBackListener(this);


    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            image = intent.getStringExtra("image");

        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    @Override
    public void back() {
        finish();
    }
}