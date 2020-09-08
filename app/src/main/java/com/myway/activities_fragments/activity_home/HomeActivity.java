package com.myway.activities_fragments.activity_home;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.myway.R;
import com.myway.activities_fragments.activity_home.fragments.Fragment_Catalouge;
import com.myway.activities_fragments.activity_home.fragments.Fragment_More;
import com.myway.activities_fragments.activity_home.fragments.Fragment_Dowanlod;
import com.myway.activities_fragments.activity_home.fragments.Fragment_Main;
import com.myway.databinding.ActivityHomeBinding;
import com.myway.language.Language;
import com.myway.models.UserModel;
import com.myway.preferences.Preferences;


import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Preferences preferences;
    private FragmentManager fragmentManager;
    private Fragment_Main fragment_main;
    private Fragment_Dowanlod fragment_dowanlod;
    private Fragment_Catalouge fragment_catalouge;
    private Fragment_More fragment_more;
    private UserModel userModel;
    private String lang;
    private String token;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
        if (savedInstanceState == null) {
            displayFragmentMain();
        }

    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        setUpBottomNavigation();


    }


    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.home), R.drawable.ic_search);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.store), R.drawable.ic_search);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.catalouge), R.drawable.ic_search);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.contact_manger), R.drawable.ic_search);

        binding.ahBottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        binding.ahBottomNav.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.color3));
        binding.ahBottomNav.setTitleTextSizeInSp(13, 13);
        binding.ahBottomNav.setForceTint(true);
        binding.ahBottomNav.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.ahBottomNav.setInactiveColor(ContextCompat.getColor(this, R.color.white));

        binding.ahBottomNav.addItem(item1);
        binding.ahBottomNav.addItem(item2);
        binding.ahBottomNav.addItem(item3);
        binding.ahBottomNav.addItem(item4);


        binding.ahBottomNav.setOnTabSelectedListener((position, wasSelected) -> {


            switch (position) {
                case 0:
                    displayFragmentMain();
                    break;
                case 1:
                    displayFragmentDownload();
                    break;
                case 2:
                    displayFragmentCatalouge();
                    break;
                case 3:
                    displayFragmentMore();
                    break;

            }
            return false;
        });

        updateBottomNavigationPosition(0);

    }

    public void updateBottomNavigationPosition(int pos) {

        binding.ahBottomNav.setCurrentItem(pos, false);

    }


    public void displayFragmentMain() {
        try {
            if (fragment_main == null) {
                fragment_main = Fragment_Main.newInstance();
            }


            if (fragment_dowanlod != null && fragment_dowanlod.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_dowanlod).commit();
            }
            if (fragment_catalouge != null && fragment_catalouge.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_catalouge).commit();
            }

            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }

            if (fragment_main.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_main).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_main, "fragment_main").addToBackStack("fragment_main").commit();

            }
            updateBottomNavigationPosition(0);
            //  binding.setTitle(getString(R.string.home));
        } catch (Exception e) {
        }

    }

    public void displayFragmentDownload() {
        try {
            if (fragment_dowanlod == null) {
                fragment_dowanlod = Fragment_Dowanlod.newInstance();
            }


            if (fragment_catalouge != null && fragment_catalouge.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_catalouge).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }

            if (fragment_dowanlod.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_dowanlod).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_dowanlod, "fragment_download").addToBackStack("fragment_download").commit();

            }
            updateBottomNavigationPosition(1);

        } catch (Exception e) {
        }

    }

    public void displayFragmentMore() {
        try {
            if (fragment_more == null) {
                fragment_more = Fragment_More.newInstance();
            }


            if (fragment_catalouge != null && fragment_catalouge.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_catalouge).commit();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_dowanlod != null && fragment_dowanlod.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_dowanlod).commit();
            }

            if (fragment_more.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_more).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_more, "fragment_comments").addToBackStack("fragment_comments").commit();

            }
            updateBottomNavigationPosition(3);

        } catch (Exception e) {
        }

    }


    public void displayFragmentCatalouge() {
        try {
            if (fragment_catalouge == null) {
                fragment_catalouge = Fragment_Catalouge.newInstance();
            }


            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }

            if (fragment_dowanlod != null && fragment_dowanlod.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_dowanlod).commit();
            }
            if (fragment_more != null && fragment_more.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_more).commit();
            }
            if (fragment_catalouge.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_catalouge).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_catalouge, "fragment_cataloug").addToBackStack("fragment_cataloug").commit();

            }
            updateBottomNavigationPosition(2);

        } catch (Exception e) {
        }

    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (fragment_main != null && fragment_main.isAdded() && fragment_main.isVisible()) {


                    finish();


        } else {
            displayFragmentMain();
        }
    }



}
