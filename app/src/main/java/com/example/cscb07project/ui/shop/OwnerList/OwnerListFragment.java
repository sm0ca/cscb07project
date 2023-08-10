package com.example.cscb07project.ui.shop.OwnerList;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentItemListBinding;
import com.example.cscb07project.MainActivity;
import com.example.cscb07project.databinding.FragmentOwnerListBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

public class OwnerListFragment extends Fragment {

    private FragmentOwnerListBinding binding;
    private OwnerListPresenter presenter;
    private RecyclerView recyclerView;

    public OwnerListFragment() {
        super(R.layout.fragment_item_list);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentOwnerListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((AppCompatActivity) requireContext()).getSupportActionBar())
                .setTitle("Your Store (" + MainActivity.ownerStore + ")");

        presenter = new OwnerListPresenter(this);
        recyclerView = view.findViewById(R.id.item_list_entry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.fab_add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_owner_list_to_owner_list_add);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Glide.get(requireContext()).clearMemory();
        presenter.onDestroyView();
    }

    // Method to set the adapter for the RecyclerView
    public void setAdapter(OwnerListRVAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }
}
