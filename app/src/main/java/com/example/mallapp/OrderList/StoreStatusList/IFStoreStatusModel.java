package com.example.mallapp.OrderList.StoreStatusList;

import com.example.mallapp.OrderList.DataHolder.OrderListStoreEntry;

import java.util.List;

public interface IFStoreStatusModel {
    void createEventListener();
    void destroyEventListener();
    List<OrderListStoreEntry> getDataList();
}
