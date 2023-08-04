package com.example.mallapp.OrderList.StoreStatusList;

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

import com.example.mallapp.OrderList.AllList.OrderList_RVAdapter;
import com.example.mallapp.R;
import com.example.mallapp.databinding.FragmentOrderStoreListBinding;

public class StoreStatusFragment extends Fragment implements IFStoreStatusView {

    private IFStoreStatusPresenter presenter;
    private ExpandableListView expandableListView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        presenter = new StoreStatusPresenter(this, OrderList_RVAdapter.getOrderIdClicked());
        com.example.mallapp.databinding.FragmentOrderStoreListBinding binding = FragmentOrderStoreListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = view.findViewById(R.id.order_expandable_list);
        presenter.onViewCreated();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void setAdapter(BaseExpandableListAdapter adapter) {
        expandableListView.setAdapter(adapter);
    }

}
