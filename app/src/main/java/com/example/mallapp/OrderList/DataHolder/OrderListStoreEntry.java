package com.example.mallapp.OrderList.DataHolder;

import java.util.List;
import java.util.Objects;

public class OrderListStoreEntry implements Comparable<OrderListStoreEntry> {

    private final String storeName;
    private boolean complete;
    private List<OrderListItemEntry> itemList;

    public OrderListStoreEntry(String storeName) {
        this.storeName = storeName;
    }

    public OrderListStoreEntry(String storeName, boolean complete, List<OrderListItemEntry> itemList) {
        this.storeName = storeName;
        this.complete = complete;
        this.itemList = itemList;
    }

    public String getStoreName() {
        return storeName;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }


    public List<OrderListItemEntry> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderListItemEntry> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderListStoreEntry that = (OrderListStoreEntry) o;
        return storeName.equals(that.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeName);
    }

    @Override
    public int compareTo(OrderListStoreEntry orderListStoreEntry) {
        return storeName.compareTo(orderListStoreEntry.storeName);
    }
}
