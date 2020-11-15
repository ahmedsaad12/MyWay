package com.endpoint.myway.activities_fragments.activity_choose_country;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.endpoint.myway.R;
import com.endpoint.myway.activities_fragments.activity_home.HomeActivity;
import com.endpoint.myway.databinding.ActivityChooseCountryBinding;
import com.endpoint.myway.language.Language;
import com.endpoint.myway.preferences.Preferences;

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
        if (preferences.getCountry(this).equals("egypt")) {
            binding.fr.setChecked(true);
        } else if (preferences.getCountry(this).equals("saudi")) {
            binding.fr2.setChecked(true);
        }
        binding.fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fr2.setChecked(false);
                binding.fr.setChecked(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        preferences.create_update_country(ChooseCountryActivity.this, "egypt");

                        Intent intent = new Intent(ChooseCountryActivity.this, HomeActivity.class);

                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            }
        });
        binding.fr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fr.setChecked(false);
                binding.fr2.setChecked(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        preferences.create_update_country(ChooseCountryActivity.this, "saudi");
                        Intent intent = new Intent(ChooseCountryActivity.this, HomeActivity.class);

                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            }
        });
        binding.llegypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.fr.setChecked(true);
                binding.fr2.setChecked(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        preferences.create_update_country(ChooseCountryActivity.this, "egypt");

                        Intent intent = new Intent(ChooseCountryActivity.this, HomeActivity.class);

                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            }
        });
        binding.llsuadia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                binding.fr2.setChecked(true);
                binding.fr.setChecked(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        preferences.create_update_country(ChooseCountryActivity.this, "saudi");
                        Intent intent = new Intent(ChooseCountryActivity.this, HomeActivity.class);

                        startActivity(intent);
                        finish();
                    }
                }, 1000);

            }
        });
    }


}