package com.example.productapp.adapters;

import static java.util.prefs.Preferences.MAX_NAME_LENGTH;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.productapp.DetailProductActivity;
import com.example.productapp.R;
import com.example.productapp.Session;
import com.example.productapp.models.Product;
import com.example.productapp.repository.OrderRepository;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
            holder = new ViewHolder();
            holder.productNameTextView = view.findViewById(R.id.tv_product_title);
            holder.productPriceTextView = view.findViewById(R.id.tv_product_price);
            holder.productImageView = view.findViewById(R.id.img_url);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Product product = productList.get(position);

        String productName = product.getTenSP();
        if (productName.length() > 30) {
            productName = productName.substring(0, 30) + "...";
        }

        holder.productNameTextView.setText(productName);
        holder.productPriceTextView.setText("$ " + String.valueOf(product.getGia()));
        Glide.with(context).load(product.getHinhAnh()).into(holder.productImageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProductActivity.class);

                Gson gson = new Gson();
                String productJson = gson.toJson(product);
                intent.putExtra("selected_product", productJson);
                context.startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_add_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRepository orderRepository = new OrderRepository(context);
                orderRepository.addOrderToCart(Session.getUserSession().getUserId(), product.getMaSP(), 1, new OrderRepository.OrderAddCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(context, "Đã thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(String error) {
                        Toast.makeText(context, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        return view;
    }

    private static class ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        ImageView productImageView;
    }
}
