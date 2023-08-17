package com.example.cscb07project.ui.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_login_model implements activity_login_contract.Model {
    private final activity_login_contract.Presenter presenter;


    public activity_login_model(activity_login_contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loggingInUser(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        loggingInUser user = new loggingInUserEmail(email, password, mAuth, this);
        user.enter();
    }

    @Override
    public void checkLoggedIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(firebaseAuth -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null) {
                presenter.isLoggedIn();
            }
        });
    }

    @Override
    public void changeProgressBarVisibility(int mode) {
        presenter.changeProgressBarVisibility(mode);
    }

    @Override
    public void doToastView(String message) {
        presenter.doToast(message);
    }
}
