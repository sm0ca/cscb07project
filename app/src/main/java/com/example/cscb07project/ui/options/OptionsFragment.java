package com.example.cscb07project.ui.options;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentOptionsBinding;
import com.example.cscb07project.ui.login_register.activity_login;
import com.google.firebase.auth.FirebaseAuth;

public class OptionsFragment extends Fragment {

    private FragmentOptionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OptionsViewModel optionsViewModel =
                new ViewModelProvider(this).get(OptionsViewModel.class);

        binding = FragmentOptionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textOptions;
        optionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // logout implementation
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Button logout_button = root.findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(getContext(), activity_login.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}