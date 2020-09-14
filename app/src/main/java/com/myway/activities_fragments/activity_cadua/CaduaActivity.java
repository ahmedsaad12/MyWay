package com.myway.activities_fragments.activity_cadua;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.myway.R;
import com.myway.databinding.ActivityCaduaBinding;
import com.myway.databinding.ActivityContactUsBinding;
import com.myway.interfaces.Listeners;
import com.myway.language.Language;
import com.myway.models.ContactUsModel;
import com.myway.remote.Api;
import com.myway.share.Common;
import com.myway.tags.Tags;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaduaActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityCaduaBinding binding;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadua);
        initView();
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);



    }


    @Override
    public void back() {
        finish();
    }



}
