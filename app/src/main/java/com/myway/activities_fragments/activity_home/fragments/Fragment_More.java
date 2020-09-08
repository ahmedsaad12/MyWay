package com.myway.activities_fragments.activity_home.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.myway.R;
import com.myway.activities_fragments.activity_choose_country.ChooseCountryActivity;
import com.myway.activities_fragments.activity_contact_us.ContactUsActivity;
import com.myway.activities_fragments.activity_home.HomeActivity;
import com.myway.databinding.FragmentMoreBinding;
import com.myway.interfaces.Listeners;
import com.myway.models.UserModel;
import com.myway.preferences.Preferences;


import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_More extends Fragment implements Listeners.SettingActions {

    private HomeActivity activity;
    private FragmentMoreBinding binding;
    private Preferences preferences;
    private String lang;
    private UserModel userModel;


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

        binding.setAction(this);
    }


    @Override
    public void contactUs() {
        Intent intent=new Intent(activity, ContactUsActivity.class);
        startActivity(intent);
    }

    @Override
    public void changecountry() {
        Intent intent=new Intent(activity, ChooseCountryActivity.class);
        startActivity(intent);
    }

    @Override
    public void aboutApp() {

    }

    @Override
    public void joinMyway() {

    }

    @Override
    public void ourpage() {

    }
}
