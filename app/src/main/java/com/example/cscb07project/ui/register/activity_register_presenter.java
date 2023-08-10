package com.example.cscb07project.ui.register;

import android.net.Uri;

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
        if(email.isEmpty()) {
            doToast("Enter your Email");
            view.progressBarVisibility(4);
            return;
        }
        if(password.isEmpty()) {
            doToast("Enter your Password");
            view.progressBarVisibility(4);
            return;
        }

        // check if user have chosen if they are owner or not
        if(isOwnerId == 0) {
            doToast("Select your user type");
            view.progressBarVisibility(4);
            return;
        }

        // check if store name is empty
        if(isOwnerId == view.getRadioButtonRegisterOwner() && storeName.isEmpty()) {
            doToast("Enter your store name");
            view.progressBarVisibility(4);
            return;
        }

        // check if logo is empty
        if(view.getStoreLogoUri() == null) {
            doToast("Enter your store logo");
            view.progressBarVisibility(4);
            return;
        }

        // create the user
        model.createUser(email, password, storeName, isOwnerId);
    }

    @Override
    public void doToast(String message) {
        view.doToast(message);
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
    public Uri getStoreLogoUri() {
        return view.getStoreLogoUri();
    }


}
