package com.example.mallapp.ui.tools;

import androidx.recyclerview.widget.RecyclerView;

public interface IFPresenterWithRV<VH extends RecyclerView.ViewHolder> {
    void notifyAdapter(IFNotifyAdapterService notifier);
    void onBindViewHolderAtPos(VH holder, int position);
    int getDataSize();
}
