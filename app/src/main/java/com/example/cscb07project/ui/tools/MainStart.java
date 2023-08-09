package com.example.cscb07project.ui.tools;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07project.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainStart {
    public static void switchToMain(AppCompatActivity activity) {
        FirebaseAuth dbAuth = FirebaseAuth.getInstance();
        String username = dbAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(username)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                DataSnapshot userSnapshot = task.getResult();
                MainActivity.currentUser = username;
                MainActivity.isOwner = Boolean.TRUE.equals(userSnapshot.child("isOwner")
                        .getValue(Boolean.class));
                MainActivity.ownerStore = userSnapshot.hasChild("storeName") ?
                        userSnapshot.child("storeName").getValue(String.class) : "";

                Intent main = new Intent(activity, MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(main);
                activity.finish();
            }
        });
    }
}
