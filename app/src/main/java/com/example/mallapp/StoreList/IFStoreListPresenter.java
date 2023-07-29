package com.example.mallapp.StoreList;

import com.example.mallapp.tools.IFPresenterWithRV;

public interface IFStoreListPresenter extends IFPresenterWithRV<StoreList_RVAdapter.StoreList_VH> {
    void onViewCreated();
    void onDestroy();
}
