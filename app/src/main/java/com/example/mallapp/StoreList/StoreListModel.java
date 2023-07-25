package com.example.mallapp.StoreList;

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

public class StoreListModel {

    private static final String LOGO_NODE_NAME = "logo";
    private final StoreListPresenter presenter;
    private final DatabaseReference queryNames;
    private ChildEventListener listener;

    public List<StoreListEntry> getStores() {
        return stores;
    }

    private List<StoreListEntry> stores;

    public StoreListModel(StoreListPresenter presenter, String url) {
        this.presenter = presenter;
        stores = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
        queryNames = db.getReference().child("stores");
    }

    public void createEventListener() {
        Log.d("SLM.java", "Started listener");
        listener = queryNames.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                String logoURL = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                StoreListEntry newEntry = new StoreListEntry(storeName, logoURL);
                if(previousChildName == null) {
                    stores.add(0, newEntry);
                    presenter.notifyAdapterItemInserted(0);
                }
                else {
                    int idxToInsert = stores.indexOf(new StoreListEntry(previousChildName)) + 1;
                    stores.add(idxToInsert, newEntry);
                    presenter.notifyAdapterItemInserted(idxToInsert);
                }
                //presenter.setAdapter(stores);
                Log.d("SLM.java", "Prev: " + previousChildName);
                Log.d("SLM.java", "Added: " + snapshot.getKey());
                printCurrentList();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String updatedStoreName = snapshot.getKey();
                String updatedLogo = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                if(previousChildName == null) {
                    stores.get(0).setStoreName(updatedStoreName);
                    stores.get(0).setLogo(updatedLogo);
                    presenter.notifyAdapterItemChanged(0);
                }
                else {
                    StoreListEntry prev = new StoreListEntry(previousChildName);
                    int idxToUpdate = stores.indexOf(prev) + 1;
                    stores.get(idxToUpdate).setStoreName(updatedStoreName);
                    stores.get(idxToUpdate).setLogo(updatedLogo);
                    presenter.notifyAdapterItemChanged(idxToUpdate);
                }
                //presenter.setAdapter(stores);
                Log.d("SLM.java", "Prev: " + previousChildName);
                Log.d("SLM.java", "Changed: " + updatedStoreName);
                printCurrentList();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String removedStoreName = snapshot.getKey();
                int idxRemoved = stores.indexOf(new StoreListEntry(removedStoreName));
                stores.remove(new StoreListEntry(removedStoreName));
                //setAdapter(stores);
                presenter.notifyAdapterItemRemoved(idxRemoved);
                Log.d("SLM.java", "Removed: " + snapshot.getKey());
                printCurrentList();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String movedName = snapshot.getKey();
                String movedLogo = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                StoreListEntry movedEntry = new StoreListEntry(movedName, movedLogo);
                int idxInitial = stores.indexOf(movedEntry);
                stores.remove(movedEntry);
                if(previousChildName == null) {
                    stores.add(0, movedEntry);
                    presenter.notifyAdapterItemMoved(idxInitial, 0);
                    //presenter.setAdapter(stores);
                }
                else {
                    int idxToInsert = stores.indexOf(new StoreListEntry(previousChildName)) + 1;
                    stores.add(idxToInsert, movedEntry);
                    presenter.notifyAdapterItemMoved(idxInitial, idxToInsert);
                    //presenter.setAdapter(stores);
                }
                Log.d("SLM.java", "Prev: " + previousChildName);
                Log.d("SLM.java", "Moved: " + snapshot.getKey());
                printCurrentList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SLM.java", "Err listening to store names");
                destroyEventListener();
            }
        });
    }

    private void printCurrentList() {
        Log.d("SLM.java", "========= Printing List =========");

        int i = 0;
        for(StoreListEntry sle : stores) {
            Log.d("SLM.java", "idx " + i + ": " + sle.getStoreName());
            i++;
        }
        Log.d("SLM.java", "=================================");
    }

    public void destroyEventListener() {
        if(listener != null) {
            queryNames.removeEventListener(listener);
        }
    }

}
