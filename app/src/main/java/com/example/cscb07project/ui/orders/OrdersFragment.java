package com.example.cscb07project.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentOrdersBinding;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;

    private RecyclerView recyclerView;

    private OrderPresenter presenter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new OrderPresenter(this);
        recyclerView = view.findViewById(R.id.order_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderRVAdapter adapter = new OrderRVAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    public void setAdapter(OrderRVAdapter adapter) {recyclerView.setAdapter(adapter);}


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}