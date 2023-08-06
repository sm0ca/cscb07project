package com.example.mallapp.ui.cart;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentCartBinding;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {
    public static double subTotal;
    private FragmentCartBinding binding;
    private CartPresenter presenter;
    private RecyclerView recyclerView;
    private TextView totalPriceView;
    private Button cartProceedView;

    public CartFragment() {
        super(R.layout.fragment_cart);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        CartViewModel cartViewModel =
//                new ViewModelProvider(this).get(CartViewModel.class);
        CartFragment.subTotal = 0;
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

        totalPriceView = view.findViewById(R.id.cart_subtotal_value);

        cartProceedView = view.findViewById(R.id.cart_proceed_button);
        cartProceedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CartFragment.subTotal == 0.0) {
                    Toast.makeText(getContext(), "Please add some items to your cart first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                NavHostFragment.findNavController(CartFragment.this)
                        .navigate(R.id.action_navigation_cart_to_cart_checkout);
            }
        });

        CartRVAdapter adapter = new CartRVAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    public void setAdapter(CartRVAdapter adapter) {recyclerView.setAdapter(adapter);}

    public void setTotal(double subTotal) {
        CartFragment.subTotal = subTotal;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        totalPriceView.setText(formatter.format(subTotal));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}