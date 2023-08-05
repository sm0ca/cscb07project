package com.example.mallapp.OrderList.AllList;

import com.example.mallapp.OrderList.DataHolder.OrderListEntry;
import com.example.mallapp.tools.IFNotifyAdapterService;

public class OrderListPresenter implements IFOrderListPresenter {

    private final IFOrderListView view;
    private final IFOrderListModel model;
    private final OrderList_RVAdapter adapter;

    public OrderListPresenter(IFOrderListView view) {
        this.view = view;
        this.model = new OrderListModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        this.adapter = new OrderList_RVAdapter(this, view);
    }

    @Override
    public void onViewCreated() {
        view.setAdapter(adapter);
        model.createEventListener();
    }

    @Override
    public void onDestroyView() {
        model.destroyEventListener();
    }

    @Override
    public int getOrderIdAtPos(int position) {
        return model.getDataList().get(position).getOrderId();
    }

    @Override
    public void notifyAdapter(IFNotifyAdapterService notifier) {
        notifier.notifyAdapter(adapter);
    }

    @Override
    public void onBindViewHolderAtPos(OrderList_RVAdapter.OrderListVH holder, int position) {
        OrderListEntry order = model.getDataList().get(position);
        int orderId = order.getOrderId();   // Start of orderId
        String formattedId = "ID: " + orderId;
        holder.getOrderIdTV().setText(formattedId);
        String orderAddress = order.getAddress();   // Start of orderAddress
        if(orderAddress != null && !orderAddress.isEmpty()) {
            String formattedAddress = orderAddress.replace(", ", "\n");
            view.setTextViewText(holder.getOrderAddressTV(), formattedAddress);
//            holder.getOrderAddressTV().setText(formattedAddress);
        }
        else {
            String noAddress = "Address: N/A";
            view.setTextViewText(holder.getOrderAddressTV(), noAddress);
//            holder.getOrderAddressTV().setText(noAddress);
        }
        boolean orderAllComplete = order.isAllComplete();   // Start of orderAllComplete
        if(orderAllComplete) {
            String complete = "COMPLETE";
            view.setTextViewText(holder.getOrderAllCompleteTV(), complete);
//            holder.getOrderAllCompleteTV().setText(complete);
        }
        else {
            String incomplete = "INCOMPLETE";
            view.setTextViewText(holder.getOrderAllCompleteTV(), incomplete);
        }
        String orderFullName = order.getFullName();     // Start of orderFullName
        if(orderFullName != null && !orderFullName.isEmpty()) {
            view.setTextViewText(holder.getOrderFullNameTV(), orderFullName);
        }
        else {
            String noFullName = "Full name: N/A";
            view.setTextViewText(holder.getOrderFullNameTV(), noFullName);
        }
        String orderTime = order.getTime();     // Start of orderTime
        if(orderTime != null && !orderTime.isEmpty()) {
            String formattedTime = orderTime.replace(", ", "\n");
            view.setTextViewText(holder.getOrderTimeTV(), formattedTime);
//            holder.getOrderTimeTV().setText(orderTime);
        }
        else {
            String noTime = "Time of order: N/A";
            view.setTextViewText(holder.getOrderTimeTV(), noTime);
//            holder.getOrderTimeTV().setText(noTime);
        }
    }

    @Override
    public int getDataSize() {
        return model.getDataList().size();
    }
}
