package com.endpoint.myway.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.endpoint.myway.R;
import com.endpoint.myway.activities_fragments.activity_home.HomeActivity;
import com.endpoint.myway.adapters.Cataloug_Adapter;
import com.endpoint.myway.databinding.FragmentCatalougBinding;
import com.endpoint.myway.models.CatalougDataModel;
import com.endpoint.myway.models.SingleCatalougModel;
import com.endpoint.myway.models.UserModel;
import com.endpoint.myway.preferences.Preferences;
import com.endpoint.myway.remote.Api;
import com.endpoint.myway.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Catalouge extends Fragment {

    private HomeActivity activity;
    private FragmentCatalougBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private List<SingleCatalougModel> dataList;
    private Cataloug_Adapter food_adapter;

    public static Fragment_Catalouge newInstance() {
        return new Fragment_Catalouge();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cataloug, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        dataList=new ArrayList<>();
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
food_adapter=new Cataloug_Adapter(dataList,activity);
binding.recViewStatus.setLayoutManager(new LinearLayoutManager(activity));
binding.recViewStatus.setAdapter(food_adapter);
//        binding.gvFolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                /*Intent intent = new Intent(activity, PhotosActivity.class);
//                intent.putExtra("value",i);
//                startActivity(intent);*/
//            }
//        });


        getProducts();
    }

    private void getProducts() {
        binding.progBarCategory.setVisibility(View.VISIBLE);
        dataList.clear();
        food_adapter.notifyDataSetChanged();

        Api.getService(Tags.base_url)
                .getcataloug("all", preferences.getCountry(activity))
                .enqueue(new Callback<CatalougDataModel>() {
                    @Override
                    public void onResponse(Call<CatalougDataModel> call, Response<CatalougDataModel> response) {
                        binding.progBarCategory.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            dataList.addAll(response.body().getCatalog());

                            if (dataList.size() > 0) {
                                food_adapter.notifyDataSetChanged();
                               // binding.tv.setVisibility(View.GONE);
                            } else {
                                //binding.tvNoEvents.setVisibility(View.VISIBLE);

                            }


                        } else {
                            binding.progBarCategory.setVisibility(View.GONE);

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
                            binding.progBarCategory.setVisibility(View.GONE);

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


}