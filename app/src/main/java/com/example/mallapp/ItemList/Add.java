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

    public Add(String username, String store, String item){
        this.item = item;
        this.store = store;
        this.username = username;
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
                    int updatedQuantity = currentQuantity + 1;
                    mDatabase.child(username).child("cart").child(store).child(item).setValue(updatedQuantity);
                } else {
                    // If the item does not exist, set the fields for item name and quantity
                    mDatabase.child(username).child("cart").child(store).child(item).setValue(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that may occur during data retrieval
                Log.e("Firebase", "Error retrieving data from Firebase: " + error.getMessage());
            }
        });
    }

//    public void addToFirebase() {
//        Log.d("Firebase", "Adding data to Firebase...");
//        mDatabase = FirebaseDatabase.getInstance().getReference("users");
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // Check if the user already exists in the database
//                if (snapshot.hasChild(username)) {
//                    // If the user exists, check if the store already exists in the user's cart
//                    DataSnapshot userSnapshot = snapshot.child(username);
//                    if (userSnapshot.child("cart").hasChild(store)) {
//                        // If the store exists, check if the item already exists in the store's cart
//                        DataSnapshot storeSnapshot = userSnapshot.child("cart").child(store);
//                        if (storeSnapshot.hasChild(item)) {
//                            // If the item exists, update the quantity
//                            int currentQuantity = storeSnapshot.child(item).getValue(Integer.class);
//                            int updatedQuantity = currentQuantity + 1;
//                            mDatabase.child(username).child("cart").child(store).child(item).setValue(updatedQuantity);
//                        } else {
//                            // If the item does not exist, set the fields for item name and quantity
//                            mDatabase.child(username).child("cart").child(store).child(item).setValue(1);
//                        }
//                    } else {
//                        // If the store does not exist, set the fields for store, item, and quantity
//                        mDatabase.child(username).child("cart").child(store).child(item).setValue(1);
//                    }
//                } else {
//                    // If the user does not exist, create a new cart for the user and set the fields for store, item, and quantity
//                    mDatabase.child(username).child("cart").child(store).child(item).setValue(1);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Firebase", "Database operation canceled: " + error.getMessage());
//            }
//        });
//    }

}
