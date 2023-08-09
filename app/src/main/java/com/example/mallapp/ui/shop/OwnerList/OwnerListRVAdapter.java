package com.example.mallapp.ui.shop.OwnerList;

import static com.example.mallapp.MainActivity.currentUser;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.example.mallapp.ui.shop.ItemList.ItemListPresenter;
import com.example.mallapp.ui.shop.ItemList.ItemListEntry;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.util.List;



public class OwnerListRVAdapter extends RecyclerView.Adapter<OwnerListRVAdapter.ItemListViewHolder> {

    private final Context context;

    private final OwnerListPresenter presenter;
    private final List<ItemListEntry> itemsList;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public OwnerListRVAdapter(Context context, List<ItemListEntry> itemsList, OwnerListPresenter presenter) {
        this.context = context;
        this.itemsList = itemsList;
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        holder.getItemName().setText(itemsList.get(position).getItemName());
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        holder.getItemPrice().setText(formatter.format(itemsList.get(position).getPrice()));
        holder.getItemBrand().setText(itemsList.get(position).getBrand());
        holder.getDeleteButton().setIcon(context.getDrawable(R.drawable.round_remove_36));

        if (itemsList.get(position).getImgURL() != null && !itemsList.get(position).getImgURL().isEmpty()) {

           // Glide.with(context).load(itemsList.get(position).getImgURL()).into(holder.getItemLogo());
            Glide.with(context).load(Uri.parse(itemsList.get(position).getImgURL())).into(holder.getItemLogo());
        } else {
            holder.getItemLogo().setImageResource(R.drawable.ic_launcher_background);
        }
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

        private final TextView itemDescription;

        private final MaterialButton deleteButton;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_entry_name);
            itemPrice = itemView.findViewById(R.id.item_entry_price);
            itemLogo = itemView.findViewById(R.id.item_entry_img);
            itemBrand = itemView.findViewById(R.id.item_entry_brand);
            itemDescription = itemView.findViewById(R.id.item_entry_description);
            deleteButton = itemView.findViewById(R.id.item_entry_modifier);


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    String itemname = presenter.getItem(position);
                    String username = currentUser;
                    getStoreName getStoreNameInstance = new getStoreName(username);
                    getStoreNameInstance.retrieveStoreName(new getStoreName.OnStoreNameListener() {
                        @Override
                        public void onStoreNameRetrieved(String storename) {
                            Delete_Item delete_item = new Delete_Item(storename, itemname);
                            delete_item.delete();
                            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Log.d("failed", "delete failed");
                        }
                    });
                }
            });

            itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    ItemListPresenter.item_name = presenter.getItem(position);
                    ItemListPresenter.item_description= presenter.getDescription(position);
                    ItemListPresenter.item_brand = presenter.getitemBrand(position);
                    ItemListPresenter.item_image = presenter.getLogo(position);
                    Navigation.findNavController(view).navigate(R.id.action_owner_list_to_detailed_owner_entry);
                }
            });

            itemLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    ItemListPresenter.item_name = presenter.getItem(position);
                    ItemListPresenter.item_description= presenter.getDescription(position);
                    ItemListPresenter.item_brand = presenter.getitemBrand(position);
                    ItemListPresenter.item_image = presenter.getLogo(position);
                    Navigation.findNavController(view).navigate(R.id.action_owner_list_to_detailed_owner_entry);
                }
            });

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

        public MaterialButton getDeleteButton() {
            return deleteButton;
        }

        public TextView getItemDescription(){return itemDescription;}
    }
}
