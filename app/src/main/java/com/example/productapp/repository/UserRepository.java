package com.example.productapp.repository;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.productapp.models.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRepository {
    private RequestQueue requestQueue;
    private String baseUrl = "http://10.0.2.2/api/";

    public UserRepository(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void login(String username, String password, final UserCallback callback) {
        String url = baseUrl + "login.php";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if login was successful
                            boolean success = response.getBoolean("success");
                            if (success) {
                                // Login successful, get user data
                                JSONObject userJson = response.getJSONObject("user");
                                User user = new User(
                                        userJson.getInt("UserID"),
                                        userJson.getString("Username"),
                                        userJson.getString("Password")
                                        // Add more fields as needed
                                );
                                // Pass the user object to the callback
                                callback.onSuccess(user);
                            } else {
                                // Login failed, get error message
                                String message = response.getString("message");
                                callback.onError(message);
                            }
                        } catch (JSONException e) {
                            // JSON parsing error
                            callback.onError("JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Volley error
                        callback.onError("Volley error: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    // Method to register
    public void register(String username, String password, final UserCallback callback) {
        String url = baseUrl + "register.php";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if login was successful
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONObject userJson = response.getJSONObject("user");
                                User user = new User(
                                        userJson.getInt("UserID"),
                                        userJson.getString("Username"),
                                        userJson.getString("Password")
                                );
                                callback.onSuccess(user);
                            } else {
                                String message = response.getString("message");
                                callback.onError(message);
                            }
                        } catch (JSONException e) {
                            // JSON parsing error
                            callback.onError("JSON parsing error: " + e.getMessage());
                        }
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

    // Method to load image using Picasso
    public void loadImage(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    public interface UserCallback {
        void onSuccess(User response);
        void onError(String message);
    }
}
