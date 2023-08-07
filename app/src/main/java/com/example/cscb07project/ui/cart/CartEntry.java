package com.example.cscb07project.ui.cart;

import com.example.cscb07project.ui.itemlist.ItemListEntry;

public class CartEntry extends ItemListEntry {
    private String storeName;
    private int qty;

    public CartEntry() {}

    public CartEntry(String storeName) {
        this.storeName = storeName;
    }

    public CartEntry(String itemName, String brand, String imgURL, double price, int modifierIcon, int qty) {
        super(itemName, brand, imgURL, price, modifierIcon);
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
