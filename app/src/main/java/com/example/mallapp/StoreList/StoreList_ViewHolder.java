package com.example.mallapp.StoreList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallapp.R;

public class StoreList_ViewHolder extends RecyclerView.ViewHolder {

    private final TextView storeName;
    private final ImageView storeLogo;

    public StoreList_ViewHolder(@NonNull View itemView) {
        super(itemView);
        storeName = itemView.findViewById(R.id.store_name);
        storeLogo = itemView.findViewById(R.id.store_logo);
    }

    public TextView getStoreName() {
        return storeName;
    }

    public ImageView getStoreLogo() {
        return storeLogo;
    }
}