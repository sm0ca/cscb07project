package com.example.mallapp.ItemList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallapp.R;

import java.util.List;

public class ItemListRVAdapter extends RecyclerView.Adapter<ItemListViewHolder> {

    private final Context context;
    private final List<ItemListEntry> itemsList;

    public ItemListRVAdapter(Context context, List<ItemListEntry> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        holder.getItemName().setText(itemsList.get(position).getItemName());
        holder.getItemPrice().setText(String.valueOf(itemsList.get(position).getPrice()));
        holder.getItemBrand().setText(itemsList.get(position).getBrand());

        if (itemsList.get(position).getLogoURL() != null && !itemsList.get(position).getLogoURL().isEmpty()) {
            Glide.with(context).load(itemsList.get(position).getLogoURL()).into(holder.getItemLogo());
        } else {
            holder.getItemLogo().setImageResource(R.drawable.placeholder_store_icon);
        }

        holder.getItemLogo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent itemListIntent = new Intent(context, ItemListFragment.class);
                context.startActivity(itemListIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
