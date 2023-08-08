package com.example.mallapp.ui.orders.OrderList.DataHolder;

public class OrderListItemEntry extends ABOrderImage implements Comparable<OrderListItemEntry> {

    private final String itemName;
    private final int itemQty;

    public OrderListItemEntry(String itemName, int itemQty) {
        this.itemName = itemName;
        this.itemQty = itemQty;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    @Override
    public int compareTo(OrderListItemEntry orderListItemEntry) {
        return itemName.compareTo(orderListItemEntry.itemName);
    }
}
