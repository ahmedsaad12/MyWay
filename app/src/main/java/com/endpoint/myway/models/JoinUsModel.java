package com.endpoint.myway.models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.endpoint.myway.BR;
import com.endpoint.myway.R;

import java.io.Serializable;

public class JoinUsModel extends BaseObservable implements Serializable {

    private String name;
    private String email;
    private String number;
    private String address;
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_subject = new ObservableField<>();
    public ObservableField<String> error_message = new ObservableField<>();


    public JoinUsModel() {
        this.name = "";
        this.email = "";
        this.number = "";
        this.address = "";
    }

    public JoinUsModel(String name, String email, String number, String address) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        this.email = email;

        this.number = number;
        this.address = address;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);


    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Bindable
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    @Bindable

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDataValid(Context context) {
        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !TextUtils.isEmpty(number) &&
                !TextUtils.isEmpty(address)

        ) {
            error_name.set(null);
            error_email.set(null);
            error_subject.set(null);
            error_message.set(null);

            return true;
        } else {
            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_required));
            } else {
                error_name.set(null);
            }


            if (email.isEmpty()) {
                error_email.set(context.getString(R.string.field_required));
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error_email.set(context.getString(R.string.inv_email));
            } else {
                error_email.set(null);
            }

            if (number.isEmpty()) {
                error_subject.set(context.getString(R.string.field_required));
            } else {
                error_subject.set(null);
            }


            if (address.isEmpty()) {
                error_message.set(context.getString(R.string.field_required));
            }  else {
                error_message.set(null);

            }


            return false;
        }
    }
}
