package com.example.mallapp.StoreList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallapp.tools.IFPresenterWithRV;

public class StoreList_RVAdapter extends RecyclerView.Adapter<StoreList_ViewHolder> {

    private final IFPresenterWithRV<StoreList_ViewHolder> presenter;
    private final IFStoreListView view;

    public StoreList_RVAdapter(IFPresenterWithRV<StoreList_ViewHolder> presenter, IFStoreListView view) {
        this.presenter = presenter;
        this.view = view;
    }

    @NonNull
    @Override
    public StoreList_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = view.getViewContext();
        View viewHold = LayoutInflater.from(context).inflate(view.getRVLayoutResource(), parent, false);
        return new StoreList_ViewHolder(viewHold);
    }


    @Override
    public void onBindViewHolder(@NonNull StoreList_ViewHolder holder, int position) {
        presenter.onBindViewHolderAtPos(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getDataSize();
    }

}
