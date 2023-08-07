package com.example.cscb07project.ui.orders;

import java.util.List;

public class OrderPresenter {

    private final OrdersFragment fragment;
    private final OrderModel model;
    private OrderRVAdapter adapter;

    public OrderPresenter(OrdersFragment fragment) {
        this.fragment = fragment;
        this.model = new OrderModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<OrderEntry> items) {
        adapter = new OrderRVAdapter(fragment.getContext(), items);
        fragment.setAdapter(adapter);
        }

    public static void changeStatus(String orderNumber, boolean status) {
        OrderModel.changeStatus(orderNumber, status);

    }
}
