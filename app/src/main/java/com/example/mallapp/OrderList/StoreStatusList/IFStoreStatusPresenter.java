package com.example.mallapp.OrderList.StoreStatusList;

public interface IFStoreStatusPresenter {
    void onViewCreated();
    void onStart();
    void onDestroyView();
    void notifyAdapterDataChanged();
    int getGroupCount();
    int getChildrenCount(int i);
    Object getGroup(int i);
    Object getChild(int i, int i1);
    long getGroupId(int i);
    long getChildId(int i, int i1);
    String getStoreName(int i);
    boolean getStoreComplete(int i);
    String getItemName(int i, int i1);
    int getItemQty(int i, int i1);
}
