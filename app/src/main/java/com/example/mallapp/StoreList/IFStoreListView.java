package com.example.mallapp.StoreList;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface IFStoreListView {
    void setAdapter(RecyclerView.Adapter<?> adapter);
    void displayStoreLogo(StoreList_ViewHolder holder);
    void displayStoreLogo(StoreList_ViewHolder holder, String imageURL);
    int getRVLayoutResource();
    Context getViewContext();
}
