package com.example.cscb07project.ui.shop.storelist;

import com.example.cscb07project.ui.tools.IFPresenterWithRV;

public interface IFStoreListPresenter extends IFPresenterWithRV<StoreList_RVAdapter.StoreList_VH> {
    void onViewCreated();
    void onDestroy();
    String getStoreNameAtPos(int position);
}
