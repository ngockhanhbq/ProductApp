package com.example.productapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.productapp.MainActivity;
import com.example.productapp.R;
import com.example.productapp.Session;
import com.example.productapp.models.User;
import com.example.productapp.repository.UserRepository;
import com.example.productapp.services.UserSession;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        TextView loginTextView = view.findViewById(R.id.tv_login);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment())
                        .addToBackStack(null) // Optional: Adds the transaction to the back stack
                        .commit();
            }
        });

        TextInputEditText usernameEditText = view.findViewById(R.id.editTextUsernameR);
        TextInputEditText passwordEditText = view.findViewById(R.id.editTextPasswordR);
        AppCompatButton loginButton = view.findViewById(R.id.buttonRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                UserRepository userRepository = new UserRepository(getContext());

                userRepository.register(username, password, new UserRepository.UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        UserSession userSession = Session.getUserSession();
                        userSession.setUsername(user.getUsername());
                        userSession.setUserId(user.getUserID());
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
