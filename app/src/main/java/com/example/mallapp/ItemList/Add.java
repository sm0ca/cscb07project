package com.example.mallapp.ItemList;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add {
    private DatabaseReference mDatabase;
    private String username;
    private String store;
    private String item;

    private int qty;

    public Add(String username, String store, String item, int qty){
        this.item = item;
        this.store = store;
        this.username = username;
        this.qty = qty;

    }

    public void addToFirebase() {
        Log.d("Firebase", "Adding data to Firebase...");
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Assuming the user always exists in the database
                DataSnapshot userSnapshot = snapshot.child(username);

                // Check if the item already exists in the store's cart
                if (userSnapshot.child("cart").child(store).hasChild(item)) {
                    // If the item exists, update the quantity
                    int currentQuantity = userSnapshot.child("cart").child(store).child(item).getValue(Integer.class);
                    int updatedQuantity = currentQuantity + qty;
                    mDatabase.child(username).child("cart").child(store).child(item).setValue(updatedQuantity);
                } else {
                    // If the item does not exist, set the fields for item name and quantity
                    mDatabase.child(username).child("cart").child(store).child(item).setValue(qty);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that may occur during data retrieval
                Log.e("Firebase", "Error retrieving data from Firebase: " + error.getMessage());
            }
        });
    }
}
