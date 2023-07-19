package com.example.mallapp.StoreList;

import com.example.mallapp.MainActivity;

public class StoreListPresenter {

    private final MainActivity view;
    private final StoreListModel model;

    public StoreListPresenter(MainActivity view) {
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
