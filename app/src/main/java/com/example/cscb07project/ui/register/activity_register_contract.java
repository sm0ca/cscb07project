package com.example.cscb07project.ui.register;


public interface activity_register_contract {

    interface View {
//        void onSuccess(String message);
//        void onError(String message);

        void progressBarVisibility(int i);
        int getRadioButtonRegisterOwner();
        int getRadioButtonRegisterCustomer();
        void loggedIn();

    }

    interface Presenter {
        void doRegisterEmail(String email, String password, String storeName, int isOwnerId);
        void doToastView(String message);
        void doCheckLoggedIn();
        void isLoggedIn();
        void changeProgressBarVisibility(int mode);
    }

    interface Model {
        void createUser(String email, String password, String storeName, int isOwnerId);
        void checkLoggedIn();

    }
}
