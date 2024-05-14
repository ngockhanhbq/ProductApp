package com.example.productapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.productapp.R;
import com.example.productapp.adapters.CategoryAdapter;
import com.example.productapp.adapters.ProductAdapter;
import com.example.productapp.models.Category;
import com.example.productapp.models.Product;
import com.example.productapp.repository.CategoryRepository;
import com.example.productapp.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements  CategoryAdapter.CategorySelectionListener{
    private ViewFlipper viewFlipper;
    private List<String> imageUrls;

    GridView gridView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCategories);
        gridView = view.findViewById(R.id.gridViewProducts);
        gridView.setNumColumns(2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        CategoryRepository categoryRepository = new CategoryRepository(getContext());
        categoryRepository.getCategories(new CategoryRepository.CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                categoryList.add(0, new Category(-1,"Tất cả","https://inngochuong.com/uploads/images/mau-san-pham/mau-backgroud-dep-don-gian/mau-background.jpg"));
                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList,HomeFragment.this);
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onError(String message) {
                // Handle error
            }
        });

        ProductRepository productRepository = new ProductRepository(getContext());
        productRepository.getProducts(new ProductRepository.ProductCallback() {
            @Override
            public void onSuccess(List<Product> productList) {
                ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
                gridView.setAdapter(productAdapter);
            }

            @Override
            public void onError(String message) {
                // Handle error
            }
        },0);

        viewFlipper = view.findViewById(R.id.viewFlipper);
        // Initialize list of image URLs (replace these with actual URLs)
        imageUrls = new ArrayList<>();
        imageUrls.add("https://intphcm.com/data/upload/banner-my-pham-ngot-ngao.jpg");
        imageUrls.add("https://intphcm.com/data/upload/banner-my-pham-dep.jpg");
        imageUrls.add("https://intphcm.com/data/upload/banner-mac-truyen-thong.jpg");
        imageUrls.add("https://intphcm.com/data/upload/banner-my-pham-cap-bach.jpg");
        for (String imageUrl : imageUrls) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(imageUrl).into(imageView);
            viewFlipper.addView(imageView);
        }
        // Set flip interval and start flipping
        viewFlipper.setFlipInterval(2000); // Set flip interval in milliseconds (e.g., 3 seconds)
        viewFlipper.startFlipping();

        return view;
    }

    @Override
    public void onCategorySelected(Category category) {
        ProductRepository productRepository = new ProductRepository(getContext());
        productRepository.getProducts(new ProductRepository.ProductCallback() {
            @Override
            public void onSuccess(List<Product> productList) {
                ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
                gridView.setAdapter(productAdapter);
            }

            @Override
            public void onError(String message) {
                // Handle error
            }
        },category.getMaLoaiSP());
    }
}
