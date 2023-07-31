package com.example.cscb07project.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartPresenter presenter;
    private RecyclerView recyclerView;

    public CartFragment() {
        super(R.layout.fragment_cart);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        CartViewModel cartViewModel =
//                new ViewModelProvider(this).get(CartViewModel.class);
        binding = FragmentCartBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//        final TextView textView = binding.textCart;
//        cartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new CartPresenter(this);
        recyclerView = view.findViewById(R.id.cart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CartRVAdapter adapter = new CartRVAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    public void setAdapter(CartRVAdapter adapter) {recyclerView.setAdapter(adapter);}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}