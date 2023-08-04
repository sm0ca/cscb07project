package com.example.mallapp.OrderList.StoreStatusList;

public class StoreStatusPresenter implements IFStoreStatusPresenter {

    private final IFStoreStatusView view;
    private final IFStoreStatusModel model;
    private final StoreStatus_ExpListAdapter adapter;

    public StoreStatusPresenter(IFStoreStatusView view, int orderIdClicked) {
        this.view = view;
        this.model = new StoreStatusModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/", orderIdClicked);
        this.adapter = new StoreStatus_ExpListAdapter(this, view);
    }

    @Override
    public void onViewCreated() {
        view.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        model.createEventListener();
    }

    @Override
    public void onDestroyView() {
        model.destroyEventListener();
    }

    @Override
    public void notifyAdapterDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return model.getDataList().size();
    }

    @Override
    public int getChildrenCount(int i) {
        return model.getDataList().get(i).getItemList().size();
    }

    @Override
    public Object getGroup(int i) {
        return model.getDataList().get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return model.getDataList().get(i).getItemList().get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public String getStoreName(int i) {
        return model.getDataList().get(i).getStoreName();
    }

    @Override
    public boolean getStoreComplete(int i) {
        return model.getDataList().get(i).isComplete();
    }

    @Override
    public String getItemName(int i, int i1) {
        return model.getDataList().get(i).getItemList().get(i1).getItemName();
    }

    @Override
    public int getItemQty(int i, int i1) {
        return model.getDataList().get(i).getItemList().get(i1).getItemQty();
    }


}
