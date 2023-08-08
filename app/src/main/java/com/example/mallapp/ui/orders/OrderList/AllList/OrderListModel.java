package com.example.mallapp.ui.orders.OrderList.AllList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mallapp.MainActivity;
import com.example.mallapp.ui.orders.OrderList.DataHolder.OrderListEntry;
import com.example.mallapp.ui.tools.NotifyAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderListModel implements IFOrderListModel {

    private static final String NODE_ORDERS = "orders";
    private static final String NODE_ADDRESS = "address";
    private static final String NODE_ALL_COMPLETE = "allComplete";
    private static final String NODE_TIME = "time";
    private static final String NODE_FULL_NAME = "fullName";
    private static final String NODE_USERNAME = "username";
    private static final String CURRENT_USERNAME = MainActivity.currentUser;
    private final IFOrderListPresenter presenter;
    private final DatabaseReference queryOrders;
    private ChildEventListener listener;
    private final List<OrderListEntry> orderList;

    public OrderListModel(IFOrderListPresenter presenter, String databaseURL) {
        this.presenter = presenter;
        orderList = new ArrayList<>();
        queryOrders = FirebaseDatabase.getInstance(databaseURL).getReference().child(NODE_ORDERS);
    }

    @Override
    public void createEventListener() {
        Log.d("mine", "Started order listener");
        if(listener != null) {
            return;
        }
        listener = queryOrders.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String addedUsername = snapshot.child(NODE_USERNAME).getValue(String.class);
                if(addedUsername != null && addedUsername.equals(CURRENT_USERNAME)) {
                    String orderId = snapshot.getKey();
                    String orderAddress = snapshot.child(NODE_ADDRESS).getValue(String.class);
                    boolean orderComplete = Boolean.TRUE.equals(snapshot.child(NODE_ALL_COMPLETE).getValue(Boolean.class));
                    String orderFullName = snapshot.child(NODE_FULL_NAME).getValue(String.class);
                    String orderTime = snapshot.child(NODE_TIME).getValue(String.class);
                    orderList.add(new OrderListEntry(orderId, orderAddress, orderComplete, orderFullName, orderTime));
                    Collections.sort(orderList);
                    int idx = orderList.indexOf(new OrderListEntry(orderId));
                    presenter.notifyAdapter(new NotifyAdapter.Inserted(idx));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String changedUsername = snapshot.child(NODE_USERNAME).getValue(String.class);
                String orderId = snapshot.getKey();
                if(changedUsername != null && changedUsername.equals(CURRENT_USERNAME)) {
                    String orderAddress = snapshot.child(NODE_ADDRESS).getValue(String.class);
                    boolean orderComplete = Boolean.TRUE.equals(snapshot.child(NODE_ALL_COMPLETE).getValue(Boolean.class));
                    String orderFullName = snapshot.child(NODE_FULL_NAME).getValue(String.class);
                    String orderTime = snapshot.child(NODE_TIME).getValue(String.class);
                    if(!orderList.contains(new OrderListEntry(orderId))) {
                        orderList.add(new OrderListEntry(orderId));
                        Collections.sort(orderList);
                    }
                    int idx = orderList.indexOf(new OrderListEntry(orderId));
                    orderList.get(idx).setAddress(orderAddress);
                    orderList.get(idx).setAllComplete(orderComplete);
                    orderList.get(idx).setFullName(orderFullName);
                    orderList.get(idx).setTime(orderTime);
                    presenter.notifyAdapter(new NotifyAdapter.Changed(idx));
                }
                else {
                    int idx = orderList.indexOf(new OrderListEntry(orderId));
                    orderList.remove(new OrderListEntry(orderId));
                    presenter.notifyAdapter(new NotifyAdapter.Removed(idx));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String removedUsername = snapshot.child(NODE_USERNAME).getValue(String.class);
                if(removedUsername != null && removedUsername.equals(CURRENT_USERNAME)) {
                    String orderId = snapshot.getKey();
                    int removedIdx = orderList.indexOf(new OrderListEntry(orderId));
                    orderList.remove(new OrderListEntry(orderId));
                    presenter.notifyAdapter(new NotifyAdapter.Removed(removedIdx));
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("mine", "Listener had error. Not good very bad.");
                destroyEventListener();
            }
        });
    }

    @Override
    public void destroyEventListener() {
        if(listener != null) {
            Log.d("mine", "destroyed order listener");
            queryOrders.removeEventListener(listener);
        }
    }

    @Override
    public List<OrderListEntry> getDataList() {
        return orderList;
    }
}
