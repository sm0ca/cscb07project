package com.example.cscb07project.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;

import java.util.ArrayList;
import java.util.Objects;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.OrderViewHolder>{

    Context context;

    ArrayList<Order> list;

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_order_button,parent,false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order user = list.get(position);
        holder.order_number.setText(user.getOrder_number());
        holder.date.setText(user.getDate());
        holder.time.setText(user.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView order_number, date, time;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            order_number = itemView.findViewById(R.id.order_number);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}

