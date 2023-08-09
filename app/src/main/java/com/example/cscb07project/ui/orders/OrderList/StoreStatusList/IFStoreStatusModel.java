package com.example.cscb07project.ui.orders.OrderList.StoreStatusList;

import com.example.cscb07project.ui.orders.OrderList.DataHolder.OrderListStoreEntry;

import java.util.List;

public interface IFStoreStatusModel {
    void createEventListener();
    void destroyEventListener();
    List<OrderListStoreEntry> getDataList();
}
