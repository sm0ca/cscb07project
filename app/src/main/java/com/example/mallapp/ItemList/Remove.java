package com.example.mallapp.ItemList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Remove {
    private DatabaseReference mDatabase;
    private String username;
    private String store;
    private String item;

    public Remove(String username, String store, String item){
        this.item = item;
        this.username = username;
        this.store = store;
    }

    public void RemovefromDatabase(){
        Log.d("Firebase", "Removing data from Firebase...");
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot userSnapshot = snapshot.child(username);
                // check if the store already exists
                if(userSnapshot.child("cart").hasChild(store)){
                    // Check if the item already exists in the store's cart
                    if (userSnapshot.child("cart").child(store).hasChild(item)) {
                        // If the item exists, reduce the quantity
                        int currentQuantity = userSnapshot.child("cart").child(store).child(item).getValue(Integer.class);
                        int updatedQuantity = currentQuantity - 1;
                        if(updatedQuantity >= 0){
                            mDatabase.child(username).child("cart").child(store).child(item).setValue(updatedQuantity);
                        }
                        else{
                            // always set to 0 to avoid negative quantities
                            mDatabase.child(username).child("cart").child(store).child(item).setValue(0);
                        }
                    } else {
                        // If the item does not exist, just return
                        return;
                    }
                }
                else{
                    // store does not exist, just return
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error removing data from Firebase: " + error.getMessage());
            }
        });
    }
}
