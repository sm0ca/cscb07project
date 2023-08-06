package com.example.mallapp.ui.shop.storelist;

import android.content.Context;

import com.example.mallapp.ui.tools.IFViewWithRV;

public interface IFStoreListView extends IFViewWithRV<StoreList_RVAdapter.StoreList_VH> {
    void displayStoreLogo(StoreList_RVAdapter.StoreList_VH holder);
    void displayStoreLogo(StoreList_RVAdapter.StoreList_VH holder, String imageURL);
    int getRVLayoutResource();
    Context getViewContext();
}