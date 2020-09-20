package com.myway.activities_fragments.activity_choose_country;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.myway.R;
import com.myway.activities_fragments.activity_home.HomeActivity;
import com.myway.databinding.ActivityChooseCountryBinding;
import com.myway.language.Language;
import com.myway.preferences.Preferences;

import io.paperdb.Paper;


public class ChooseCountryActivity extends AppCompatActivity {
    private ActivityChooseCountryBinding binding;
    private String lang;
    private Preferences preferences;


    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_country);
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
       binding.llegypt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               binding.fr.setBackground(getResources().getDrawable(R.drawable.ic_checked));
               binding.fr2.setBackground(getResources().getDrawable(R.drawable.circle_bg));
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
               preferences.create_update_country(ChooseCountryActivity.this,"egypt");

               Intent intent=new Intent(ChooseCountryActivity.this, HomeActivity.class);

               startActivity(intent);
               finish();
                   }
               },1000);
           }
       });
        binding.llsuadia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                binding.fr2.setBackground(getResources().getDrawable(R.drawable.ic_checked));
                binding.fr.setBackground(getResources().getDrawable(R.drawable.circle_bg));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        preferences.create_update_country(ChooseCountryActivity.this,"saudi");
                        Intent intent=new Intent(ChooseCountryActivity.this, HomeActivity.class);

                        startActivity(intent);
                        finish();
                    }
                },1000);

            }
        });
    }


}