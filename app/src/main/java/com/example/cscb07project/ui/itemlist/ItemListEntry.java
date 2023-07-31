package com.example.cscb07project.ui.itemlist;

public class ItemListEntry {
    private String itemName;
    private String brand;
    private String imgURL;
    private double price;
//    private int modifierIcon;

    public ItemListEntry() {}

    public ItemListEntry(String itemName, String brand, String imgURL, double price, int modifierIcon) {
        this.itemName = itemName;
        this.brand = brand;
        this.imgURL = imgURL;
        this.price = price;
//        this.modifierIcon = modifierIcon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public int getModifierIcon() {
//        return modifierIcon;
//    }

//    public void setModifierIcon(int modifierIcon) {
//        this.modifierIcon = modifierIcon;
//    }
}
