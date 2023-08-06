package com.example.mallapp.ui.OwnerList;

import static com.example.mallapp.MainActivity.currentUser;

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

    private static final String ForSale = "forSale";

    private static final String DESCRIP = "description";


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
        String username = currentUser;
                    getStoreName getStoreNameInstance = new getStoreName(username);
                    getStoreNameInstance.retrieveStoreName(new getStoreName.OnStoreNameListener() { // get storename
                        @Override
                        public void onStoreNameRetrieved(String storeName) {
                            query_owner = FirebaseDatabase.getInstance("https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/").getReference().child("stores").child(storeName).child("items");
                            listener = query_owner.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                   boolean forSale = snapshot.child(ForSale).getValue(Boolean.class);
                                    if(forSale)
                                    {
                                        String itemName = snapshot.getKey();
                                        String logoURL = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                                        double price = snapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                                        String brand = snapshot.child(BRAND).getValue(String.class);
                                        String description = snapshot.child(DESCRIP).getValue(String.class);
                                        OwnerListEntry newEntry = new OwnerListEntry(itemName, logoURL, price, brand, description);
                                        ItemsList.add(newEntry);
                                        presenter.setAdapter(ItemsList);
                                    }
                                    else{
                                    }
                                }
                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    boolean forSale = snapshot.child(ForSale).getValue(Boolean.class);
                                    if (!forSale) {
                                        String itemName = snapshot.getKey();
                                        // Find and remove the item from the ItemsList
                                        for (OwnerListEntry entry : ItemsList) {
                                            if (entry.getItemName().equals(itemName)) {
                                                ItemsList.remove(entry);
                                                break;
                                            }
                                        }
                                        presenter.setAdapter(ItemsList);
                                    }
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
