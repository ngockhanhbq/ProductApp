package com.example.productapp.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.productapp.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private Context context;
    private RequestQueue requestQueue;
    private String baseUrl = "http://10.0.2.2/api/";

    public CategoryRepository(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void getCategories(CategoryCallback callback) {
        String url = baseUrl + "loaisp.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Category> categoryList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int maLoaiSP = jsonObject.getInt("MaLoaiSP");
                                String tenLoaiSP = jsonObject.getString("TenLoaiSP");
                                String hinhanh = jsonObject.getString("HinhAnh");
                                Category category = new Category(maLoaiSP, tenLoaiSP, hinhanh);
                                categoryList.add(category);
                                System.out.println(response);
                            } catch (JSONException e) {
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(categoryList);
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

    public interface CategoryCallback {
        void onSuccess(List<Category> categoryList);
        void onError(String message);
    }
}
