package com.example.mallapp.ui.cart;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentCheckoutBinding;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CheckoutFragment extends Fragment {
    private ArrayList<EditText> editables;
    private EditText nameView;
    private EditText addressView;
    private EditText postalView;
    private ImageView cardImageView;


    private FragmentCheckoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCheckoutBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editables = new ArrayList<>();
        for (View focusable : view.getFocusables(View.FOCUS_FORWARD)) {
            if (focusable instanceof EditText) {
                editables.add((EditText) focusable);
            }
        }

        nameView = view.findViewById(R.id.edit_name);
        addressView = view.findViewById(R.id.edit_address);
        postalView = view.findViewById(R.id.edit_postal_code);

        TextView subtotalView = view.findViewById(R.id.subtotal_value);
        TextView hstView = view.findViewById(R.id.hst_value);
        TextView totalView = view.findViewById(R.id.total_value);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        subtotalView.setText(formatter.format(CartFragment.subTotal));
        hstView.setText(formatter.format(CartFragment.subTotal*0.13));
        totalView.setText(formatter.format(CartFragment.subTotal*1.13));

        cardImageView = view.findViewById(R.id.card_image);
        EditText cardNumberView = view.findViewById(R.id.edit_card_number);
        cardNumberView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cardImageView.setImageResource(getCardImg(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        Button placeOrderView = view.findViewById(R.id.place_order_button);
        placeOrderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add logic to place order
                for (EditText editable : editables) {
                    if (editable.getText().toString().length() == 0) {
                        Toast.makeText(getContext(), "Please fill out all of the fields!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String nameText = nameView.getText().toString();
                String addressText = addressView.getText().toString();
                String postalText = postalView.getText().toString();

                CartPresenter.placeOrder(nameText, addressText + ", " + postalText);
                NavHostFragment.findNavController(CheckoutFragment.this)
                        .navigate(R.id.action_cart_checkout_to_navigation_shop);
            }
        });
    }

    public int getCardImg(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            return R.drawable.card_other;
        }
        switch (charSequence.charAt(0)) {
            case '3':
                return R.drawable.card_amex;
            case '4':
                return R.drawable.card_visa;
            case '5':
                return R.drawable.card_mastercard;
            case '6':
                return R.drawable.card_discover;
            default:
                return R.drawable.card_other;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}