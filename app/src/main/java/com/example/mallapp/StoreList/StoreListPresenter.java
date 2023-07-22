package com.example.mallapp.StoreList;

import androidx.appcompat.app.AppCompatActivity;

public class StoreListPresenter {

    private final AppCompatActivity view;
    private final StoreListModel model;

    public StoreListPresenter(AppCompatActivity view) {
        this.view = view;
        this.model = new StoreListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
    }

    public void startListener() {
        model.createEventListener();
    }

    public void printAllData() {
        model.printDatabase();
    }

}
