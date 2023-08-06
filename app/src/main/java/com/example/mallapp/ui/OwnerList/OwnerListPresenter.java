package com.example.mallapp.ui.OwnerList;

import java.util.List;

public class OwnerListPresenter {

    private final OwnerListFragment fragment;
    private final OwnerListModel model;
    private OwnerListRVAdapter adapter;

    public OwnerListPresenter(OwnerListFragment fragment) {
        this.fragment = fragment;
        model = new OwnerListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<OwnerListEntry> items) {
        adapter = new OwnerListRVAdapter(fragment.getContext(), items, this);
        fragment.setAdapter(adapter);
    }

    public String getItem(int index){
        return model.getItemsList().get(index).getItemName();
    }

    public String getDescription(int position) {return model.getItemsList().get(position).getDescription();}

    public String getitemBrand(int index){return model.getItemsList().get(index).getBrand();}

    public String getLogo(int index){return model.getItemsList().get(index).getLogoURL();}

}
