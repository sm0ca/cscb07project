package com.example.mallapp.ItemList;

import java.util.List;

public class ItemListPresenter {

    private final ItemListFragment fragment;
    private final ItemListModel model;
    private ItemListRVAdapter adapter;

    public ItemListPresenter(ItemListFragment fragment) {
        this.fragment = fragment;
        model = new ItemListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<ItemListEntry> items) {
        adapter = new ItemListRVAdapter(fragment.getContext(), items);
        fragment.setAdapter(adapter);
    }

}
