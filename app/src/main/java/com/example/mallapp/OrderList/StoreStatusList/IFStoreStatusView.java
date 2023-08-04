package com.example.mallapp.OrderList.StoreStatusList;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

public interface IFStoreStatusView {
    Context getViewContext();
    void setAdapter(BaseExpandableListAdapter adapter);
}
