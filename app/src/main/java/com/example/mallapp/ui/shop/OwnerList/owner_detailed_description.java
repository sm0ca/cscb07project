package com.example.mallapp.ui.shop.OwnerList;

import static com.example.mallapp.MainActivity.ownerStore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentDetailedOwnerItemBinding;
import com.example.mallapp.ui.shop.ItemList.ItemListPresenter;

public class owner_detailed_description extends Fragment {

    private int selectedQuantity = 1;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        FragmentDetailedOwnerItemBinding binding = FragmentDetailedOwnerItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView itemNameView = view.findViewById(R.id.item_entry_name);
        TextView itemBrandView = view.findViewById(R.id.item_entry_brand);
        TextView itemDescriptionView = view.findViewById(R.id.item_entry_description);
        ImageView itemImageView = view.findViewById(R.id.item_entry_img);

        String itemName = ItemListPresenter.item_name;
        String itemBrand = ItemListPresenter.item_brand;
        String itemDescription = ItemListPresenter.item_description;
        String itemimage = ItemListPresenter.item_image;

        itemNameView.setText(itemName);
        itemBrandView.setText(itemBrand);
        if(itemimage != null && !itemimage.isEmpty()) {
            Glide.with(getContext()).load(itemimage).into(itemImageView);
        }
        else {
            itemImageView.setImageResource(R.drawable.ic_launcher_background);
        }
        itemDescriptionView.setText(itemDescription);
        Button deletebutton = view.findViewById(R.id.detailed_delete_button);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete_Item delete_item = new Delete_Item(ownerStore, itemName);
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                delete_item.delete();
                getParentFragmentManager().popBackStack();

            }
        });

    }

}

