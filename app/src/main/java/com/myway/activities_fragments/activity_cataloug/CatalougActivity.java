package com.myway.activities_fragments.activity_cataloug;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.myway.R;
import com.myway.adapters.Cataloug_Adapter;
import com.myway.adapters.Offers_Adapter;
import com.myway.databinding.ActivityCatalougeBinding;
import com.myway.databinding.ActivityOffersBinding;
import com.myway.interfaces.Listeners;
import com.myway.language.Language;
import com.myway.models.CatalougDataModel;
import com.myway.models.NewsDataModel;
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


public class CatalougActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityCatalougeBinding binding;
    private String lang;
    private Preferences preferences;

    private List<SingleCatalougModel> dataList;
    private Cataloug_Adapter food_adapter;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_catalouge);
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        dataList = new ArrayList<>();

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setBackListener(this);

        food_adapter=new Cataloug_Adapter(dataList,this);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(food_adapter);
        getProducts();
    }

    private void getProducts() {
        binding.progBar.setVisibility(View.VISIBLE);
        dataList.clear();
        food_adapter.notifyDataSetChanged();

        Api.getService(Tags.base_url)
                .getcataloug("all", preferences.getCountry(this))
                .enqueue(new Callback<CatalougDataModel>() {
                    @Override
                    public void onResponse(Call<CatalougDataModel> call, Response<CatalougDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            dataList.addAll(response.body().getCatalog());

                            if (dataList.size() > 0) {
                                food_adapter.notifyDataSetChanged();
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
                    public void onFailure(Call<CatalougDataModel> call, Throwable t) {
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