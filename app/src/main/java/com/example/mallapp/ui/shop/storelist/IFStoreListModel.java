package com.example.mallapp.ui.shop.storelist;

import java.util.List;

interface IFStoreListModel {
    void createEventListener();
    void destroyEventListener();
    List<StoreListEntry> getDataList();
    int getSizeOfDataList();
}
