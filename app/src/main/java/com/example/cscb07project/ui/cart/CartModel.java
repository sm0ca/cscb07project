package com.example.cscb07project.ui.cart;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.ui.itemlist.ItemListEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CartModel {
    private static String dbUrl;
    private final CartPresenter presenter;
    private final DatabaseReference cartRef; // make static?
    private final DatabaseReference storeRef;
    private ChildEventListener listener;
    private List<CartEntry> cartList;

    public CartModel(CartPresenter presenter, String url) {
        this.dbUrl = url;
        this.presenter = presenter;
        cartList = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(dbUrl);
        cartRef = db.getReference().child("users").child(MainActivity.currentUser).child("cart");
        storeRef = db.getReference().child("stores");
    }

    public void createEventListener() {
        listener = cartRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String storeName = snapshot.getKey();
                cartList.add(new CartEntry(storeName)); // This should only happen once per store
                presenter.setAdapter(cartList);

                cartRef.child(storeName).addChildEventListener(new ChildEventListener() {
                    final String store = storeName;
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot cartSnapshot, @Nullable String previousChildName) {
                        String itemName = cartSnapshot.getKey();
                        int qty = cartSnapshot.getValue(Integer.class);
                        DatabaseReference itemRef = storeRef.child(store).child("items").child(itemName);
                        itemRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot itemSnapshot = task.getResult();
                                if (Boolean.FALSE.equals(itemSnapshot.child("forSale").getValue(Boolean.class))) {
                                    removeItem(store, itemName, true);
                                    return;
                                }
                                if (itemSnapshot.child("price").getValue(Double.class) == null) {
                                    Log.d("SLM.java", itemName + " is priceless.");
                                    return;
                                }
                                CartEntry newEntry = new CartEntry(itemName,
                                        itemSnapshot.child("brand").getValue(String.class),
                                        itemSnapshot.child("image").getValue(String.class),
                                        itemSnapshot.child("price").getValue(Double.class),
                                        R.drawable.round_remove_circle_36,
                                        qty);
                                int idx = 0;
                                while (idx < cartList.size() - 1 && !Objects.equals(cartList.get(idx).getStoreName(), store)) {
                                    idx++;
                                }
                                cartList.add(idx + 1, newEntry);
                                presenter.setAdapter(cartList);
                            }
                        });
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot cartSnapshot, @Nullable String previousChildName) {
                        String changedItem = cartSnapshot.getKey();
                        int newQty = cartSnapshot.getValue(Integer.class);
                        int idx = 0;
                        while (idx < cartList.size() - 1 && !Objects.equals(cartList.get(idx).getStoreName(), store)) {
                            idx++;
                        }
                        while (idx < cartList.size() - 1 && !Objects.equals(cartList.get(idx).getItemName(), changedItem)) {
                            idx++;
                        }
                        if (Objects.equals(cartList.get(idx).getItemName(), changedItem)) {
                            cartList.get(idx).setQty(newQty);
                        }
                        presenter.setAdapter(cartList);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot cartSnapshot) {
                        Log.d("SLM.java", "Small Rem.");
                        String remItem = cartSnapshot.getKey();
                        int idx = 0;
                        while (idx < cartList.size() - 1 && !Objects.equals(cartList.get(idx).getStoreName(), store)) {
                            idx++;
                        }
                        while (idx < cartList.size() - 1 && !Objects.equals(cartList.get(idx).getItemName(), remItem)) {
                            idx++;
                        }
                        if (Objects.equals(cartList.get(idx).getItemName(), remItem)) {
                            cartList.remove(idx);
                        }
                        presenter.setAdapter(cartList);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // onChildChanged(snapshot, previousChildName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("SLM.java", "Err listening to cart item.");
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d("SLM.java", "Big Rem.");
                String removedStore = snapshot.getKey();
                int idx = 0;
                while (idx < cartList.size() - 1 && !Objects.equals(cartList.get(idx).getStoreName(), removedStore)) {idx++;}
                if (Objects.equals(cartList.get(idx).getStoreName(), removedStore)) {
                    cartList.remove(idx);
                }
//                while (idx < cartList.size() && Objects.equals(cartList.get(idx).getStoreName(), null)) {
//                    cartList.remove(idx);}
                presenter.setAdapter(cartList);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                onChildChanged(snapshot, previousChildName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SLM.java", "Err listening to cart.");
                destroyEventListener();
            }
        });
    }

    public void destroyEventListener() {
        if (listener == null) {
            cartRef.removeEventListener(listener);
        }
    }

    public static void removeItem(String store, String item, Boolean fullRem) {
        DatabaseReference newRef = FirebaseDatabase.getInstance(dbUrl)
                .getReference("users").child(MainActivity.currentUser).child("cart");
        newRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot cartSnapshot = task.getResult();
                if (!(cartSnapshot.hasChild(store) && cartSnapshot.child(store).hasChild(item))) {
                    return;
                }
                DataSnapshot itemSnapshot = cartSnapshot.child(store).child(item);
                int qty =  itemSnapshot.getValue(Integer.class);
                if (fullRem || qty <= 1) {
                    newRef.child(store).child(item).removeValue();
                } else {
                    newRef.child(store).child(item).setValue(qty - 1);
                }
                checkCart();
            }
        });
    }


    public static void checkCart() {
        DatabaseReference newRef = FirebaseDatabase.getInstance(dbUrl)
                .getReference("users").child(MainActivity.currentUser);
        newRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot userSnapshot = task.getResult();
                if (!userSnapshot.hasChild("cart")) {
                    newRef.child("cart").setValue("");
                }
            }
        });
    }

    public static void placeOrder(String name, String address) {
        DatabaseReference newRef = FirebaseDatabase.getInstance(dbUrl)
                .getReference("users").child(MainActivity.currentUser);

        newRef.child("cart").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DatabaseReference orderRef = FirebaseDatabase.getInstance(dbUrl)
                        .getReference("orders");

                SimpleDateFormat idFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date currTime = new Date();
                String orderId = idFormatter.format(currTime);

                orderRef.child(orderId).child("address").setValue(address);
                orderRef.child(orderId).child("allComplete").setValue(false);
                orderRef.child(orderId).child("fullName").setValue(name);
                orderRef.child(orderId).child("time").setValue(timeFormatter.format(currTime));
                orderRef.child(orderId).child("username").setValue(MainActivity.currentUser);

                DataSnapshot cartSnapshot = task.getResult();
                for (DataSnapshot store : cartSnapshot.getChildren()) {
                    orderRef.child(orderId).child("storesItems").child(store.getKey())
                            .child("complete").setValue(false);
                    for (DataSnapshot item : cartSnapshot.child(store.getKey()).getChildren()) {
                        orderRef.child(orderId).child("storesItems").child(store.getKey())
                                .child("items").child(item.getKey()).setValue(item.getValue());
                    }
                }
                newRef.child("cart").setValue("");
            }
        });
    }
}