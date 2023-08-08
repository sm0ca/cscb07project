package com.example.cscb07project.ui.login;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class activity_login_presenter implements activity_login_contract.Presenter {

    activity_login_contract.View view;
    activity_login_contract.Model model;

    public activity_login_presenter(activity_login_contract.View view) {
        this.view = view;
        model = new activity_login_model(this);
    }

    @Override
    public void doLoginEmail(String email, String password) {
        // check if email or password is empty
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

        model.loggingInUser(email, password);

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
}
