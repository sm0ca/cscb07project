package com.example.mallapp.OrderList.AllList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallapp.R;


public class OrderList_RVAdapter extends RecyclerView.Adapter<OrderList_RVAdapter.OrderListVH> {

    private final IFOrderListPresenter presenter;
    private final IFOrderListView view;
    private static int orderIdClicked;

    public OrderList_RVAdapter(IFOrderListPresenter presenter, IFOrderListView view) {
        this.presenter = presenter;
        this.view = view;
    }

    @NonNull
    @Override
    public OrderListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderListVH(LayoutInflater.from(view.getViewContext()).inflate(view.getRVLayoutResource(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListVH holder, int position) {
        presenter.onBindViewHolderAtPos(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getDataSize();
    }

    public static int getOrderIdClicked() {
        return orderIdClicked;
    }

    public class OrderListVH extends RecyclerView.ViewHolder {

        private final TextView orderIdTV;
        private final TextView orderAddressTV;
        private final TextView orderAllCompleteTV;
        private final TextView orderFullNameTV;
        private final TextView orderTimeTV;

        public OrderListVH(@NonNull View itemView) {
            super(itemView);
            orderIdTV = itemView.findViewById(R.id.order_id);
            orderAddressTV = itemView.findViewById(R.id.order_address);
            orderAllCompleteTV = itemView.findViewById(R.id.order_status);
            orderFullNameTV = itemView.findViewById(R.id.order_full_name);
            orderTimeTV = itemView.findViewById(R.id.order_time);
            ImageView orderNextIconIV = itemView.findViewById(R.id.order_next_icon);

            orderNextIconIV.setOnClickListener(view -> {
                int positionClicked = getBindingAdapterPosition();
                orderIdClicked = presenter.getOrderIdAtPos(positionClicked);
                Navigation.findNavController(view).navigate(R.id.action_order_list_to_store_status);
            });
        }

        public TextView getOrderIdTV() {
            return orderIdTV;
        }

        public TextView getOrderAddressTV() {
            return orderAddressTV;
        }

        public TextView getOrderAllCompleteTV() {
            return orderAllCompleteTV;
        }

        public TextView getOrderFullNameTV() {
            return orderFullNameTV;
        }

        public TextView getOrderTimeTV() {
            return orderTimeTV;
        }

    }

}
