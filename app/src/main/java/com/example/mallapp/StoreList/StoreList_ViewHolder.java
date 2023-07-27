package com.example.mallapp.StoreList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallapp.R;

import org.w3c.dom.Text;

public class StoreList_ViewHolder extends RecyclerView.ViewHolder {

    private final TextView storeName;
    private final ImageView storeLogo;
    private final TextView storeOwner;

    public StoreList_ViewHolder(@NonNull View itemView) {
        super(itemView);
        storeName = itemView.findViewById(R.id.store_name);
        storeLogo = itemView.findViewById(R.id.store_logo);
        storeOwner = itemView.findViewById(R.id.owner_name);
    }



    public TextView getStoreName() {
        return storeName;
    }

    public ImageView getStoreLogo() {
        return storeLogo;
    }

    public TextView getStoreOwner(){return storeOwner;}
}