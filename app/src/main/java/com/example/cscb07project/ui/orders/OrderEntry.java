package com.example.cscb07project.ui.orders;

import com.example.cscb07project.ui.itemlist.ItemListEntry;

public class OrderEntry extends ItemListEntry {
    private String orderNumber;
    private String date;
    private boolean status;
    private int qty;
    private boolean isVisible;

    public OrderEntry(String orderNumber, String date, Boolean status){
        this.orderNumber = orderNumber;
        this.date = date;
        this.status = status;
        this.isVisible = true;
    }

    public OrderEntry(String itemName, String brand, String imgURL, double price, int modifierIcon, int qty) {
        super(itemName, brand, imgURL, price, modifierIcon);
        this.qty = qty;
        this.isVisible = false;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDate() {
        return date;
    }

    public boolean isStatus() {
        return status;
    }

    public int getQty() {
        return qty;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
