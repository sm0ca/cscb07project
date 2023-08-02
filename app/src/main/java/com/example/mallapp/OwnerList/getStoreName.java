package com.example.mallapp.OwnerList;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getStoreName {
    private String username;

    public interface OnStoreNameListener {
        void onStoreNameRetrieved(String storeName);
        void onFailure(String errorMessage);
    }

    public getStoreName(String username){
        this.username = username;
    }

    public void retrieveStoreName(final OnStoreNameListener listener){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference userRef = usersRef.child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String storeName = snapshot.child("storeName").getValue(String.class);
                // Pass the store name to the caller using the callback
                listener.onStoreNameRetrieved(storeName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String errorMessage = "Getting storename failed: " + error.getMessage();
                // Pass the error message to the caller using the callback
                listener.onFailure(errorMessage);
            }
        });
    }
}
