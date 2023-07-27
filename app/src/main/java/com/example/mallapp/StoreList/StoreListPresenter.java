package com.example.mallapp.StoreList;

import com.example.mallapp.tools.IFNotifyAdapterService;
import com.example.mallapp.tools.IFPresenterWithRV;

public class StoreListPresenter implements IFStoreListPresenter, IFPresenterWithRV<StoreList_ViewHolder> {

    private final IFStoreListView view;
    private final IFStoreListModel model;
    private final StoreList_RVAdapter adapter;

    public StoreListPresenter(IFStoreListView view) {
        this.view = view;
        model = new StoreListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        adapter = new StoreList_RVAdapter(this, view);
    }

    public void notifyAdapter(IFNotifyAdapterService notifier) {
        notifier.notifyAdapter(adapter);
    }

    @Override
    public void onViewCreated() {
        view.setAdapter(adapter);
        model.createEventListener();
    }

    @Override
    public void onDestroy() {
        model.destroyEventListener();
    }

    @Override
    public void onBindViewHolderAtPos(StoreList_ViewHolder holder, int position) {
        String storeName = model.getDataList().get(position).getStoreName();
        String storeLogo = model.getDataList().get(position).getLogo();
        holder.getStoreName().setText(storeName);
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
