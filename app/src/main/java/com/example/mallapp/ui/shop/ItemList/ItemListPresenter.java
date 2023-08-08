package com.example.mallapp.ui.shop.ItemList;

import java.util.List;

public class ItemListPresenter {

    private final ItemListFragment fragment;
    private final ItemListModel model;
    private ItemListRVAdapter adapter;

    public static String item_name;
    public static String item_brand;
    public static String item_description;
    public static String item_image;


    public ItemListPresenter(ItemListFragment fragment) {
        this.fragment = fragment;
        model = new ItemListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<ItemListEntry> items) {
        adapter = new ItemListRVAdapter(fragment.getContext(), items, this);
        fragment.setAdapter(adapter);
    }

    public String getItem(int index){
        return model.getItemsList().get(index).getItemName();
    }

    public String getDescription(int index){return model.getItemsList().get(index).getDescription();}

    public String getitemBrand(int index){return model.getItemsList().get(index).getBrand();}

    public String getLogo(int index){return model.getItemsList().get(index).getImgURL();}
}
