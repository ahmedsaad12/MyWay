package com.endpoint.myway.activities_fragments.activity_product;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.endpoint.myway.R;
import com.endpoint.myway.adapters.Product_Adapter;
import com.endpoint.myway.databinding.ActivityProductsBinding;
import com.endpoint.myway.interfaces.Listeners;
import com.endpoint.myway.language.Language;
import com.endpoint.myway.models.ProductDataModel;
import com.endpoint.myway.models.SingleProductModel;
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


public class ProductActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityProductsBinding binding;
    private String lang;
    private Preferences preferences;

    private List<SingleProductModel> dataList;
    private Product_Adapter food_adapter;
    ImagePopup imagePopup;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        initView();
    }

    private void initView() {
        imagePopup = new ImagePopup(this);
        imagePopup.setFullScreen(true);
        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(false);
        imagePopup.setImageOnClickClose(true);  // Optional


        preferences = Preferences.getInstance();
        dataList = new ArrayList<>();

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setBackListener(this);

        food_adapter = new Product_Adapter(dataList, this);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(food_adapter);
        getProducts();

    }



    private void getProducts() {
        binding.progBar.setVisibility(View.VISIBLE);
        dataList.clear();
        food_adapter.notifyDataSetChanged();

        Api.getService(Tags.base_url)
                .getproducts("all", preferences.getCountry(this))
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            dataList.addAll(response.body().getProduct());

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
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
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

    public void showimage(String image) {
        imagePopup.initiatePopupWithPicasso(Tags.IMAGE_URL+image);
        imagePopup.viewPopup();

    }
}