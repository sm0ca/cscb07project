package com.example.cscb07project.ui.tools;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface IFViewWithRV<VH extends RecyclerView.ViewHolder> {
    int getRVLayoutResource();
    Context getViewContext();
    void setAdapter(RecyclerView.Adapter<VH> adapter);
}
