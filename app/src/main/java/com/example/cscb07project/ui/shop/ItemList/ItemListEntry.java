package com.example.cscb07project.ui.shop.ItemList;
public class ItemListEntry {
    public String itemName;
    public String logoURL;
    public double price;

    public String brand;
    public String description;

    public int modifiericon;

    public ItemListEntry() {
    }

    public ItemListEntry(String itemName, String logoURL, double price, String brand, String description) {
        this.itemName = itemName;
        this.logoURL = logoURL;
        this.price = price;
        this.brand = brand;
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemBrand(String brand){this.brand = brand;}

    public String getBrand(){return brand;}

    public String getImgURL() {
        return logoURL;
    }

    public String getDescription(){return description;}
}
