package com.example.mallapp.StoreList;

import com.example.mallapp.tools.IFNotifyAdapterService;

public class StoreListPresenter implements IFStoreListPresenter {

    private final IFStoreListView view;
    private final IFStoreListModel model;
    private final StoreList_RVAdapter adapter;

    public StoreListPresenter(IFStoreListView view) {
        this.view = view;
        model = new StoreListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        adapter = new StoreList_RVAdapter(this, view);
    }

    @Override
    public void notifyAdapter(IFNotifyAdapterService notifier) {
        notifier.notifyAdapter(adapter);
    }

    @Override
    public void onViewCreated() {
        view.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        model.createEventListener();
    }

    @Override
    public void onStop() {
        model.destroyEventListener();
    }

    @Override
    public String getStoreNameAtPos(int position) {
        return model.getDataList().get(position).getStoreName();
    }

    @Override
    public void onBindViewHolderAtPos(StoreList_RVAdapter.StoreList_VH holder, int position) {
        String storeName = model.getDataList().get(position).getStoreName();
        String ownerName = model.getDataList().get(position).getOwnerName();
        String storeLogo = model.getDataList().get(position).getLogo();
        holder.getStoreNameTV().setText(storeName);
        String ownerLabel;
        if(ownerName != null && !ownerName.isEmpty()) {
            ownerLabel = "By: " + ownerName;
        }
        else {
            ownerLabel = "By: N/A";
        }
        holder.getStoreOwnerTV().setText(ownerLabel);
        if(storeLogo != null && !storeLogo.isEmpty()) {
            view.displayStoreLogo(holder, storeLogo);
        }
        else {
            view.displayStoreLogo(holder);
        }
    }

    @Override
    public int getDataSize() {
        return model.getSizeOfDataList();
    }

}
