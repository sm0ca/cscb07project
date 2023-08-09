package com.example.cscb07project.ui.orders.OrderList.AllList;

import com.example.cscb07project.ui.orders.OrderList.DataHolder.OrderListEntry;
import com.example.cscb07project.ui.tools.IFNotifyAdapterService;

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
    public String getOrderIdAtPos(int position) {
        return model.getDataList().get(position).getOrderId();
    }

    @Override
    public void notifyAdapter(IFNotifyAdapterService notifier) {
        notifier.notifyAdapter(adapter);
    }

    @Override
    public void onBindViewHolderAtPos(OrderList_RVAdapter.OrderListVH holder, int position) {
        OrderListEntry order = model.getDataList().get(position);
        String orderId = order.getOrderId();   // Start of orderId
        String formattedId = "ID: " + orderId;
        holder.getOrderIdTV().setText(formattedId);
        String orderAddress = order.getAddress();   // Start of orderAddress
        if(orderAddress != null && !orderAddress.isEmpty()) {
            String formattedAddress = orderAddress.replace(", ", "\n");
            view.setTextViewText(holder.getOrderAddressTV(), formattedAddress);
        }
        else {
            String noAddress = "Address: N/A";
            view.setTextViewText(holder.getOrderAddressTV(), noAddress);
        }
        boolean orderAllComplete = order.isAllComplete();   // Start of orderAllComplete
        if(orderAllComplete) {
            String complete = "COMPLETE";
            view.setTextViewText(holder.getOrderAllCompleteTV(), complete);
            view.setColorComplete(holder.itemView);
        }
        else {
            String incomplete = "INCOMPLETE";
            view.setTextViewText(holder.getOrderAllCompleteTV(), incomplete);
            view.setColorIncomplete(holder.itemView);
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
        }
        else {
            String noTime = "Time of order: N/A";
            view.setTextViewText(holder.getOrderTimeTV(), noTime);
        }
    }

    @Override
    public int getDataSize() {
        return model.getDataList().size();
    }
}
