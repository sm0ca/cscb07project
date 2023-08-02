package com.example.mallapp.OwnerList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OwnerListModel {

    private static final String LOGO_NODE_NAME = "image";
    private static final String PRICE_NODE_NAME = "price";
    private static final String BRAND = "brand";

    //private FirebaseAuth mAuth;
    //private FirebaseUser mUser;


    private final OwnerListPresenter presenter;
    private DatabaseReference query_owner;
    private ChildEventListener listener;

    private List<OwnerListEntry> ItemsList;

    public OwnerListModel(OwnerListPresenter presenter, String url) {
        this.presenter = presenter;
        ItemsList = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
    }

    public void createEventListener() {
        Log.d("SLM.java", "Started listener2");
        //        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();
//        String username = mUser.getUid();
// to get the current userID/name, but use a hard coded one for now
        String username = "person 1"; // hardcoded
                    getStoreName getStoreNameInstance = new getStoreName(username);
                    getStoreNameInstance.retrieveStoreName(new getStoreName.OnStoreNameListener() { // get storename
                        @Override
                        public void onStoreNameRetrieved(String storeName) {
                            query_owner = FirebaseDatabase.getInstance("https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/").getReference().child("stores").child(storeName).child("items");
                            listener = query_owner.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    String itemName = snapshot.getKey();
                                    String logoURL = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                                    double price = snapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                                    String brand = snapshot.child(BRAND).getValue(String.class);
                                    OwnerListEntry newEntry = new OwnerListEntry(itemName, logoURL, price, brand);
                                    ItemsList.add(newEntry);
                                    presenter.setAdapter(ItemsList);
                                }
                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                    String storeName = snapshot.getKey();
                                    Log.d("SLM.java", "Removed: " + storeName);
                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    onChildChanged(snapshot, previousChildName);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.d("SLM.java", "Err listening to item names");
                                    destroyEventListener();
                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Log.d("store cannot get", "cannot get storename");
                        }
                    });
                }


    public void destroyEventListener() {
        if (listener != null) {
            query_owner.removeEventListener(listener);
        }
    }

    public List<OwnerListEntry> getItemsList(){
        return ItemsList;
    }


}
