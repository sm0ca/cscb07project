package com.example.mallapp.StoreList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallapp.R;
import com.example.mallapp.databinding.FragmentStoreListBinding;

public class StoreListFragment extends Fragment implements IFStoreListView {

    private FragmentStoreListBinding binding;
    private IFStoreListPresenter presenter;
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new StoreListPresenter(this);
        recyclerView = view.findViewById(R.id.store_list_entry);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        presenter.onViewCreated();
    }


    @Override
    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void displayStoreLogo(StoreList_ViewHolder holder) {
        holder.getStoreLogo().setImageResource(R.drawable.placeholder_store_icon);
    }

    @Override
    public void displayStoreLogo(StoreList_ViewHolder holder, String imageURL) {
        Glide.with(requireContext()).load(imageURL).into(holder.getStoreLogo());
    }

    @Override
    public int getRVLayoutResource() {
        return R.layout.fragment_store_entry;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        presenter.onDestroy();
    }
}
