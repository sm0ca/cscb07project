package com.example.mallapp.StoreList;

import androidx.annotation.NonNull;

import java.util.Objects;

public class StoreListEntry implements Comparable<StoreListEntry> {

    private String storeName;
    private String logo;
    private String owner;

    public StoreListEntry(String storeName) {
        this.storeName = storeName;
    }

    public StoreListEntry(String storeName, String imageURL, String owner) {
        this.storeName = storeName;
        this.logo = imageURL;
        this.owner = owner;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOwner(){return owner;}

    public void setOwner(String owner) {this.owner = owner;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreListEntry that = (StoreListEntry) o;
        return storeName.equals(that.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeName);
    }

    @NonNull
    @Override
    public String toString() {
        return storeName;
    }

    @Override
    public int compareTo(StoreListEntry storeListEntry) {
        return storeName.compareTo(storeListEntry.getStoreName());
    }

}
