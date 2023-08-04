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

    private static final int FIRST_IDX = 0;
    private static final String LOGO_NODE_NAME = "logo";
    private static final String OWNER = "owner";
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
        Log.d("mine", "Started listener");
        if(listener != null) {
            return;
        }
        listener = queryNames.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                String logoURL = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                String owner = snapshot.child(OWNER).getValue(String.class);
                StoreListEntry newEntry = new StoreListEntry(storeName, owner, logoURL);
                if(previousChildName == null) {
                    stores.add(FIRST_IDX, newEntry);
                    presenter.notifyAdapter(new NotifyAdapter.Inserted(FIRST_IDX));
                }
                else {
                    int idxToInsert = stores.indexOf(new StoreListEntry(previousChildName)) + 1;
                    stores.add(idxToInsert, newEntry);
                    presenter.notifyAdapter(new NotifyAdapter.Inserted(idxToInsert));
                }
//                Log.d("SLM.java", "Added");
//                printCurrentList();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String updatedStoreName = snapshot.getKey();
                String updatedLogo = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                String updatedOwner = snapshot.child(OWNER).getValue(String.class);
                if(previousChildName == null) {
                    stores.get(FIRST_IDX).setStoreName(updatedStoreName);
                    stores.get(FIRST_IDX).setLogo(updatedLogo);
                    stores.get(FIRST_IDX).setOwnerName(updatedOwner);
                    presenter.notifyAdapter(new NotifyAdapter.Changed(FIRST_IDX));
                }
                else {
                    StoreListEntry prev = new StoreListEntry(previousChildName);
                    int idxToUpdate = stores.indexOf(prev) + 1;
                    stores.get(idxToUpdate).setStoreName(updatedStoreName);
                    stores.get(idxToUpdate).setLogo(updatedLogo);
                    stores.get(idxToUpdate).setOwnerName(updatedOwner);
                    presenter.notifyAdapter(new NotifyAdapter.Changed(idxToUpdate));
                }
//                Log.d("SLM.java", "Changed");
//                printCurrentList();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String removedStoreName = snapshot.getKey();
                int idxRemoved = stores.indexOf(new StoreListEntry(removedStoreName));
                stores.remove(new StoreListEntry(removedStoreName));
                presenter.notifyAdapter(new NotifyAdapter.Removed(idxRemoved));
//                Log.d("SLM.java", "Removed");
//                printCurrentList();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String movedName = snapshot.getKey();
                String movedLogo = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                String movedOwner = snapshot.child(OWNER).getValue(String.class);
                StoreListEntry movedEntry = new StoreListEntry(movedName, movedOwner, movedLogo);
                int idxInitial = stores.indexOf(movedEntry);
                stores.remove(movedEntry);
                if(previousChildName == null) {
                    stores.add(FIRST_IDX, movedEntry);
                    presenter.notifyAdapter(new NotifyAdapter.Moved(idxInitial, FIRST_IDX));
                }
                else {
                    int idxToInsert = stores.indexOf(new StoreListEntry(previousChildName)) + 1;
                    stores.add(idxToInsert, movedEntry);
                    presenter.notifyAdapter(new NotifyAdapter.Moved(idxInitial, idxToInsert));
                }
//                Log.d("SLM.java", "Moved");
//                printCurrentList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("mine", "Err listening to store names");
                destroyEventListener();
            }
        });
    }


    public void destroyEventListener() {
        if(listener != null) {
            Log.d("mine", "destroyed store listener");
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
