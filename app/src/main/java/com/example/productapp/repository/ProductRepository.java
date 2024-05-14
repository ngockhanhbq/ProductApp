package com.example.productapp.repository;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.productapp.models.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private Context context;
    private RequestQueue requestQueue;
    private String baseUrl = "http://10.0.2.2/api/sanpham.php";

    public ProductRepository(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getProducts(ProductCallback callback,int categoryId) {
        if (categoryId > 0){
            baseUrl =  baseUrl + "?category=" + categoryId;
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, baseUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
                        try {
                            List<Product> productList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Parse product data from JSON object
                                int productId = jsonObject.getInt("MaSP");
                                String productName = jsonObject.getString("TenSP");
                                double productPrice = jsonObject.getDouble("Gia");
                                String productDescription = jsonObject.getString("MoTa");
                                String productImage = jsonObject.getString("HinhAnh");
                                int categoryCode = jsonObject.getInt("MaLoaiSP");
                                // Create Product object
                                Product product = new Product(productId, productName, productPrice, productDescription, productImage,categoryCode);
                                productList.add(product);

                            }
                            // Pass the list of products to the callback
                            callback.onSuccess(productList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error parsing JSON response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        error.printStackTrace();
                        callback.onError("Volley error: " + error.getMessage());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public interface ProductCallback {
        void onSuccess(List<Product> productList);
        void onError(String message);
    }
}
