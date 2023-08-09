package com.example.cscb07project.ui.orders.OrderList.StoreStatusList;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cscb07project.ui.orders.OrderList.DataHolder.OrderListItemEntry;
import com.example.cscb07project.ui.orders.OrderList.DataHolder.OrderListStoreEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StoreStatusModel implements IFStoreStatusModel {

    private static final String NODE0_ORDERS = "orders";
    private static final String NODE2_STORES = "storesItems";
    private static final String NODE4_COMPLETE = "complete";
    private static final String NODE4_ITEMS = "items";
    private static final String NODE_IMG0 = "stores";
    private static final String NODE_IMG2_ITEMS = "items";
    private static final String NODE_IMG2_LOGO = "logo";
    private static final String NODE_IMG4 = "image";
    private final IFStoreStatusPresenter presenter;
    private final String url;
    private final DatabaseReference queryStores;
    private ValueEventListener listener;
//    private ChildEventListener listener;
    private final List<OrderListStoreEntry> storeList;
    private final List<StoreStatusImages> imagesList;

    public StoreStatusModel(IFStoreStatusPresenter presenter, String url, String orderIdClicked) {
        this.presenter = presenter;
        this.url = url;
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
        String orderIdStr = orderIdClicked;
        queryStores = db.getReference().child(NODE0_ORDERS).child(orderIdStr).child(NODE2_STORES);
        storeList = new ArrayList<>();
        imagesList = new ArrayList<>();
    }


    @Override
    public void createEventListener() {
        if(listener != null) {
            return;
        }
        listener = queryStores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeList.clear();
                for (StoreStatusImages image : imagesList) {
                    image.removeListener();
                }
                imagesList.clear();
                for(DataSnapshot dsKeyIsStoreName : snapshot.getChildren()) {
                    String storeName = dsKeyIsStoreName.getKey();
                    boolean storeComplete = Boolean.TRUE.equals(dsKeyIsStoreName.child(NODE4_COMPLETE).getValue(Boolean.class));
                    List<OrderListItemEntry> itemList = new ArrayList<>();
                    for(DataSnapshot dsKeyIsItemName : dsKeyIsStoreName.child(NODE4_ITEMS).getChildren()) {
                        String itemName = dsKeyIsItemName.getKey();
                        try {
                            int itemQty = Objects.requireNonNull(dsKeyIsItemName.getValue(Integer.class));
                            OrderListItemEntry item = new OrderListItemEntry(itemName, itemQty);
                            itemList.add(item);
                            String path = String.format("%s/%s/%s/%s/%s", NODE_IMG0, storeName, NODE_IMG2_ITEMS, itemName, NODE_IMG4);
                            imagesList.add(new StoreStatusImages(item, path, url, presenter));
                        }
                        catch (Exception e) {
                            int defaultItemQty = 0;
                            OrderListItemEntry item = new OrderListItemEntry(itemName, defaultItemQty);
                            itemList.add(item);
                            String path = String.format("%s/%s/%s/%s/%s", NODE_IMG0, storeName, NODE_IMG2_ITEMS, itemName, NODE_IMG4);
                            imagesList.add(new StoreStatusImages(item, path, url, presenter));
                        }
                    }
                    Collections.sort(itemList);
                    OrderListStoreEntry store = new OrderListStoreEntry(storeName, storeComplete, itemList);
                    storeList.add(store);
                    Collections.sort(storeList);
                    String storeLogoPath = String.format("%s/%s/%s", NODE_IMG0, storeName, NODE_IMG2_LOGO);
                    imagesList.add(new StoreStatusImages(store, storeLogoPath, url, presenter));
                }
                presenter.notifyAdapterDataChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                destroyEventListener();
            }
        });
//        listener = queryStores.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String storeName = snapshot.getKey();
//                boolean storeComplete = Boolean.TRUE.equals(snapshot.child(NODE4_COMPLETE).getValue(Boolean.class));
//                List<OrderListItemEntry> itemList = new ArrayList<>();
//                for(DataSnapshot dsKeyIsItemName : snapshot.child(NODE4_ITEMS).getChildren()) {
//                    String itemName = dsKeyIsItemName.getKey();
//                    try {
//                        int itemQty = Objects.requireNonNull(dsKeyIsItemName.getValue(Integer.class));
//                        OrderListItemEntry item = new OrderListItemEntry(itemName, itemQty);
//                        itemList.add(item);
//                        String path = String.format("%s/%s/%s/%s/%s", NODE_IMG0, storeName, NODE_IMG2_ITEMS, itemName, NODE_IMG4);
//                        imagesList.add(new StoreStatusImages(item, path, url, presenter));
//                    }
//                    catch (Exception e) {
//                        int defaultItemQty = 0;
//                        OrderListItemEntry item = new OrderListItemEntry(itemName, defaultItemQty);
//                        itemList.add(item);
//                        String path = String.format("%s/%s/%s/%s/%s", NODE_IMG0, storeName, NODE_IMG2_ITEMS, itemName, NODE_IMG4);
//                        imagesList.add(new StoreStatusImages(item, path, url, presenter));
//                    }
//                }
//                Collections.sort(itemList);
//                OrderListStoreEntry store = new OrderListStoreEntry(storeName, storeComplete, itemList);
//                storeList.add(store);
//                Collections.sort(storeList);
//                String storeLogoPath = String.format("%s/%s/%s", NODE_IMG0, storeName, NODE_IMG2_LOGO);
//                imagesList.add(new StoreStatusImages(store, storeLogoPath, url, presenter));
//                presenter.notifyAdapterDataChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String storeName = snapshot.getKey();
//                boolean storeComplete = Boolean.TRUE.equals(snapshot.child(NODE4_COMPLETE).getValue(Boolean.class));
//                List<OrderListItemEntry> itemList = new ArrayList<>();
//                for(DataSnapshot dsKeyIsItemName : snapshot.child(NODE4_ITEMS).getChildren()) {
//                    String itemName = dsKeyIsItemName.getKey();
//                    try {
//                        int itemQty = Objects.requireNonNull(dsKeyIsItemName.getValue(Integer.class));
//                        itemList.add(new OrderListItemEntry(itemName, itemQty));
//                    }
//                    catch (Exception e) {
//                        int defaultItemQty = 0;
//                        itemList.add(new OrderListItemEntry(itemName, defaultItemQty));
//                    }
//                }
//                Collections.sort(itemList);
//                if(!storeList.contains(new OrderListStoreEntry(storeName))) {
//                    storeList.add(new OrderListStoreEntry(storeName));
//                    Collections.sort(storeList);
//                }
//                int idxOfOld = storeList.indexOf(new OrderListStoreEntry(storeName));
//                storeList.get(idxOfOld).setComplete(storeComplete);
//                storeList.get(idxOfOld).setItemList(itemList);
//                presenter.notifyAdapterDataChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                String storeName = snapshot.getKey();
//                storeList.remove(new OrderListStoreEntry(storeName));
//                presenter.notifyAdapterDataChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                destroyEventListener();
//            }
//        });
    }

    @Override
    public void destroyEventListener() {
        if (listener != null) {
            queryStores.removeEventListener(listener);
        }
        for (StoreStatusImages image : imagesList) {
            image.removeListener();
        }
        imagesList.clear();
    }

    @Override
    public List<OrderListStoreEntry> getDataList() {
        return storeList;
    }
}
