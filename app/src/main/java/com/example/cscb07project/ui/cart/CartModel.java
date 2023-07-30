package com.example.cscb07project.ui.cart;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.ui.itemlist.ItemListEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartModel {
    private final CartPresenter presenter;
    private final DatabaseReference queryNames;
    private ChildEventListener listener;

    private List<ItemListEntry> itemList;

    public CartModel(CartPresenter presenter, String url) {
        this.presenter = presenter;

    }
}
