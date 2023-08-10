package com.example.cscb07project.ui.register;


import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;

public interface activity_register_contract {

    interface View {
        void progressBarVisibility(int i);
        void setStoreLogo(Uri uri);
        int getRadioButtonRegisterOwner();
        int getRadioButtonRegisterCustomer();
        void loggedIn();
        Uri getStoreLogoUri();
        void doToast(String message);

    }

    interface Presenter {
        void doRegisterEmail(String email, String password, String storeName, int isOwnerId);
        void doToast(String message);
        void doCheckLoggedIn();
        void isLoggedIn();
        void changeProgressBarVisibility(int mode);
        Uri getStoreLogoUri();
    }

    interface Model {
        void createUser(String email, String password, String storeName, int isOwnerId);
        void checkLoggedIn();
        String setStoreLogoData(Uri uri);
        void doToastView(String string);
        Uri getStoreLogoUri();
        void changeProgressBarVisibility(int mode);
        void makeSampleShopItem(FirebaseAuth user, int isOwnerId);

    }
}
