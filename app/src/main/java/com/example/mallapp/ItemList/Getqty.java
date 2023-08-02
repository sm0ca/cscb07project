package com.example.mallapp.ItemList;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mallapp.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Getqty {
    private int qty;
    private String username;
    private String itemname;
    private String storename;


    private DatabaseReference mDatabase;
    private ValueEventListener valueEventListener;
    private ChildEventListener childEventListener;


    public interface QuantityCallback {
        void onQuantityReceived(int quantity);
        void onCancelled(DatabaseError databaseError);
    }

    public Getqty(String username, String itemname, String storename){
        this.username = username;
        this.itemname = itemname;
        this.storename = storename;
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(username).child("cart");
    }


    public void getQuantityFromFirebase(final QuantityCallback callback) {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer quantity = dataSnapshot.child(storename).child(itemname).getValue(Integer.class);

                if (quantity != null) {
                    qty = quantity;
                    callback.onQuantityReceived(qty); // Notify the callback with the quantity
                } else {
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled(databaseError);
            }
        };

        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }

    public void startListening() {
        mDatabase.addValueEventListener(valueEventListener);
    }

    public void stopListening() {
        mDatabase.removeEventListener(valueEventListener);
    }

    public int getQuantity() {
        return qty;
    }

}


