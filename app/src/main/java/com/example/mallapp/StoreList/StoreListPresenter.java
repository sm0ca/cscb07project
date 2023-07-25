package com.example.mallapp.StoreList;

public class StoreListPresenter {

    private final StoreListFragment fragment;
    private final StoreListModel model;
    private final StoreList_RVAdapter adapter;

    public StoreListPresenter(StoreListFragment fragment) {
        this.fragment = fragment;
        model = new StoreListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        adapter = new StoreList_RVAdapter(fragment.getContext(), model.getStores());
        fragment.setAdapter(adapter);
        model.createEventListener();
    }

    public void notifyAdapterItemInserted(int position) {
        adapter.notifyItemInserted(position);
    }

    public void notifyAdapterItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    public void notifyAdapterItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    public void notifyAdapterItemMoved(int from, int to) {
        adapter.notifyItemMoved(from, to);
    }
}
