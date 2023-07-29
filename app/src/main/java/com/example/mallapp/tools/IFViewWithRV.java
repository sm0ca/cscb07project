package com.example.mallapp.tools;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface IFViewWithRV<VH extends RecyclerView.ViewHolder> {
    int getRVLayoutResource();
    Context getViewContext();
    void setAdapter(RecyclerView.Adapter<VH> adapter);
}
