package com.example.productapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productapp.R;
import com.example.productapp.Session;
import com.example.productapp.adapters.OrderAdapter;
import com.example.productapp.models.Order;
import com.example.productapp.repository.OrderRepository;

import java.util.List;

public class OrderFragment extends Fragment {
    private RecyclerView recyclerViewOrders;

    public OrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize RecyclerView
        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        TextView tv_quantity = view.findViewById(R.id.total_quantity);
        TextView tv_order = view.findViewById(R.id.total_product);
        TextView tv_price = view.findViewById(R.id.total_price);

        // Initialize OrderAdapter
        OrderRepository orderRepository = new OrderRepository(getContext());
        orderRepository.getOrders(Session.getUserSession().getUserId(), new OrderRepository.OrderCallback() {
            @Override
            public void onSuccess(List<Order> orderList) {
                OrderAdapter orderAdapter = new OrderAdapter(getContext(),orderList);
                recyclerViewOrders.setAdapter(orderAdapter);
                tv_quantity.setText("Tổng số sản phẩm : " + orderAdapter.quantity());
                tv_order.setText("Tổng đơn hàng : " + orderAdapter.count());
                tv_price.setText("Tổng tiền : "  + orderAdapter.totalPrice());
            }

            @Override
            public void onError(String message) {

            }
        });

        return view;
    }


}
