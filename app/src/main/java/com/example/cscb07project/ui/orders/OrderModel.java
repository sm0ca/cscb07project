package com.example.cscb07project.ui.orders;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderModel {
    private static String dbUrl;
    private final OrderPresenter presenter;
    private final DatabaseReference orderRef; // make static?

    private final DatabaseReference storeRef;
    private ChildEventListener listener;
    private List<OrderEntry> orderList;

    public OrderModel(OrderPresenter presenter, String url){
        this.dbUrl = url;
        this.presenter = presenter;
        orderList = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(dbUrl);
        orderRef = db.getReference().child("orders");
        storeRef = db.getReference().child("stores").child(MainActivity.ownerStore).child("items");
    }

    public void createEventListener() {
        listener = orderRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String orderNumber = snapshot.getKey();
                if (snapshot.child("storesItems").hasChild(MainActivity.ownerStore)) {
                    DataSnapshot storeSnapshot = snapshot.child("storesItems").child(MainActivity.ownerStore);
                    String time = snapshot.child("time").getValue(String.class);
                    boolean status = storeSnapshot.child("complete").getValue(boolean.class);
                    orderList.add(new OrderEntry(orderNumber, time, status));
                    presenter.setAdapter(orderList);
                    DatabaseReference completeRef = orderRef.child(orderNumber).child("storesItems").child(MainActivity.ownerStore);
                    completeRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot completeSnapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot completeSnapshot, @Nullable String previousChildName) {
                            boolean newStatus = completeSnapshot.getValue(boolean.class);
                            int idx = 0;
                            while (idx < orderList.size()  && !Objects.equals(orderList.get(idx).getOrderNumber(), orderNumber)) {
                                Log.d("SLM.java",orderList.get(idx).getItemName() + " is " + orderList.get(idx).isVisible());
                                idx++;
                            }
                            if (Objects.equals(orderList.get(idx).getOrderNumber(), orderNumber)) {
                                orderList.get(idx).setStatus(newStatus);
                                Log.d("SLM.java", "Changed "+orderNumber+" To "+newStatus);
                                idx++;
                            }
                            while (idx < orderList.size()  && Objects.equals(orderList.get(idx).getOrderNumber(), null)) {
                                orderList.get(idx).setVisible(!newStatus);
                                Log.d("SLM.java",orderList.get(idx).getItemName() + " is " + orderList.get(idx).isVisible());
                                idx++;

                            }
                            presenter.setAdapter(orderList);
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot completeSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot completeSnapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    for (DataSnapshot itemSnapshot : storeSnapshot.child("items").getChildren()) {
                        String itemName = itemSnapshot.getKey();
                        DatabaseReference itemRef = storeRef.child(itemName);
                        itemRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot itemDataSnapshot = task.getResult();
                                String brand = itemDataSnapshot.child("brand").getValue(String.class);
                                Log.d("SLM.java", brand);
                                String image = itemDataSnapshot.child("image").getValue(String.class);
                                double price = itemDataSnapshot.child("price").getValue(Double.class);
                                int qty = itemSnapshot.getValue(int.class);
                                OrderEntry itemEntry = new OrderEntry(itemName, brand, image, price, R.drawable.round_remove_circle_36, qty);
                                int idx = 0;
                                while (idx < orderList.size() - 1 && !Objects.equals(orderList.get(idx).getOrderNumber(), orderNumber)) {
                                    idx++;
                                }
                                orderList.add(idx + 1, itemEntry);
                                presenter.setAdapter(orderList);
                            }
                        });

                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String orderNumber = snapshot.getKey();
                DatabaseReference allCompleteRef = orderRef.child(orderNumber).child("allComplete");
                DataSnapshot storeSnapshot = snapshot.child("storesItems");
                boolean allComplete = true;
                for(DataSnapshot store: storeSnapshot.getChildren()){
                    allComplete = allComplete && store.child("complete").getValue(boolean.class);
                }
                allCompleteRef.setValue(allComplete);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                onChildChanged(snapshot, previousChildName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SLM.java", "Err listening to order.");
                destroyEventListener();
            }
        });

    }

    public void destroyEventListener() {
        if (listener == null) {
            orderRef.removeEventListener(listener);
        }
    }

    public static void changeStatus(String orderNumber, boolean status){
        DatabaseReference statusRef = FirebaseDatabase.getInstance(dbUrl).getReference("orders").child(orderNumber).child("storesItems").child(MainActivity.ownerStore).child("complete");
        statusRef.setValue(status);

    }
}
