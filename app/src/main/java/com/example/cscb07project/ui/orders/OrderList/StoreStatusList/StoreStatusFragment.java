package com.example.cscb07project.ui.orders.OrderList.StoreStatusList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cscb07project.ui.orders.OrderList.AllList.OrderList_RVAdapter;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentOrderStoreListBinding;

public class StoreStatusFragment extends Fragment implements IFStoreStatusView {

    private IFStoreStatusPresenter presenter;
    private ExpandableListView expandableListView;
    private BaseExpandableListAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        presenter = new StoreStatusPresenter(this, OrderList_RVAdapter.getOrderIdClicked());
        com.example.cscb07project.databinding.FragmentOrderStoreListBinding binding = FragmentOrderStoreListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = view.findViewById(R.id.order_expandable_list);
        adapter = new StoreStatus_ExpListAdapter(presenter, this);
        expandableListView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Glide.get(requireContext()).clearMemory();
        presenter.onDestroyView();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public BaseExpandableListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(BaseExpandableListAdapter adapter) {
        expandableListView.setAdapter(adapter);
    }

}
