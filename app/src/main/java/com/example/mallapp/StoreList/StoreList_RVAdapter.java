package com.example.mallapp.StoreList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallapp.R;

import java.util.List;

public class StoreList_RVAdapter extends RecyclerView.Adapter<StoreList_ViewHolder> {

    private final Context context;
    private final List<StoreListEntry> storeLists;

    public StoreList_RVAdapter(Context context, List<StoreListEntry> storeLists) {
        this.context = context;
        this.storeLists = storeLists;
    }

    @NonNull
    @Override
    public StoreList_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreList_ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_store_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreList_ViewHolder holder, int position) {
        holder.getStoreName().setText(storeLists.get(position).getStoreName());
        if(storeLists.get(position).getLogo() != null && !storeLists.get(position).getLogo().isEmpty()) {
            Glide.with(context).load(storeLists.get(position).getLogo()).into(holder.getStoreLogo());
        }
        else {
            holder.getStoreLogo().setImageResource(R.drawable.placeholder_store_icon);
        }
    }

    @Override
    public int getItemCount() {
        return storeLists.size();
    }

}
