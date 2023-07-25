package com.example.mallapp.StoreList;

import com.example.mallapp.tools.IFNotifyAdapterService;

public class StoreListPresenter {

    private final StoreListFragment fragment;
    private final StoreListModel model;
    private final StoreList_RVAdapter adapter;

    public StoreListPresenter(StoreListFragment fragment) {
        this.fragment = fragment;
        model = new StoreListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        adapter = new StoreList_RVAdapter(fragment.getContext(), model.getStores());
    }

    public StoreList_RVAdapter getAdapter() {
        return adapter;
    }

    public void createEventListener() {
        model.createEventListener();
    }

    public void notifyAdapter(IFNotifyAdapterService notifier) {
        notifier.notifyAdapter(adapter);
    }

}
