package com.example.productapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import com.example.productapp.fragments.LoginFragment;
import com.example.productapp.fragments.RegisterFragment;

public class AuthActivity extends AppCompatActivity {

    TextView tv_register, tv_login;
    AppCompatButton btn_register,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    // Method to replace the fragment with the RegisterFragment
    public void loadRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RegisterFragment())
                .addToBackStack(null)  // Add to back stack to handle back navigation
                .commit();
    }
    public void loadLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .addToBackStack(null)  // Add to back stack to handle back navigation
                .commit();
    }
}
