package com.endpoint.myway.activities_fragments.activity_detilas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.endpoint.myway.R;
import com.endpoint.myway.databinding.ActivityCatalougeBinding;
import com.endpoint.myway.databinding.ActivityDetialsBinding;
import com.endpoint.myway.interfaces.Listeners;
import com.endpoint.myway.language.Language;
import com.endpoint.myway.preferences.Preferences;

import io.paperdb.Paper;


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