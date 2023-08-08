package com.example.cscb07project.ui.login;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_login_model implements activity_login_contract.Model {

    private FirebaseAuth mAuth;
    private activity_login_contract.Presenter presenter;
    private loggingInUser user;


    public activity_login_model(activity_login_contract.Presenter presenter) {
        this.presenter = presenter;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void loggingInUser(String email, String password) {
        user = new loggingInUserEmail(email, password, mAuth, presenter);
        user.enter();
    }

    @Override
    public void checkLoggedIn() {
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null) {
                    presenter.isLoggedIn();
                }
            }
        });
    }
}
