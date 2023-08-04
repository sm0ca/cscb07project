package com.example.mallapp.OrderList.AllList;

import android.widget.TextView;

import com.example.mallapp.tools.IFViewWithRV;

interface IFOrderListView extends IFViewWithRV<OrderList_RVAdapter.OrderListVH> {
    void setTextViewText(TextView textView, String text);
}
