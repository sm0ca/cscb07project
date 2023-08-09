package com.example.cscb07project.ui.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentShopBinding;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!MainActivity.isOwner) {
            NavHostFragment.findNavController(ShopFragment.this)
                    .navigate(R.id.action_navigation_shop_to_shop_storelist);
        }
        else {
            NavHostFragment.findNavController(ShopFragment.this)
                    .navigate(R.id.action_navigation_shop_to_ownerlist);
        }
//
//        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
//        MenuItem shopMenuItem = bottomNavigationView.getMenu().findItem(R.id.navigation_shop);
//        shopMenuItem.setChecked(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}