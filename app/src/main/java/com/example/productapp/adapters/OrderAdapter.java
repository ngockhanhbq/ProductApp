package com.example.productapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.productapp.R;
import com.example.productapp.Session;
import com.example.productapp.models.Order;
import com.example.productapp.models.Product;
import com.example.productapp.repository.OrderRepository;
import com.example.productapp.services.DialogConfirm;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Order> orderList;
    private Context context;


    public Double totalPrice() {
        return orderList.stream()
                .mapToDouble(order -> order.getProduct().getGia() * order.getSoLuong())
                .sum();
    }
    public int quantity(){
       return orderList.stream().mapToInt(Order::getSoLuong).sum();
    }
    public  int count(){
        return orderList.size();
    }
    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Set data to views
        Glide.with(context).load(order.getProduct().getHinhAnh()).into(holder.imageView);


        String productName = order.getProduct().getTenSP();
        if (productName.length() > 30) {
            productName = productName.substring(0, 30) + "...";
        }

        holder.productNameTextView.setText(productName);
        holder.quantityTextView.setText(String.valueOf(order.getSoLuong()));
        holder.priceTextView.setText("$ " + order.getProduct().getGia());


        OrderRepository orderRepository = new OrderRepository(context);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.quantityTextView.getText().toString());
                holder.quantityTextView.setText(String.valueOf(quantity + 1));
                orderRepository.addOrderToCart(Session.getUserSession().getUserId(),order.getProduct().getMaSP(),1,new OrderRepository.OrderAddCallback(){
                    @Override
                    public void onSuccess(String message) {

                    }
                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.quantityTextView.getText().toString());
                if (quantity == 1){
                    DialogConfirm.showDialog(context, new DialogConfirm.DialogCallback() {
                        @Override
                        public void onRemoveClicked() {
                            orderRepository.deleteOrder(order.getMaChiTiet(), new OrderRepository.OrderDeleteCallback() {
                                @Override
                                public void onSuccess(String message) {
                                    Toast.makeText(context,"Xóa đơn hàng thành công",Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onError(String error) {
                                    Toast.makeText(context,"Xóa đơn hàng không thành công",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }else{
                    holder.quantityTextView.setText(String.valueOf(quantity  - 1));
                    orderRepository.addOrderToCart(Session.getUserSession().getUserId(),order.getProduct().getMaSP(),-1,new OrderRepository.OrderAddCallback(){
                        @Override
                        public void onSuccess(String message) {

                        }
                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            }

        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfirm.showDialog(context, new DialogConfirm.DialogCallback() {
                    @Override
                    public void onRemoveClicked() {
                        orderRepository.deleteOrder(order.getMaChiTiet(), new OrderRepository.OrderDeleteCallback() {
                            @Override
                            public void onSuccess(String message) {
                                Toast.makeText(context,"Xóa đơn hàng thành công",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(String error) {
                                Toast.makeText(context,"Xóa đơn hàng không thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView quantityTextView;
        TextView priceTextView;
        ImageView imageView;

        ImageButton add,minus,remove;



        ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.tv_title);
            quantityTextView = itemView.findViewById(R.id.tv_quanlity_order);
            priceTextView = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.img_url_order);
            add = itemView.findViewById(R.id.btn_add_in_order);
            minus = itemView.findViewById(R.id.btn_minus_in_order);
            remove = itemView.findViewById(R.id.btn_remove_in_order);
        }
    }

}
