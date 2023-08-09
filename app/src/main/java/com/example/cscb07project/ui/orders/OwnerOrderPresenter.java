package com.example.cscb07project.ui.orders;

import java.util.List;

public class OwnerOrderPresenter {

    private final OwnerOrdersFragment fragment;
    private final OwnerOrderModel model;
    private OwnerOrderRVAdapter adapter;

    public OwnerOrderPresenter(OwnerOrdersFragment fragment) {
        this.fragment = fragment;
        this.model = new OwnerOrderModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<OwnerOrderEntry> items) {
        adapter = new OwnerOrderRVAdapter(fragment.getContext(), items);
        fragment.setAdapter(adapter);
        }

    public static void changeStatus(String orderNumber, boolean status) {
        OwnerOrderModel.changeStatus(orderNumber, status);

    }
}
