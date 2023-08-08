package com.example.mallapp.ui.shop.ItemList;

import static com.example.mallapp.MainActivity.currentUser;

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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentDetailedItemBinding;
import com.example.mallapp.MainActivity;

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
                Add add = new Add(currentUser, storename, itemName, selectedQuantity);
                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
                add.addToFirebase();
                getParentFragmentManager().popBackStack();
            }
        });

    }

}

