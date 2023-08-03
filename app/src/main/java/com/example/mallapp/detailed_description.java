package com.example.mallapp;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mallapp.ItemList.Add;
import com.example.mallapp.ItemList.ItemListPresenter;
import com.example.mallapp.ItemList.Remove;
import com.example.mallapp.R;
import com.example.mallapp.StoreList.StoreList_RVAdapter;
import com.example.mallapp.databinding.FragmentDetailedItemBinding;
import com.example.mallapp.databinding.FragmentItemListBinding;

import java.lang.reflect.Array;

public class detailed_description extends Fragment {

    private int selectedQuantity = 1;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        FragmentDetailedItemBinding binding = FragmentDetailedItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView itemNameView = view.findViewById(R.id.item_name);
        TextView itemBrandView = view.findViewById(R.id.item_brand);
        TextView itemDescriptionView = view.findViewById(R.id.item_description);
        ImageView itemImageView = view.findViewById(R.id.item_logo);

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
            itemImageView.setImageResource(R.drawable.placeholder_item_icon);
        }
        itemDescriptionView.setText(itemDescription);

        Spinner quantityspinner = view.findViewById(R.id.quantity_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.quantity_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityspinner.setAdapter(adapter);
        quantityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedQuantity = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Button addButton = view.findViewById(R.id.detailed_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storename = MainActivity.getStoreBundle().getString(MainActivity.getStoreBundleKey());
                Add add = new Add("user2", storename, itemName, selectedQuantity);
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
                add.addToFirebase();
            }
        });

    }

}

