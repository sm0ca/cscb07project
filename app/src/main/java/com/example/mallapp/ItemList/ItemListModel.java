package com.example.mallapp.ItemList;

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

public class ItemListModel {

    private static final String LOGO_NODE_NAME = "image";
    private static final String ITEMS_NODE_NAME = "items";
    private static final String PRICE_NODE_NAME = "price";
    private static final String BRAND = "brand";


    private final ItemListPresenter presenter;
    private final DatabaseReference queryNames;
    private ChildEventListener listener;

    private List<List<ItemListEntry>> storeItemsMap;

    public ItemListModel(ItemListPresenter presenter, String url) {
        this.presenter = presenter;
        storeItemsMap = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
        queryNames = db.getReference().child("stores");
    }

    public void createEventListener() {
        Log.d("SLM.java", "Started listener");
        listener = queryNames.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                List<ItemListEntry> itemsList = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.child(ITEMS_NODE_NAME).getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    String logoURL = itemSnapshot.child(LOGO_NODE_NAME).getValue(String.class);
                    double price = itemSnapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                    String brand = itemSnapshot.child(BRAND).getValue(String.class);
                    ItemListEntry newEntry = new ItemListEntry(itemName, logoURL, price, brand);
                    itemsList.add(newEntry);
                }

                storeItemsMap.add(itemsList);
               // storeItemsMap.set(number,itemsList);

                presenter.setAdapter(storeItemsMap.get(0));
                Log.d("SLM.java", "Added: " + storeName);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                List<ItemListEntry> itemsList = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.child(ITEMS_NODE_NAME).getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    String logoURL = itemSnapshot.child(LOGO_NODE_NAME).getValue(String.class);
                    double price = itemSnapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                    String brand = itemSnapshot.child(BRAND).getValue(String.class);
                    ItemListEntry updatedEntry = new ItemListEntry(itemName, logoURL, price, brand);
                    itemsList.add(updatedEntry);
                }
                storeItemsMap.add(itemsList);

                presenter.setAdapter(storeItemsMap.get(0));
                Log.d("SLM.java", "Changed: " + storeName);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String storeName = snapshot.getKey();
                storeItemsMap.remove(storeName);

                presenter.setAdapter(storeItemsMap.get(0));
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

}
