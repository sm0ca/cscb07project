package com.example.cscb07project.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentOrdersBinding;
import com.example.cscb07project.MainActivity;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!MainActivity.isOwner) {
            NavHostFragment.findNavController(OrdersFragment.this)
                    .navigate(R.id.action_navigation_orders_to_orders_all_list);
        }
        else {
            NavHostFragment.findNavController(OrdersFragment.this)
                    .navigate(R.id.action_navigation_orders_to_owner_orders);
        }
//        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
//        MenuItem orderMenuItem = bottomNavigationView.getMenu().findItem(R.id.navigation_orders);
//        orderMenuItem.setChecked(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}