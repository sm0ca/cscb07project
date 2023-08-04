package com.example.mallapp.OrderList.StoreStatusList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mallapp.OrderList.DataHolder.OrderListItemEntry;
import com.example.mallapp.OrderList.DataHolder.OrderListStoreEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StoreStatusModel implements IFStoreStatusModel {

    private static final String NODE0_ORDERS = "orders";
    private static final String NODE2_STORES = "storesItems";
    private static final String NODE4_COMPLETE = "complete";
    private static final String NODE4_ITEMS = "items";
    private final IFStoreStatusPresenter presenter;
    private final DatabaseReference queryStores;
    private ChildEventListener listener;
    private final List<OrderListStoreEntry> storeList;

    public StoreStatusModel(IFStoreStatusPresenter presenter, String url, int orderIdClicked) {
        this.presenter = presenter;
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
        String orderIdStr = Integer.toString(orderIdClicked);
        queryStores = db.getReference().child(NODE0_ORDERS).child(orderIdStr).child(NODE2_STORES);
        storeList = new ArrayList<>();
    }


    @Override
    public void createEventListener() {
        Log.d("mine", "started store status listener");
        if(listener != null) {
            return;
        }
        listener = queryStores.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                boolean storeComplete = Boolean.TRUE.equals(snapshot.child(NODE4_COMPLETE).getValue(Boolean.class));
                List<OrderListItemEntry> itemList = new ArrayList<>();
                for(DataSnapshot dsKeyIsItemName : snapshot.child(NODE4_ITEMS).getChildren()) {
                    String itemName = dsKeyIsItemName.getKey();
                    try {
                        int itemQty = Objects.requireNonNull(dsKeyIsItemName.getValue(Integer.class));
                        itemList.add(new OrderListItemEntry(itemName, itemQty));
                    }
                    catch (Exception e) {
                        int defaultItemQty = 0;
                        itemList.add(new OrderListItemEntry(itemName, defaultItemQty));
                    }
                }
                Collections.sort(itemList);
                storeList.add(new OrderListStoreEntry(storeName, storeComplete, itemList));
                Collections.sort(storeList);
                presenter.notifyAdapterDataChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                boolean storeComplete = Boolean.TRUE.equals(snapshot.child(NODE4_COMPLETE).getValue(Boolean.class));
                List<OrderListItemEntry> itemList = new ArrayList<>();
                for(DataSnapshot dsKeyIsItemName : snapshot.child(NODE4_ITEMS).getChildren()) {
                    String itemName = dsKeyIsItemName.getKey();
                    try {
                        int itemQty = Objects.requireNonNull(dsKeyIsItemName.getValue(Integer.class));
                        itemList.add(new OrderListItemEntry(itemName, itemQty));
                    }
                    catch (Exception e) {
                        int defaultItemQty = 0;
                        itemList.add(new OrderListItemEntry(itemName, defaultItemQty));
                    }
                }
                Collections.sort(itemList);
                if(!storeList.contains(new OrderListStoreEntry(storeName))) {
                    storeList.add(new OrderListStoreEntry(storeName));
                    Collections.sort(storeList);
                }
                int idxOfOld = storeList.indexOf(new OrderListStoreEntry(storeName));
                storeList.get(idxOfOld).setComplete(storeComplete);
                storeList.get(idxOfOld).setItemList(itemList);
                presenter.notifyAdapterDataChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String storeName = snapshot.getKey();
                storeList.remove(new OrderListStoreEntry(storeName));
                presenter.notifyAdapterDataChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                destroyEventListener();
            }
        });
    }

    @Override
    public void destroyEventListener() {
        if(listener != null) {
            queryStores.removeEventListener(listener);
        }
    }

    @Override
    public List<OrderListStoreEntry> getDataList() {
        return storeList;
    }
}
