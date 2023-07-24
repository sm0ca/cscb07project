package com.example.mallapp.StoreList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallapp.R;
import com.example.mallapp.databinding.FragmentStoreListBinding;

public class StoreListFragment extends Fragment {

    private FragmentStoreListBinding binding;
    private StoreListPresenter presenter;
    private RecyclerView recyclerView;

    public StoreListFragment() {
        super(R.layout.fragment_store_list);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentStoreListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // TODO: Make store item list
    // TODO: Refactor code with interfaces/abstract classes
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new StoreListPresenter(this);
        recyclerView = view.findViewById(R.id.store_list_entry);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    public void setAdapter(StoreList_RVAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

}
