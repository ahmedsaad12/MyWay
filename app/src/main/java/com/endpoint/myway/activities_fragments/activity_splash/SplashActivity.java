package com.endpoint.myway.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.endpoint.myway.R;
import com.endpoint.myway.activities_fragments.activity_choose_country.ChooseCountryActivity;
import com.endpoint.myway.activities_fragments.activity_home.HomeActivity;
import com.endpoint.myway.databinding.ActivitySplashBinding;
import com.endpoint.myway.language.Language;
import com.endpoint.myway.preferences.Preferences;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1200);
                    if (Preferences.getInstance().getCountry(SplashActivity.this).equals("0")) {
                        Intent intent = new Intent(getApplicationContext(), ChooseCountryActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


}