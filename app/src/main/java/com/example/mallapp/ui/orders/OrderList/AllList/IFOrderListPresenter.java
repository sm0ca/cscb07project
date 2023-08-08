package com.example.mallapp.ui.orders.OrderList.AllList;

import com.example.mallapp.ui.tools.IFPresenterWithRV;

public interface IFOrderListPresenter extends IFPresenterWithRV<OrderList_RVAdapter.OrderListVH> {
    void onViewCreated();
    void onDestroyView();
    String getOrderIdAtPos(int position);
}
