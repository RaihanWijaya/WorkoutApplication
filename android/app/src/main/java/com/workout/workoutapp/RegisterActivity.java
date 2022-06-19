package com.workout.workoutapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.workout.workoutapp.request.ApiUtils;
import com.workout.workoutapp.request.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class: RegisterActivity
 * Description: This class is used to register to the application.
 */

public class RegisterActivity extends AppCompatActivity {
    Button registerButton, registerBack;
    EditText registerUsername, registerPassword, registerConfirmPassword;
    TextView loginClick;
    Context mContext;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerBack = (Button) findViewById(R.id.registerBack);
        registerUsername = (EditText) findViewById(R.id.registerUsername);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);
        loginClick = (TextView) findViewById(R.id.already_hav);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                String confirmPassword = registerConfirmPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (password.contains(" ") || confirmPassword.contains(" ")) {
                    Toast.makeText(RegisterActivity.this, "Password cannot contain spaces", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    requestRegister(username, password);
                }
            }
        });

        loginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    void requestRegister(String username, String password) {
        userService.registerRequest(username, password)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                assert response.body() != null;
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("User created")) {
                                    Toast.makeText(mContext, "User registered, please login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                } else {
                                    Toast.makeText(mContext, "Fail to register", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Fail to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });

    }
}