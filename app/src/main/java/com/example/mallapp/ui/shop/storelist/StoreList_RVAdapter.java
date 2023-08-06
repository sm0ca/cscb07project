package com.example.mallapp.ui.shop.storelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.mallapp.MainActivity;

public class StoreList_RVAdapter extends RecyclerView.Adapter<StoreList_RVAdapter.StoreList_VH> {

    private final IFStoreListPresenter presenter;
    private final IFStoreListView view;

    public StoreList_RVAdapter(IFStoreListPresenter presenter, IFStoreListView view) {
        this.presenter = presenter;
        this.view = view;
    }

    @NonNull
    @Override
    public StoreList_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = view.getViewContext();
        View viewHold = LayoutInflater.from(context).inflate(view.getRVLayoutResource(), parent, false);
        return new StoreList_VH(viewHold);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreList_VH holder, int position) {
        presenter.onBindViewHolderAtPos(holder, position);
//        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_store_list_to_item_list));
    }

    @Override
    public int getItemCount() {
        return presenter.getDataSize();
    }

    public class StoreList_VH extends RecyclerView.ViewHolder {

        private final TextView storeNameTV;
        private final TextView storeOwnerTV;
        private final ImageView storeLogoIV;

        public StoreList_VH(View view) {
            super(view);
            storeNameTV = view.findViewById(R.id.store_name);
            storeOwnerTV = view.findViewById(R.id.owner_name);
            storeLogoIV = view.findViewById(R.id.store_logo);
            view.setOnClickListener(view1 -> {
                int position = getBindingAdapterPosition();
                String clickedStoreName = presenter.getStoreNameAtPos(position);
                MainActivity.getStoreBundle().putString(MainActivity.getStoreBundleKey(), clickedStoreName);
                Navigation.findNavController(view1).navigate(R.id.action_store_list_to_item_list);
            });
        }

        public TextView getStoreNameTV() {
            return storeNameTV;
        }

        public TextView getStoreOwnerTV() {
            return storeOwnerTV;
        }

        public ImageView getStoreLogoIV() {
            return storeLogoIV;
        }
    }

}
