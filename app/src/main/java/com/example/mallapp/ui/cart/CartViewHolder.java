package com.example.mallapp.ui.cart;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07project.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class CartViewHolder extends RecyclerView.ViewHolder {
    private final MaterialCardView cardView;
    private final TextView storeName;
    private final TextView itemName;
    private final TextView itemBrand;
    private final TextView itemPrice;
    private final ImageView itemImg;
    private final MaterialButton itemModifier;
    private final View headerLayout;
    private final View contentLayout;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.item_entry_name);
        itemBrand = itemView.findViewById(R.id.item_entry_brand);
        itemPrice = itemView.findViewById(R.id.item_entry_price);
        itemImg = itemView.findViewById(R.id.item_entry_img);
        itemModifier = itemView.findViewById(R.id.item_entry_modifier);
        storeName = itemView.findViewById(R.id.item_entry_store);
        headerLayout = itemView.findViewById(R.id.item_header_layout);
        contentLayout = itemView.findViewById(R.id.item_content_layout);
        cardView = itemView.findViewById(R.id.item_entry_card);
    }

    public TextView getItemName() {
        return itemName;
    }

    public TextView getItemBrand() {
        return itemBrand;
    }

    public TextView getItemPrice() {
        return itemPrice;
    }

    public ImageView getItemImg() {
        return itemImg;
    }

    public MaterialButton getItemModifier() {
        return itemModifier;
    }

    public TextView getStoreName() {
        return storeName;
    }

    public View getHeaderLayout() {
        return headerLayout;
    }

    public View getContentLayout() {
        return contentLayout;
    }

    public MaterialCardView getCardView() {
        return cardView;
    }
}
