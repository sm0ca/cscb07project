package com.example.mallapp.StoreList;

import java.util.List;

interface IFStoreListModel {
    void createEventListener();
    void destroyEventListener();
    List<StoreListEntry> getDataList();
    int getSizeOfDataList();
}
