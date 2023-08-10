package com.example.cscb07project.ui.orders.OrderList.StoreStatusList;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cscb07project.R;
import com.example.cscb07project.ui.cart.CartEntry;
import com.example.cscb07project.ui.orders.OrderList.DataHolder.ABOrderImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class StoreStatusImages {

    private final ABOrderImage dataHolder;
    private final DatabaseReference queryImage;
    private final ValueEventListener listener;
    private final IFStoreStatusPresenter presenter;

    public StoreStatusImages(ABOrderImage dataHolder, String pathToImage, String databaseURL, IFStoreStatusPresenter presenter) {
        this.dataHolder = dataHolder;
        this.presenter = presenter;
        FirebaseDatabase db = FirebaseDatabase.getInstance(databaseURL);
        queryImage = db.getReference(pathToImage);
        listener = queryImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imgURL = snapshot.getValue(String.class);
                if (imgURL == null || imgURL.isEmpty()) {
                    updateImageURL(imgURL);
                    return;
                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference fileRef = storageRef.child(imgURL);
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        updateImageURL(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("SLM.java", "Error getting download URL: " + e.getMessage());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("mine", "Err listening: " + error);
                removeListener();
            }
        });
    }

    public void updateImageURL(String imageURL) {
        dataHolder.setImageURL(imageURL);
        presenter.notifyAdapterDataChanged();
    }

    public void removeListener() {
        if (listener != null) {
            queryImage.removeEventListener(listener);
        }
    }

}
