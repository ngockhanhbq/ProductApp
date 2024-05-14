package com.example.productapp.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.productapp.models.Order;
import com.example.productapp.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private Context context;
    private RequestQueue requestQueue;
    private String baseUrl = "http://10.0.2.2/api/";

    public OrderRepository(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public void deleteOrder(int orderId, OrderDeleteCallback callback) {
        String url = baseUrl + "xoadonhang.php";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public interface OrderDeleteCallback {
        void onSuccess(String message);
        void onError(String error);
    }
    public void getOrders(int userId, OrderCallback callback) {
        String url = baseUrl + "donhang.php?userid=" + userId;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Order> orderList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int maChiTiet = jsonObject.getInt("MaChiTiet");
                                JSONObject productJson = jsonObject.getJSONObject("Product");
                                Product product = new Product(
                                        productJson.getInt("MaSP"),
                                        productJson.getString("TenSP"),
                                        productJson.getDouble("Gia"),
                                        productJson.getString("MoTa"),
                                        productJson.getString("HinhAnh"),
                                        productJson.getInt("MaLoaiSP")
                                );
                                int userId = jsonObject.getInt("UserId");
                                int soLuong = jsonObject.getInt("SoLuong");
                                Order order = new Order(maChiTiet, product,  soLuong,userId);
                                orderList.add(order);
                            } catch (JSONException e) {
                                callback.onError(e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(orderList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

// Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);

    }

    public interface OrderCallback {
        void onSuccess(List<Order> orderList);
        void onError(String message);
    }

    public void addOrderToCart(int userId, int productId, int quantity, OrderAddCallback callback) {
        String url = baseUrl + "themdonhang.php";

        // Create JSON object to hold order information
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userId);
            jsonObject.put("productid", productId);
            jsonObject.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        // Create JsonObjectRequest to send the data to the server
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    public interface OrderAddCallback {
        void onSuccess(String message);
        void onError(String error);
    }
}
