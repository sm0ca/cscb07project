package com.example.mallapp.ui.shop.OwnerList;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delete_Item {
    private String storename;
    private String itemname;

    private DatabaseReference mDatabase;

    public Delete_Item (String storename, String itemname){
        this.itemname = itemname;
        this.storename = storename;
    }

    public void delete(){
        Log.d("Firebase", "Accessing Firebase...");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("stores").child(storename);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("items").hasChild(itemname)) {
                        mDatabase.child("items").child(itemname).child("forSale").setValue(false);
                        Log.d("Firebase", "Item '" + itemname + "' set forSale=false");
                    } else {
                        Log.d("Firebase", "Item '" + itemname + "' does not exist");
                    }
                } else {
                    Log.d("Firebase", "Store '" + storename + "' does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase", "Error: " + error.getMessage());
            }
        });
    }

}
