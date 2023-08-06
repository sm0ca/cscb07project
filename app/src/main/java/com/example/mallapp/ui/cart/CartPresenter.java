package com.example.mallapp.ui.cart;

import java.util.List;

public class CartPresenter {

    private final CartFragment fragment;
    private final CartModel model;
    private CartRVAdapter adapter;

    public CartPresenter(CartFragment fragment) {
        this.fragment = fragment;
        this.model = new CartModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<CartEntry> items) {
        adapter = new CartRVAdapter(fragment.getContext(), items);
        fragment.setAdapter(adapter);

        double subTotal = 0;
        for (CartEntry item : items) {
            subTotal += item.getPrice() * item.getQty();
        }

        fragment.setTotal(subTotal);
    }

    public static void removeItem(String store, String item) {
        CartModel.removeItem(store, item, false);
    }
    public static void placeOrder(String name, String address) {
        CartModel.placeOrder(name, address);
    }
}
