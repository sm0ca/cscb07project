package com.example.cscb07project.ui.register;


import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class activity_register_model implements activity_register_contract.Model{

    public activity_register_contract.Presenter presenter;

    private FirebaseAuth mAuth;
    private createUser user;
    private String storeName;
    private int storeExists = 3;    // 0 means store doesn't exist, 1 store exists, 2 is error, 3 is awaiting input

    public activity_register_model(activity_register_contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void createUser(String email, String password, String storeName, int isOwnerId) {
        this.storeName = storeName;

        checkExistingStoreName(storeName);

        // wait checkExistingStoreName to be done
        while(true) {
            if(storeExists != 3) {
                break;
            }
        }


        if(storeExists == 1) {
            presenter.doToastView("Store name has already exist");
            presenter.changeProgressBarVisibility(4);
            return;
        }
        if(storeExists == 2) {
            presenter.doToastView("Network error, please try again");
            presenter.changeProgressBarVisibility(4);
            return;
        }

        user = new createUserEmail(email, password, storeName, isOwnerId, mAuth,this);
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

    @Override
    public void changeProgressBarVisibility(int mode) {
        presenter.changeProgressBarVisibility(mode);
    }

    @Override
    public void checkExistingStoreName(String storeName) {
        DatabaseReference temp_db = FirebaseDatabase.getInstance().getReference().child("stores").child(storeName);


        temp_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    storeExists = 1;
                }
                else {
                    storeExists = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                storeExists = 2;
            }
        });


    }


}
