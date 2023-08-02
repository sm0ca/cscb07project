package com.example.mallapp.OwnerList;
public class OwnerListEntry {
    private String itemName;
    private String logoURL;
    private double price;

    private String brand;

    public OwnerListEntry() {
    }

    public OwnerListEntry(String itemName, String logoURL, double price, String brand) {
        this.itemName = itemName;
        this.logoURL = logoURL;
        this.price = price;
        this.brand = brand;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLogoURL() {
        return logoURL;
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
}
