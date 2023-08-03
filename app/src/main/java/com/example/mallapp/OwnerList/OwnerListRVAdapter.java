package com.example.mallapp.OwnerList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;



public class OwnerListRVAdapter extends RecyclerView.Adapter<OwnerListRVAdapter.ItemListViewHolder> {

    private final Context context;

    private final OwnerListPresenter presenter;
    private final List<OwnerListEntry> itemsList;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public OwnerListRVAdapter(Context context, List<OwnerListEntry> itemsList, OwnerListPresenter presenter) {
        this.context = context;
        this.itemsList = itemsList;
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_owner_entry, parent, false));
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

//        holder.getItemLogo().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = holder.itemView.getContext();
//                Intent itemListIntent = new Intent(context, ItemListFragment.class);
//                context.startActivity(itemListIntent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
    public class ItemListViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemName;
        private final TextView itemPrice;
        private final ImageView itemLogo;

        private final TextView itemBrand;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.order_item_name);
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
}



