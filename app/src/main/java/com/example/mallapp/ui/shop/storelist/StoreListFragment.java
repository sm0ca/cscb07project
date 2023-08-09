package com.example.mallapp.ui.shop.storelist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentStoreListBinding;
import com.example.mallapp.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreListFragment extends Fragment implements IFStoreListView {

    @IdRes
    private static final int RECYCLER_VIEW_RESOURCE = R.id.store_list_entry;
    @LayoutRes
    private static final int RECYCLER_VIEW_LAYOUT = R.layout.fragment_store_entry;
    @DrawableRes
    private static final int PLACEHOLDER_STORE_ICON = R.drawable.ic_launcher_background;
    private FragmentStoreListBinding binding;
    private IFStoreListPresenter presenter;
    private RecyclerView recyclerView;

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
        recyclerView = view.findViewById(RECYCLER_VIEW_RESOURCE);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        presenter.onViewCreated();

        // MainActivity.bottomNav.getMenu().findItem(R.id.navigation_shop).setChecked(true); //setSelectedItemId(R.id.navigation_shop);

    }

    @Override
    public void setAdapter(RecyclerView.Adapter<StoreList_RVAdapter.StoreList_VH> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }


    @Override
    public void displayStoreLogo(StoreList_RVAdapter.StoreList_VH holder) {
        holder.getStoreLogoIV().setImageResource(PLACEHOLDER_STORE_ICON);
    }

    @Override
    public void displayStoreLogo(StoreList_RVAdapter.StoreList_VH holder, String imageURL) {
        Glide.with(requireContext()).load(imageURL).into(holder.getStoreLogoIV());
    }

    @Override
    public int getRVLayoutResource() {
        return RECYCLER_VIEW_LAYOUT;
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
        } catch (NullPointerException e) {
            Log.d("SLM.java", "Something went wrong with onDestroy()!");
        }
        binding = null;
        presenter.onDestroy();
    }
}
