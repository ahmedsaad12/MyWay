package com.endpoint.myway.activities_fragments.activityjoinmyway;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.endpoint.myway.R;
import com.endpoint.myway.databinding.ActivityContactUsBinding;
import com.endpoint.myway.databinding.ActivityJoinMyWayBinding;
import com.endpoint.myway.interfaces.Listeners;
import com.endpoint.myway.language.Language;
import com.endpoint.myway.models.JoinUsModel;
import com.endpoint.myway.remote.Api;
import com.endpoint.myway.share.Common;
import com.endpoint.myway.tags.Tags;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinMyWayActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityJoinMyWayBinding binding;
    private String lang;
    private JoinUsModel contactUsModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_my_way);
        initView();
    }


    private void initView() {
        contactUsModel = new JoinUsModel();
        binding.setContactUs(contactUsModel);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);
        binding.btnSend.setOnClickListener(v -> {
            sendContact();
        });


    }


    @Override
    public void back() {
        finish();
    }

    public void sendContact() {


        if (contactUsModel.isDataValid(this)) {
            try {
                ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
                dialog.setCancelable(false);
                dialog.show();
                Api.getService(Tags.base_url)
                        .Join(contactUsModel.getName(), contactUsModel.getEmail(), contactUsModel.getNumber(), contactUsModel.getAddress())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                dialog.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(JoinMyWayActivity.this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    if (response.code() == 422) {
                                        // Toast.makeText(ContactUsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    } else if (response.code() == 500) {
                                        Toast.makeText(JoinMyWayActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

                                    } else {
                                        //Toast.makeText(ContactUsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                        try {

                                            Log.e("error", response.code() + "_" + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                try {
                                    dialog.dismiss();
                                    if (t.getMessage() != null) {
                                        Log.e("error", t.getMessage());
                                        if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                            //  Toast.makeText(ContactUsActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(JoinMyWayActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (Exception e) {
                                }
                            }
                        });
            } catch (Exception e) {
            }
        }

    }


}
