package com.example.cscb07project.ui.cart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07project.R;

import org.w3c.dom.Text;

public class CartViewHolder extends RecyclerView.ViewHolder {
    private final TextView storeName;
    private final TextView itemName;
    private final TextView itemBrand;
    private final TextView itemPrice;
    private final ImageView itemImg;
    private final ImageView itemModifier;
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
        headerLayout = itemView.findViewById(R.id.cart_header_layout);
        contentLayout = itemView.findViewById(R.id.item_content_layout);
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

    public ImageView getItemModifier() {
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
}
