package com.example.cscb07project.ui.register;


import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class activity_register_model implements activity_register_contract.Model{

    public activity_register_contract.Presenter presenter;

    private FirebaseAuth mAuth;
    private createUser user;
    private String storeName;

    public activity_register_model(activity_register_contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void createUser(String email, String password, String storeName, int isOwnerId) {
        this.storeName = storeName;

        user = new createUserEmail(email, password, storeName, isOwnerId, mAuth,this, presenter);
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

    @Override
    public String setStoreLogoData(Uri uri) {
        StorageReference firebaseStorageReference = FirebaseStorage.getInstance().getReference("images/" +
                storeName + "/" + storeName + "Logo");
       firebaseStorageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

       return firebaseStorageReference.getPath();


    }

    @Override
    public void doToastView(String string) {
        presenter.doToastView(string);
    }

    @Override
    public Uri getStoreLogoUri() {
        return presenter.getStoreLogoUri();
    }
}
