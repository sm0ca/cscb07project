package com.example.cscb07project.ui.orders.OrderList.AllList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.google.android.material.button.MaterialButton;


public class OrderList_RVAdapter extends RecyclerView.Adapter<OrderList_RVAdapter.OrderListVH> {

    private final IFOrderListPresenter presenter;
    private final IFOrderListView view;
    private static String orderIdClicked;

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

    public static String getOrderIdClicked() {
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
            MaterialButton orderNextIconIV = itemView.findViewById(R.id.order_next_icon);

            orderNextIconIV.setOnClickListener(listenerView -> {
                int positionClicked = getBindingAdapterPosition();
                orderIdClicked = presenter.getOrderIdAtPos(positionClicked);
                // TODO: Fix crash when going from order list to each store page
//                View inflatedView = View.inflate(view.getViewContext(), R.layout.fragment_order_all_entry, null);
//                Navigation.findNavController(listenerView).navigate(R.id.action_orders_all_list_to_orders_store_status_list);
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
