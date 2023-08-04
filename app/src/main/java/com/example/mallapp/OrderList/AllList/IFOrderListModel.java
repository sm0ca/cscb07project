package com.example.mallapp.OrderList.AllList;

import com.example.mallapp.OrderList.DataHolder.OrderListEntry;

import java.util.List;

interface IFOrderListModel {
    void createEventListener();
    void destroyEventListener();
    List<OrderListEntry> getDataList();
}
