package com.example.cscb07project.ui.login_register;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createUserEmail implements createUser{
    private String email;
    private String password;
    private String storeName;
    private int isOwner_check;
    private FirebaseAuth mAuth;
    private Context context;
    private View view;
    public createUserEmail (String email, String password, String storeName, int isOwner_check, FirebaseAuth mAuth, Context context, View view) {
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.isOwner_check = isOwner_check;
        this.mAuth = mAuth;
        this.context =context;
        this.view = view;
    }
    @Override
    public void create() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        activity_register.getRegisterProgressBar().setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            Log.d("TAG_REGISTER", "createUserWithEmail:success");

                            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().
                                    child("users").child(mAuth.getCurrentUser().getUid());
                            if(isOwner_check == R.id.radioButton_register_owner) {
                                dbReference.child("isOwner").setValue(true);
                                dbReference.child("storeName").setValue(storeName);
                            }
                            else if(isOwner_check == R.id.radioButton_register_customer) {
                                dbReference.child("isOwner").setValue(false);
                                dbReference.child("cart").setValue("");
                            }
                            else {
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w("TAG_REGISTER", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



}
