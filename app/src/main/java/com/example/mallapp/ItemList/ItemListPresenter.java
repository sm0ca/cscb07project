package com.example.mallapp.ItemList;

import com.example.mallapp.MainActivity;

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
        adapter = new ItemListRVAdapter(fragment.getContext(), items, this);
        fragment.setAdapter(adapter);
    }

    public String getItem(int index){
        return model.getItemsList().get(index).getItemName();
    }

}
