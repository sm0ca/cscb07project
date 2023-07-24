package com.example.mallapp.StoreList;

import java.util.List;

public class StoreListPresenter {

    private final StoreListFragment fragment;
    private final StoreListModel model;
    private StoreList_RVAdapter adapter;

    public StoreListPresenter(StoreListFragment fragment) {
        this.fragment = fragment;
        model = new StoreListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<StoreListEntry> stores) {
        adapter = new StoreList_RVAdapter(fragment.getContext(), stores);
        fragment.setAdapter(adapter);
    }

}
