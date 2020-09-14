package com.myway.activities_fragments.activity_cadua;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
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
binding.edtcadua.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
int num=Integer.parseInt(binding.edtcadua.getText().toString());
if(num>=200&&num<=599){

}
else if(num>=600&&num<=1199){

}
else if(num>=1200&&num<=2399){

}
else if(num>=2400&&num<=3999){

}
else if(num>=4000&&num<=6599){

}
else if(num>=6600&&num<=9999){

}
else if(num>=10000){

}
    }
});


    }


    @Override
    public void back() {
        finish();
    }



}
