package com.example.cscb07project.ui.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;

import java.text.NumberFormat;
import java.util.List;

public class OrderRVAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private final Context context;
    private final List<OrderEntry> orderList;

    public OrderRVAdapter(Context context, List<OrderEntry> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item_entry, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        if (orderList.get(position).getOrderNumber() != null) {
            holder.getHeaderLayout().setVisibility(View.VISIBLE);
            holder.getContentLayout().setVisibility(View.GONE);
            holder.getOrderNumber().setText("Order Number: "+orderList.get(position).getOrderNumber());
            holder.getDate().setText(orderList.get(position).getDate());
            if (orderList.get(position).isStatus())
                holder.getStatus().setText("Complete");
            else{
                holder.getStatus().setText("Incomplete");
            }
            Log.d("SLM.java", "HIIIIIIII");
            holder.getCompleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("SLM.java", String.valueOf(holder.getAdapterPosition()));
                    int idx = holder.getAdapterPosition();
                    String orderNumber = orderList.get(idx).getOrderNumber();
                    boolean currentStatus = orderList.get(idx).isStatus();
                    Log.d("SLM.java", "Change to not " + currentStatus);
                    OrderPresenter.changeStatus(orderNumber, !currentStatus);

                }
            });
        } else {
            holder.getHeaderLayout().setVisibility(View.GONE);
            holder.getContentLayout().setVisibility(View.VISIBLE);
            holder.getItemModifier().setVisibility(View.GONE);

            holder.getItemName().setText(orderList.get(position).getItemName());
            holder.getItemBrand().setText(orderList.get(position).getBrand());
            int quantity_val = orderList.get(position).getQty();

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String priceString = formatter.format(orderList.get(position).getPrice());
            holder.getItemPrice().setText(priceString + (quantity_val > 1 ? " (x" + quantity_val + ")" : ""));

            String img_url_val = orderList.get(position).getImgURL();
            if (img_url_val == null || img_url_val.isEmpty()) {
                holder.getItemImg().setImageResource(R.drawable.ic_launcher_background);
            } else {
                Glide.with(context).load(img_url_val).into(holder.getItemImg());
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
