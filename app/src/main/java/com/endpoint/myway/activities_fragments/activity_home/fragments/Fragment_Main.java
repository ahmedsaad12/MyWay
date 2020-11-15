package com.endpoint.myway.activities_fragments.activity_home.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.endpoint.myway.R;
import com.endpoint.myway.activities_fragments.activity_cadua.CaduaActivity;
import com.endpoint.myway.activities_fragments.activity_cataloug.CatalougActivity;
import com.endpoint.myway.activities_fragments.activity_contact_us.ContactUsActivity;
import com.endpoint.myway.activities_fragments.activity_home.HomeActivity;
import com.endpoint.myway.activities_fragments.activity_news.NewsActivity;
import com.endpoint.myway.activities_fragments.activity_offers.OffersActivity;
import com.endpoint.myway.activities_fragments.activity_product.ProductActivity;
import com.endpoint.myway.activities_fragments.activity_setting.SettingActivity;
import com.endpoint.myway.activities_fragments.activityjoinmyway.JoinMyWayActivity;
import com.endpoint.myway.adapters.SlidingImage_Adapter;
import com.endpoint.myway.databinding.FragmnetMainBinding;
import com.endpoint.myway.models.Slider_Model;
import com.endpoint.myway.preferences.Preferences;
import com.endpoint.myway.remote.Api;
import com.endpoint.myway.tags.Tags;


import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Main extends Fragment {
    private static Dialog dialog;
    private HomeActivity activity;
    private FragmnetMainBinding binding;
    private LinearLayoutManager manager, manager2;
    private Preferences preferences;
    private SlidingImage_Adapter slidingImage__adapter;

    private int current_page = 0, NUM_PAGES;
    private String lang;


    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_main, container, false);
        initView();
        get_slider();
        change_slide_image();
        return binding.getRoot();
    }

    private void get_slider() {

        Api.getService(Tags.base_url).get_slider().enqueue(new Callback<Slider_Model>() {
            @Override
            public void onResponse(Call<Slider_Model> call, Response<Slider_Model> response) {
                binding.progBarSlider.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null && response.body().getSlider() != null) {
                    if (response.body().getSlider().size() > 0) {
                    update(response.body());

                    } else {

                        binding.pager.setVisibility(View.GONE);
                    }
                } else if (response.code() == 404) {
                    binding.pager.setVisibility(View.GONE);
                } else {
                    binding.pager.setVisibility(View.GONE);
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Slider_Model> call, Throwable t) {
                try {
                    binding.progBarSlider.setVisibility(View.GONE);
                    binding.pager.setVisibility(View.GONE);

                    Log.e("Error", t.getMessage());

                } catch (Exception e) {

                }

            }
        });

    }

    private void update(Slider_Model body) {
        NUM_PAGES = body.getSlider().size();
        slidingImage__adapter = new SlidingImage_Adapter(activity, body.getSlider());
        binding.pager.setAdapter(slidingImage__adapter);
    }

    private void change_slide_image() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (current_page == NUM_PAGES) {
                    current_page = 0;
                }
                binding.pager.setCurrentItem(current_page++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    private void initView() {


        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        binding.tab.setupWithViewPager(binding.pager);
        binding.progBarSlider.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.cardnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, NewsActivity.class);
                startActivity(intent);
            }
        });
        binding.cardproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProductActivity.class);
                startActivity(intent);
            }
        });

        binding.cardoffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OffersActivity.class);
                startActivity(intent);
            }
        });
        binding.cardcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ContactUsActivity.class);
                startActivity(intent);
            }
        });
        binding.cardjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, JoinMyWayActivity.class);
                startActivity(intent);
            }
        });
        binding.cardcadua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CaduaActivity.class);
                startActivity(intent);
            }
        });
        binding.cardcatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CatalougActivity.class);
                startActivity(intent);
            }
        });
        binding.cardset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SettingActivity.class);
                startActivity(intent);
            }
        });
    }


}
