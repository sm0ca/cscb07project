package com.example.cscb07project.ui.orders.OwnerOrder;

import com.example.cscb07project.ui.shop.ItemList.ItemListEntry;

public class OwnerOrderEntry extends ItemListEntry {
    private String orderNumber;
    private String date;
    private boolean status;
    private int qty;
    private boolean isVisible;

    public OwnerOrderEntry(String orderNumber, String date, Boolean status){
        this.orderNumber = orderNumber;
        this.date = date;
        this.status = status;
        this.isVisible = true;
    }

    public OwnerOrderEntry(String itemName, String brand, String imgURL, double price, int modifierIcon, int qty, boolean visible) {
        super.itemName = itemName;
        super.brand = brand;
        super.logoURL = imgURL;
        super.price = price;
        super.modifiericon = modifierIcon;
        this.qty = qty;
        this.isVisible = visible;
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
