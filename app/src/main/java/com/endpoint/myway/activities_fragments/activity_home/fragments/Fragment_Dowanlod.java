package com.endpoint.myway.activities_fragments.activity_home.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
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
import com.endpoint.myway.activities_fragments.activity_home.HomeActivity;
import com.endpoint.myway.adapters.Dowalod_Adapter;
import com.endpoint.myway.databinding.FragmentDownloadBinding;
import com.endpoint.myway.preferences.Preferences;


import java.io.File;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Dowanlod extends Fragment {

    private HomeActivity activity;
    private FragmentDownloadBinding binding;
    private Preferences preferences;
    private String lang;


    public static Fragment_Dowanlod newInstance() {
        return new Fragment_Dowanlod();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false);
        return binding.getRoot();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }


    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        binding.progBarCategory.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        getfiles();
    }

    private void getfiles() {
        String path = Environment.getExternalStorageDirectory().toString() + "/foldr";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        if(directory.listFiles()!=null) {
            File[] files = directory.listFiles();
            Dowalod_Adapter dowalod_adapter = new Dowalod_Adapter(files, activity);
            binding.recViewStatus.setLayoutManager(new LinearLayoutManager(activity));
            binding.recViewStatus.setAdapter(dowalod_adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity != null) {
            getfiles();
        }
    }
}
