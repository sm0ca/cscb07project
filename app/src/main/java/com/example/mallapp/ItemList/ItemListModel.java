package com.example.mallapp.ItemList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mallapp.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemListModel {

    private static final String LOGO_NODE_NAME = "image";
    private static final String ITEMS_NODE_NAME = "items";
    private static final String PRICE_NODE_NAME = "price";
    private static final String BRAND = "brand";


    private final ItemListPresenter presenter;
    private final DatabaseReference queryNames;
    private ChildEventListener listener;

    private List<ItemListEntry> ItemsList;

    public ItemListModel(ItemListPresenter presenter, String url) {
        this.presenter = presenter;
        ItemsList = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
        queryNames = db.getReference().child("stores").child(MainActivity.getStoreBundle().getString(MainActivity.getStoreBundleKey())).child(ITEMS_NODE_NAME);
    }

    public void createEventListener() {
        Log.d("SLM.java", "Started listener2");
        listener = queryNames.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("ss","hello");
                    String itemName = snapshot.getKey();
                    Log.d("name", itemName);
                    String logoURL = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                    double price = snapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                    String brand = snapshot.child(BRAND).getValue(String.class);
                    ItemListEntry newEntry = new ItemListEntry(itemName, logoURL, price, brand);
                    ItemsList.add(newEntry);

                presenter.setAdapter(ItemsList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                for (DataSnapshot itemSnapshot : snapshot.child(ITEMS_NODE_NAME).getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    String logoURL = itemSnapshot.child(LOGO_NODE_NAME).getValue(String.class);
                    double price = itemSnapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                    String brand = itemSnapshot.child(BRAND).getValue(String.class);
                    ItemListEntry updatedEntry = new ItemListEntry(itemName, logoURL, price, brand);
                    ItemsList.add(updatedEntry);
                }

                presenter.setAdapter(ItemsList);
                Log.d("SLM.java", "Changed: " + storeName);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String storeName = snapshot.getKey();
                //.remove(storeName);

                //presenter.setAdapter(storeItemsMap.get(0));
                Log.d("SLM.java", "Removed: " + storeName);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                onChildChanged(snapshot, previousChildName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SLM.java", "Err listening to store names");
                destroyEventListener();
            }
        });
    }

    public void destroyEventListener() {
        if (listener != null) {
            queryNames.removeEventListener(listener);
        }
    }

    public List<ItemListEntry> getItemsList(){
        return ItemsList;
    }


}
