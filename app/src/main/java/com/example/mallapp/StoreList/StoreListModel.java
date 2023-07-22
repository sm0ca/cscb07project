package com.example.mallapp.StoreList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreListModel {

    private final StoreListPresenter presenter;
    private final DatabaseReference queryNames;
    private ChildEventListener listener;
    private final List<String> storeNames;

    public StoreListModel(StoreListPresenter presenter, String url) {
        this.presenter = presenter;
        storeNames = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
        queryNames = db.getReference().child("stores");
    }

    public void createEventListener() {
        Log.d("SLM.java", "Started listener");
        listener = queryNames.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                storeNames.add(snapshot.getKey());
                Log.d("SLM.java", "Added: " + snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String newStoreName = snapshot.getKey();
                if(previousChildName == null) {
                    storeNames.set(0, newStoreName);
                }
                else {
                    int prevChildIdx = storeNames.indexOf(previousChildName);
                    storeNames.set(prevChildIdx + 1, newStoreName);
                }
                Log.d("SLM.java", "Changed: " + newStoreName);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                storeNames.remove(snapshot.getKey());
                Log.d("SLM.java", "Removed: " + snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String movedName = snapshot.getKey();
                storeNames.remove(movedName);
                if(previousChildName == null) {
                    storeNames.add(0, movedName);
                }
                else {
                    int prevChildIdx = storeNames.indexOf(previousChildName);
                    storeNames.add(prevChildIdx + 1, movedName);
                }
                Log.d("SLM.java", "Moved: " + snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SLM.java", "Err listening to store names");
                destroyEventListener();
            }
        });
    }

    public void destroyEventListener() {
        if(listener != null) {
            queryNames.removeEventListener(listener);
        }
    }

    public void printList() {
        int i = 0;
        for(String str : storeNames) {
            Log.d("SLModel.java List", "idx " + i + ": " + str);
            i++;
        }
    }

    public void printDatabase() {
        Log.d("SLM.java", "Printing data");
        queryNames.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String key = ds.getKey();
                    Log.d("SLM.java", "key: " + key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SLM.java", "Err printing data");
            }
        });
    }
}
