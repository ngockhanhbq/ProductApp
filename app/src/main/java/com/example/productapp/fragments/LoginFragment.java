package com.example.productapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productapp.AuthActivity;
import com.example.productapp.MainActivity;
import com.example.productapp.Session;
import com.example.productapp.R;
import com.example.productapp.models.User;
import com.example.productapp.repository.UserRepository;
import com.example.productapp.services.UserSession;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment {

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Find the TextView for Đăng ký
        TextInputEditText usernameEditText = view.findViewById(R.id.editTextUsername);
        TextInputEditText passwordEditText = view.findViewById(R.id.editTextPassword);
        TextView registerTextView = view.findViewById(R.id.tv_register);
        AppCompatButton loginButton = view.findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                UserRepository userRepository = new UserRepository(getContext());

                userRepository.login(username, password, new UserRepository.UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        UserSession userSession = Session.getUserSession();
                        userSession.setUsername(user.getUsername());
                        userSession.setUserId(user.getUserID());
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        // Set a click listener to the register TextView
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthActivity) getActivity()).loadRegisterFragment();
            }
        });

        return view;
    }
}
