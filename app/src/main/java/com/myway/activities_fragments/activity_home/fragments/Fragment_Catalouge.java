package com.myway.activities_fragments.activity_home.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.myway.R;
import com.myway.activities_fragments.activity_home.HomeActivity;
import com.myway.databinding.FragmentCatalougBinding;
import com.myway.models.Model_images;
import com.myway.models.UserModel;
import com.myway.preferences.Preferences;

import java.util.ArrayList;
import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Catalouge extends Fragment {

    private HomeActivity activity;
    private FragmentCatalougBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;



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
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

//        binding.gvFolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                /*Intent intent = new Intent(activity, PhotosActivity.class);
//                intent.putExtra("value",i);
//                startActivity(intent);*/
//            }
//        });

    }



}