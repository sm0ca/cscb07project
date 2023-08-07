package com.example.cscb07project.ui.orders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    private final TextView itemName;
    private final TextView itemBrand;
    private final TextView itemPrice;
    private final ImageView itemImg;
    private final ImageView itemModifier;
    private final View headerLayout;
    private final View contentLayout;
    private final TextView orderNumber;
    private final TextView date;
    private final TextView status;
    private final Button completeButton;


    public OrderViewHolder(@NonNull View itemView) {
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

    public Button getCompleteButton() {
        return completeButton;
    }
}
