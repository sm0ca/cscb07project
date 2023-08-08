package com.example.cscb07project.ui.register;

import static android.app.Activity.RESULT_OK;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class activity_register_presenter implements activity_register_contract.Presenter {

    private activity_register_contract.View view;
    private activity_register_contract.Model model;

    public activity_register_presenter(activity_register_contract.View view) {
        this.view = view;
        model = new activity_register_model(this);
    }


    @Override
    public void doRegisterEmail(String email, String password, String storeName, int isOwnerId) {
        // Check if email and password is empty
        if(TextUtils.isEmpty(email)) {
            Toast.makeText((Context) view, "Enter your Email", Toast.LENGTH_SHORT).show();
            view.progressBarVisibility(4);
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText((Context) view, "Enter your Password", Toast.LENGTH_SHORT).show();
            view.progressBarVisibility(4);
            return;
        }

        // check if user have chosen if they aare owner or not
        if(isOwnerId == 0) {
            Toast.makeText((Context) view, "Select your user type", Toast.LENGTH_SHORT).show();
            view.progressBarVisibility(4);
            return;
        }

        // check if store name is empty
        if(isOwnerId == view.getRadioButtonRegisterOwner() && TextUtils.isEmpty(storeName)) {
            Toast.makeText((Context) view, "Enter your store name", Toast.LENGTH_SHORT).show();
            view.progressBarVisibility(4);
            return;
        }

        // check if logo is empty
        if(view.getStoreLogoUri() == null) {
            Toast.makeText((Context) view, "Enter your store logo", Toast.LENGTH_SHORT).show();
            view.progressBarVisibility(4);
            return;
        }

        // create the user
        model.createUser(email, password, storeName, isOwnerId);
    }

    @Override
    public void doToastView(String message) {
        Toast.makeText((Context) view, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doCheckLoggedIn() {
        model.checkLoggedIn();
    }

    @Override
    public void isLoggedIn() {
        view.loggedIn();
    }

    @Override
    public void changeProgressBarVisibility(int mode) {
        view.progressBarVisibility(mode);
    }

    @Override
    public String setStoreLogo() {
        return model.setStoreLogoData(getStoreLogoUri());
    }

    @Override
    public Uri getStoreLogoUri() {
        return view.getStoreLogoUri();
    }


}
