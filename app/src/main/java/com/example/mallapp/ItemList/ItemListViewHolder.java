package com.example.mallapp.ItemList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallapp.R;

public class ItemListViewHolder extends RecyclerView.ViewHolder {

    private final TextView itemName;
    private final TextView itemPrice;
    private final ImageView itemLogo;

    private final TextView itemBrand;

    public ItemListViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.item_name);
        itemPrice = itemView.findViewById(R.id.item_price);
        itemLogo = itemView.findViewById(R.id.item_logo);
        itemBrand = itemView.findViewById(R.id.item_brand);
    }

    public TextView getItemName() {
        return itemName;
    }

    public TextView getItemPrice() {
        return itemPrice;
    }

    public ImageView getItemLogo() {
        return itemLogo;
    }

    public TextView getItemBrand(){return itemBrand;}
}
