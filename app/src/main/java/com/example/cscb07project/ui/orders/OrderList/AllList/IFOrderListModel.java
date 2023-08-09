package com.example.cscb07project.ui.orders.OrderList.AllList;

import com.example.cscb07project.ui.orders.OrderList.DataHolder.OrderListEntry;

import java.util.List;

interface IFOrderListModel {
    void createEventListener();
    void destroyEventListener();
    List<OrderListEntry> getDataList();
}
