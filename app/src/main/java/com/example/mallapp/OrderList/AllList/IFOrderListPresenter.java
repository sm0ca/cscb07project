package com.example.mallapp.OrderList.AllList;

import com.example.mallapp.tools.IFPresenterWithRV;

public interface IFOrderListPresenter extends IFPresenterWithRV<OrderList_RVAdapter.OrderListVH> {
    void onViewCreated();
    void onDestroyView();
    int getOrderIdAtPos(int position);
}
