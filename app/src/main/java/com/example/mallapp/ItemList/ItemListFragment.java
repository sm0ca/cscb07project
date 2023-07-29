package com.example.mallapp.ItemList;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallapp.R;
import com.example.mallapp.databinding.FragmentItemListBinding;

import java.util.ArrayList;

public class ItemListFragment extends Fragment {

    private FragmentItemListBinding binding;
    private ItemListPresenter presenter;
    private RecyclerView recyclerView;

    public ItemListFragment() {
        super(R.layout.fragment_item_list);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        presenter = new ItemListPresenter(this);
//        recyclerView = view.findViewById(R.id.item_list_entry);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Create the adapter and set it for the RecyclerView
//        ItemListRVAdapter adapter = new ItemListRVAdapter(requireContext(), new ArrayList<>());
//        recyclerView.setAdapter(adapter);
    }

    // Method to set the adapter for the RecyclerView
    public void setAdapter(ItemListRVAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }
}
