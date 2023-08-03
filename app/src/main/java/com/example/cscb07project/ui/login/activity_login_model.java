package com.example.cscb07project.ui.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cscb07project.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
