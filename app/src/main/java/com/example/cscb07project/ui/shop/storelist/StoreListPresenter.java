package com.example.cscb07project.ui.shop.storelist;

import com.example.cscb07project.ui.tools.IFNotifyAdapterService;

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
        model.destroyEventListener();
        model.createEventListener();
    }

    @Override
    public void onDestroy() {
        model.destroyEventListener();
    }

    @Override
    public String getStoreNameAtPos(int position) {
        return model.getDataList().get(position).getStoreName();
    }

    @Override
    public void onBindViewHolderAtPos(StoreList_RVAdapter.StoreList_VH holder, int position) {
        String storeName = model.getDataList().get(position).getStoreName();
        String storeLogo = model.getDataList().get(position).getLogo();
        holder.getStoreNameTV().setText(storeName);
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
