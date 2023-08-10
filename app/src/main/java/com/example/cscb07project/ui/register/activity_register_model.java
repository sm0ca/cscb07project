package com.example.cscb07project.ui.register;


import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
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

        DatabaseReference newRef = FirebaseDatabase.getInstance().getReference().child("stores");

        // checking if storeName has already existed
        newRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot storesSnap = task.getResult();
                // if exist
                if (storesSnap.hasChild(storeName)) {
                    presenter.doToast("Store name has already exist");
                    presenter.changeProgressBarVisibility(4);

                // if not
                } else {
                    user = new createUserEmail(email, password, storeName, isOwnerId, mAuth,activity_register_model.this);
                    user.create();
                    makeSampleShopItem(mAuth, isOwnerId);
                }
            }
        });
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
        presenter.doToast(string);
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
    public void makeSampleShopItem(FirebaseAuth user, int isOwnerId) {

        if(isOwnerId == R.id.radioButton_register_owner) {
            DatabaseReference temp_db = FirebaseDatabase.getInstance().getReference().child("stores")
                    .child(storeName).child("items").child("sampleItem");
            temp_db.child("brand").setValue("[item name]");
            temp_db.child("description").setValue("[item description]");
            temp_db.child("forSale").setValue(true);
            temp_db.child("image").setValue("");
            temp_db.child("price").setValue(0);
            }

    }


}
