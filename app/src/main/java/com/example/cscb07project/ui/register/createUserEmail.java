package com.example.cscb07project.ui.register;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.ui.tools.MainStart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class createUserEmail implements createUser{
    private String email;
    private String password;
    private String storeName;
    private int isOwner_check;
    private FirebaseAuth mAuth;
    private activity_register_contract.Model model;
    public createUserEmail (String email, String password, String storeName, int isOwner_check,
                            FirebaseAuth mAuth, activity_register_contract.Model model) {
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.isOwner_check = isOwner_check;
        this.mAuth = mAuth;
        this.model = model;
    }
    @Override
    public void create() {
        // check if storeName is not taken
        FirebaseDatabase.getInstance().getReference("stores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(storeName)) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // checking and uploading storeLogo
        if(isOwner_check == R.id.radioButton_register_owner && model.getStoreLogoUri() == null) {
            model.doToastView("Upload store logo");
            return;
        }

        // creating the account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        model.changeProgressBarVisibility(4);

                        if (task.isSuccessful()) {
                            Log.i("TAG_REGISTER", "createUserWithEmail:success");
                            model.checkLoggedIn();

                            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().
                                    child("users").child(mAuth.getCurrentUser().getUid());

                            // if user is owner
                            if (isOwner_check == R.id.radioButton_register_owner) {
                                dbReference.child("isOwner").setValue(true);
                                dbReference.child("storeName").setValue(storeName);
                                // setValue store's logo with image path
                                FirebaseDatabase.getInstance().getReference("stores/" + storeName + "/" + "logo")
                                        .setValue(model.setStoreLogoData(model.getStoreLogoUri()));

                                // setValue store's owner
                                FirebaseDatabase.getInstance().getReference("stores/" + storeName + "/" + "owner")
                                        .setValue(mAuth.getUid());


                            // if user is customer
                            } else if (isOwner_check == R.id.radioButton_register_customer) {
                                dbReference.child("isOwner").setValue(false);
                                dbReference.child("cart").setValue("");
                            }
                        } else {
                            Log.w("TAG_REGISTER", "createUserWithEmail:failure");
                            model.doToastView("Authentication failed");
                        }
                    }
                });
    }

}
