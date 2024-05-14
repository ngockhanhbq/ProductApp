package com.example.productapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.productapp.models.Product;
import com.example.productapp.repository.OrderRepository;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        String productJson = getIntent().getStringExtra("selected_product");


        Gson gson = new Gson();
        Product product = gson.fromJson(productJson, Product.class);
        ImageView imageViewProduct = findViewById(R.id.imageView_product);
        TextView textViewTitle = findViewById(R.id.textView_title);
        TextView textViewPrice = findViewById(R.id.textView_price);
        TextView textViewDescription = findViewById(R.id.textView_description);
        TextView textViewQuantity = findViewById(R.id.tv_quanlity);

        // Set data to views
        Glide.with(getApplicationContext()).load(product.getHinhAnh()).into(imageViewProduct);
        textViewTitle.setText(product.getTenSP());
        textViewPrice.setText("$ " + String.valueOf(product.getGia()));
        textViewDescription.setText(product.getMoTa());


        ImageButton add = findViewById(R.id.btn_add);
        ImageButton minus = findViewById(R.id.btn_remove);
        AppCompatButton buttonBuy = findViewById(R.id.btn_buy);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(textViewQuantity.getText().toString());
                textViewQuantity.setText(String.valueOf(quantity +  1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(textViewQuantity.getText().toString());
                if (quantity == 1){
                    return;
                }
                textViewQuantity.setText(String.valueOf(quantity - 1));
            }
        });
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRepository orderRepository =  new OrderRepository(getApplicationContext());
                orderRepository.addOrderToCart(Session.getUserSession().getUserId(), product.getMaSP(), Integer.parseInt(textViewQuantity.getText().toString()), new OrderRepository.OrderAddCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
