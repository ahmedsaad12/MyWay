package com.myway.activities_fragments.activity_home.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.myway.R;
import com.myway.activities_fragments.activity_home.HomeActivity;
import com.myway.databinding.FragmnetMainBinding;
import com.myway.preferences.Preferences;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Main extends Fragment {
    private static Dialog dialog;
    private HomeActivity activity;
    private FragmnetMainBinding binding;
    private LinearLayoutManager manager, manager2;
    private Preferences preferences;


    private String lang;


    public static Fragment_Main newInstance() {
        return new Fragment_Main();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmnet_main, container, false);
        initView();

        return binding.getRoot();
    }


    private void initView() {


        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());


    }


}
