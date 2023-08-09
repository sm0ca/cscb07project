package com.example.mallapp.ui.orders.OwnerOrder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class OwnerOrderViewHolder extends RecyclerView.ViewHolder {
    private final TextView itemName;
    private final TextView itemBrand;
    private final TextView itemPrice;
    private final ImageView itemImg;
    private final MaterialButton itemModifier;
    private final View headerLayout;
    private final View contentLayout;
    private final TextView orderNumber;
    private final TextView date;
    private final TextView status;
    private final MaterialButton completeButton;

    private final MaterialCardView cardView;


    public OwnerOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.item_entry_name);
        itemBrand = itemView.findViewById(R.id.item_entry_brand);
        itemPrice = itemView.findViewById(R.id.item_entry_price);
        itemImg = itemView.findViewById(R.id.item_entry_img);
        itemModifier = itemView.findViewById(R.id.item_entry_modifier);
        headerLayout = itemView.findViewById(R.id.order_header_layout);
        contentLayout = itemView.findViewById(R.id.item_content_layout);
        orderNumber = itemView.findViewById(R.id.order_number);
        date = itemView.findViewById(R.id.date);
        status = itemView.findViewById(R.id.status);
        completeButton = itemView.findViewById(R.id.complete_button);
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

    public View getHeaderLayout() {
        return headerLayout;
    }

    public View getContentLayout() {
        return contentLayout;
    }

    public TextView getOrderNumber() {
        return orderNumber;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getStatus() {
        return status;
    }

    public MaterialButton getCompleteButton() {
        return completeButton;
    }

    public MaterialCardView getCardView() {
        return cardView;
    }
}
