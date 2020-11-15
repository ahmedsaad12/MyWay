package com.endpoint.myway.activities_fragments.activity_setting;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.endpoint.myway.R;
import com.endpoint.myway.adapters.Setting_Adapter;
import com.endpoint.myway.databinding.ActivityNewsBinding;
import com.endpoint.myway.databinding.ActivitySettingBinding;
import com.endpoint.myway.interfaces.Listeners;
import com.endpoint.myway.language.Language;
import com.endpoint.myway.models.SettingDataModel;
import com.endpoint.myway.models.SingleSettingModel;
import com.endpoint.myway.preferences.Preferences;
import com.endpoint.myway.remote.Api;
import com.endpoint.myway.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivitySettingBinding binding;
    private String lang;
    private Preferences preferences;

    private List<SingleSettingModel> dataList;
    private Setting_Adapter news_adapter;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        dataList = new ArrayList<>();

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setBackListener(this);

        news_adapter = new Setting_Adapter
                (dataList, this);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(news_adapter);
        getProducts();
    }

    private void getProducts() {
        binding.progBar.setVisibility(View.VISIBLE);
        dataList.clear();
        news_adapter.notifyDataSetChanged();

        Api.getService(Tags.base_url)
                .getsetting("all", preferences.getCountry(this))
                .enqueue(new Callback<SettingDataModel>() {
                    @Override
                    public void onResponse(Call<SettingDataModel> call, Response<SettingDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            dataList.addAll(response.body().getClient_system());

                            if (dataList.size() > 0) {
                                news_adapter.notifyDataSetChanged();
                                binding.tvNoEvents.setVisibility(View.GONE);
                            } else {
                                binding.tvNoEvents.setVisibility(View.VISIBLE);

                            }


                        } else {
                            binding.progBar.setVisibility(View.GONE);

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                // Toast.makeText(SearchActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(SearchActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingDataModel> call, Throwable t) {
                        try {
                            binding.progBar.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //  Toast.makeText(SearchActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(SearchActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
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