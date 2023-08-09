package com.example.cscb07project.ui.orders.OrderList.AllList;

import com.example.cscb07project.ui.tools.IFPresenterWithRV;

public interface IFOrderListPresenter extends IFPresenterWithRV<OrderList_RVAdapter.OrderListVH> {
    void onViewCreated();
    void onDestroyView();
    String getOrderIdAtPos(int position);
}
