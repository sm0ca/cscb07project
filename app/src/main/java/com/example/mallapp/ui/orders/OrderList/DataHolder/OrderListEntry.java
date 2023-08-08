package com.example.mallapp.ui.orders.OrderList.DataHolder;

import androidx.annotation.NonNull;

import java.util.Objects;

public class OrderListEntry implements Comparable<OrderListEntry> {

    private final String orderId;
    private String address;
    private boolean allComplete;
    private String fullName;
    private String time;

    public OrderListEntry(String orderId) {
        this.orderId = orderId;
    }

    public OrderListEntry(String orderId, String address, boolean allComplete, String fullName, String time) {
        this.orderId = orderId;
        this.address = address;
        this.allComplete = allComplete;
        this.time = time;
        this.fullName = fullName;
    }


    @Override
    public int compareTo(OrderListEntry orderListEntry) {
        if(this.equals(orderListEntry)) {
            return 0;
        }
        return this.orderId.compareTo(orderListEntry.orderId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderListEntry that = (OrderListEntry) o;
        return orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAllComplete() {
        return allComplete;
    }

    public void setAllComplete(boolean allComplete) {
        this.allComplete = allComplete;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderListEntry{" +
                "orderId=" + orderId +
                ", address='" + address + '\'' +
                ", allComplete=" + allComplete +
                ", fullName='" + fullName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
