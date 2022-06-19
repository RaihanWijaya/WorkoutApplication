package com.workout.workoutapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
 * Class: LoginActivity
 * Description: This class is used to login to the application.
 */

public class LoginActivity extends AppCompatActivity {
    Button loginButton, loginBack;
    EditText loginUsername, loginPassword;
    TextView registerClick;
    Context mContext;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginBack = (Button) findViewById(R.id.loginBack);
        loginUsername = (EditText) findViewById(R.id.loginUsername);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        registerClick = (TextView) findViewById(R.id.loginRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(mContext, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    requestLogin(username, password);
                }
            }
        });

        registerClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    void requestLogin(String username, String password) {
        userService.loginRequest(username, password)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("message").equals("authorized")){
                                    Toast.makeText(mContext, "Login success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Fail to login", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}