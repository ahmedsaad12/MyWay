package com.endpoint.myway.activities_fragments.activity_home.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.endpoint.myway.R;
import com.endpoint.myway.activities_fragments.activity_about_app.AboutAppActivity;
import com.endpoint.myway.activities_fragments.activity_choose_country.ChooseCountryActivity;
import com.endpoint.myway.activities_fragments.activity_contact_us.ContactUsActivity;
import com.endpoint.myway.activities_fragments.activity_home.HomeActivity;
import com.endpoint.myway.activities_fragments.activity_setting.SettingActivity;
import com.endpoint.myway.activities_fragments.activityjoinmyway.JoinMyWayActivity;
import com.endpoint.myway.databinding.FragmentMoreBinding;
import com.endpoint.myway.interfaces.Listeners;
import com.endpoint.myway.models.SettingModel;
import com.endpoint.myway.models.UserModel;
import com.endpoint.myway.preferences.Preferences;
import com.endpoint.myway.remote.Api;
import com.endpoint.myway.tags.Tags;


import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_More extends Fragment implements Listeners.SettingActions {

    private HomeActivity activity;
    private FragmentMoreBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;
    private String facebook;


    public static Fragment_More newInstance() {

        return new Fragment_More();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {


        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setAction(this);
        getAppData();
    }


    @Override
    public void contactUs() {
        Intent intent = new Intent(activity, ContactUsActivity.class);
        startActivity(intent);
    }

    @Override
    public void changecountry() {
        Intent intent = new Intent(activity, ChooseCountryActivity.class);
        startActivity(intent);
    }

    @Override
    public void aboutApp() {

        Intent intent = new Intent(activity, AboutAppActivity.class);
        intent.putExtra("type", 2);

        startActivity(intent);
    }

    @Override
    public void joinMyway() {
        Intent intent = new Intent(activity, JoinMyWayActivity.class);
        startActivity(intent);
    }

    @Override
    public void ourpage() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www."+facebook));
        startActivity(intent);
    }

    @Override
    public void work() {
        Intent intent = new Intent(activity, ContactUsActivity.class);
        startActivity(intent);
    }

    private void getAppData() {

        Api.getService(Tags.base_url)
                .getSetting()
                .enqueue(new Callback<SettingModel>() {
                    @Override
                    public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                        if (response.isSuccessful() && response.body() != null) {


                            facebook = response.body().getFacebook();
                            Log.e("lllfllfl",facebook);


                        } else {
                            try {

                                Log.e("errorssss", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (response.code() == 500) {
                                //   Toast.makeText(AboutAppActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                            } else {
                                // Toast.makeText(AboutAppActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingModel> call, Throwable t) {
                        try {
                            //  binding.progBar.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    //       Toast.makeText(AboutAppActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                } else {
                                    //        Toast.makeText(AboutAppActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {
                        }
                    }
                });

    }

}
