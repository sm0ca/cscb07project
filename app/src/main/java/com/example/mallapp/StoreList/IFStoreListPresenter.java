package com.example.mallapp.StoreList;

import com.example.mallapp.tools.IFPresenterWithRV;

public interface IFStoreListPresenter extends IFPresenterWithRV<StoreList_ViewHolder> {
    void onViewCreated();
    void onDestroy();
}
