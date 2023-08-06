package com.example.mallapp.ui.shop.storelist;

import com.example.mallapp.ui.tools.IFPresenterWithRV;

public interface IFStoreListPresenter extends IFPresenterWithRV<StoreList_RVAdapter.StoreList_VH> {
    void onViewCreated();
    void onDestroy();
    String getStoreNameAtPos(int position);
}
