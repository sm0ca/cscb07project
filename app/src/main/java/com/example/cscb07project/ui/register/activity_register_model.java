package com.example.cscb07project.ui.register;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_register_model implements activity_register_contract.Model{

    activity_register_contract.Presenter presenter;

    private FirebaseAuth mAuth;
    private createUser user;

    public activity_register_model(activity_register_contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void createUser(String email, String password, String storeName, int isOwnerId) {
        user = new createUserEmail(email, password, storeName, isOwnerId, mAuth, presenter);
        user.create();
    }

    @Override
    public void checkLoggedIn() {
        mAuth = FirebaseAuth.getInstance();
        // Checking if logged in
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    presenter.isLoggedIn();
                }
            }
        });
    }
}
