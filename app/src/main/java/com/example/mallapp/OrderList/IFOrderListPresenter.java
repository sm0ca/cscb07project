package com.example.mallapp.OrderList;

import com.example.mallapp.tools.IFPresenterWithRV;

public interface IFOrderListPresenter extends IFPresenterWithRV<OrderList_RVAdapter.OrderListVH> {
    void onViewCreated();
    void onStart();
    void onStop();
}
