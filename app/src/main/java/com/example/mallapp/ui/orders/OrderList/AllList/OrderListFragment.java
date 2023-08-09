package com.example.mallapp.ui.orders.OrderList.AllList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentOrderAllListBinding;

public class OrderListFragment extends Fragment implements IFOrderListView {

    private IFOrderListPresenter presenter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        presenter = new OrderListPresenter(this);
        com.example.cscb07project.databinding.FragmentOrderAllListBinding binding = FragmentOrderAllListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.order_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.onViewCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public int getRVLayoutResource() {
        return R.layout.fragment_order_all_entry;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter<OrderList_RVAdapter.OrderListVH> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setTextViewText(TextView textView, String text) {
        textView.setText(text);
    }

    @Override
    public void setColorComplete(View view) {
//        ((CardView) view.findViewById(R.id.order_all_entry_card))
//                .setCardBackgroundColor(requireContext().getColor(R.color.order_completed));
        ((TextView) view.findViewById(R.id.order_status))
                .setTextColor(requireContext().getColor(R.color.order_completed));
    }

    @Override
    public void setColorIncomplete(View view) {
        ((TextView) view.findViewById(R.id.order_status))
                .setTextColor(requireContext().getColor(R.color.order_not_complete));
    }
}
