package com.example.mallapp.ui.orders.OrderList.StoreStatusList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.elevation.SurfaceColors;

import java.util.Locale;

public class StoreStatus_ExpListAdapter extends BaseExpandableListAdapter {

    private final IFStoreStatusPresenter storePresenter;
    private final IFStoreStatusView storeView;
//    private final ExpandableListView expListView;

    public StoreStatus_ExpListAdapter(IFStoreStatusPresenter storePresenter, IFStoreStatusView storeView) {
        this.storePresenter = storePresenter;
        this.storeView = storeView;
//        this.expListView = expListView;
    }

    @Override
    public int getGroupCount() {
        return storePresenter.getGroupCount();
    }

    @Override
    public int getChildrenCount(int i) {
        return storePresenter.getChildrenCount(i);
    }

    @Override
    public Object getGroup(int i) {
        return storePresenter.getGroup(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return storePresenter.getChild(i, i1);
    }

    @Override
    public long getGroupId(int i) {
        return storePresenter.getGroupId(i);
    }

    @Override
    public long getChildId(int i, int i1) {
        return storePresenter.getChildId(i, i1);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View inflatedView = View.inflate(storeView.getViewContext(), R.layout.fragment_order_store_title, null);
        TextView storeNameTV = inflatedView.findViewById(R.id.order_store_name);
        TextView storeCompleteTV = inflatedView.findViewById(R.id.order_store_status);
        ImageView storeImageIV = inflatedView.findViewById(R.id.order_store_image);
        storeNameTV.setText(storePresenter.getStoreName(i));
        String status;

        ((MaterialCardView) inflatedView.findViewById(R.id.order_store_card_view))
                .setCardBackgroundColor(SurfaceColors.SURFACE_1.getColor(storeView.getViewContext()));
        if (storePresenter.getStoreComplete(i)) {
            status = "COMPLETE";
            storeCompleteTV.setTextColor(storeView.getViewContext().getColor(R.color.order_completed));
        }
        else {
            status = "INCOMPLETE";
            storeCompleteTV.setTextColor(storeView.getViewContext().getColor(R.color.order_not_complete));
        }
        storeCompleteTV.setText(status);
        String storeImgURL = storePresenter.getStoreImageURL(i);
        if (storeImgURL != null) {
            Glide.with(storeView.getViewContext()).load(storeImgURL).into(storeImageIV);
        }
        return inflatedView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View inflatedView = View.inflate(storeView.getViewContext(), R.layout.fragment_order_store_child, null);
        TextView itemNameTV = inflatedView.findViewById(R.id.order_item_name);
        TextView itemQtyTV = inflatedView.findViewById(R.id.order_item_qty);
        ImageView itemImageIV = inflatedView.findViewById(R.id.order_item_image);
        itemNameTV.setText(storePresenter.getItemName(i, i1));
        itemQtyTV.setText(String.format(Locale.CANADA, "QTY: %d", storePresenter.getItemQty(i, i1)));
        String itemImgURL = storePresenter.getItemImageURL(i, i1);
        if (itemImgURL != null) {
            Glide.with(storeView.getViewContext()).load(itemImgURL).into(itemImageIV);
        }
        return inflatedView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
