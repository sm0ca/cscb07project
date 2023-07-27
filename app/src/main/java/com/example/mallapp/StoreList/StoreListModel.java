package com.example.mallapp.StoreList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mallapp.tools.NotifyAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StoreListModel implements IFStoreListModel {

    private static final String LOGO_NODE_NAME = "logo";
    private final IFStoreListPresenter presenter;
    private final DatabaseReference queryNames;
    private ChildEventListener listener;
    private final List<StoreListEntry> stores;

    public StoreListModel(IFStoreListPresenter presenter, String url) {
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
                    presenter.notifyAdapter(new NotifyAdapter.Inserted(0));
                }
                else {
                    int idxToInsert = stores.indexOf(new StoreListEntry(previousChildName)) + 1;
                    stores.add(idxToInsert, newEntry);
                    presenter.notifyAdapter(new NotifyAdapter.Inserted(idxToInsert));
                }
                printCurrentList();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String updatedStoreName = snapshot.getKey();
                String updatedLogo = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                if(previousChildName == null) {
                    stores.get(0).setStoreName(updatedStoreName);
                    stores.get(0).setLogo(updatedLogo);
                    presenter.notifyAdapter(new NotifyAdapter.Changed(0));
                }
                else {
                    StoreListEntry prev = new StoreListEntry(previousChildName);
                    int idxToUpdate = stores.indexOf(prev) + 1;
                    stores.get(idxToUpdate).setStoreName(updatedStoreName);
                    stores.get(idxToUpdate).setLogo(updatedLogo);
                    presenter.notifyAdapter(new NotifyAdapter.Changed(idxToUpdate));
                }
                printCurrentList();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String removedStoreName = snapshot.getKey();
                int idxRemoved = stores.indexOf(new StoreListEntry(removedStoreName));
                stores.remove(new StoreListEntry(removedStoreName));
                presenter.notifyAdapter(new NotifyAdapter.Removed(idxRemoved));
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
                    presenter.notifyAdapter(new NotifyAdapter.Moved(idxInitial, 0));
                }
                else {
                    int idxToInsert = stores.indexOf(new StoreListEntry(previousChildName)) + 1;
                    stores.add(idxToInsert, movedEntry);
                    presenter.notifyAdapter(new NotifyAdapter.Moved(idxInitial, idxToInsert));
                }
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

    @Override
    public List<StoreListEntry> getDataList() {
        return stores;
    }

    @Override
    public int getSizeOfDataList() {
        return stores.size();
    }
}
