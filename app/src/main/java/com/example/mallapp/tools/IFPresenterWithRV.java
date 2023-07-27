package com.example.mallapp.tools;

import androidx.recyclerview.widget.RecyclerView;

public interface IFPresenterWithRV<VH extends RecyclerView.ViewHolder> {
    void onBindViewHolderAtPos(VH holder, int position);
    void notifyAdapter(IFNotifyAdapterService notifier);
    int getDataSize();
}
