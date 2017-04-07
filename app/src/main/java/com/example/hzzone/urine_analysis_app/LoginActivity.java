package com.example.hzzone.urine_analysis_app;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private EditText email_input, password_input;
    private Button signUpButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.d(TAG, "handleMessage: " + "1");
                    break;
                default:
                    Log.d(TAG, "handleMessage: error");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password_input = (EditText)findViewById(R.id.email_edit_input);
        email_input = (EditText)findViewById(R.id.email_edit_input);
        signUpButton = (Button)findViewById(R.id.confirm_button);
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = password_input.getText().toString();
                final String email = email_input.getText().toString();
                if(email=="" || password=="")
                    Toast.makeText(LoginActivity.this, "请输入账号或密码", Toast.LENGTH_LONG);
                else {
                    new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "onCreate: ");
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("email", "2@outlook.com")
                                .add("username", "hzzone")
                                .add("password", "123")
                                .add("password2", "123")
                                .add("submit", "Register")
                                .build();
                        Request request = new Request.Builder()
                                .url("http:119.29.16.200:5000/auth/register")
                                .post(requestBody)
                                .build();
                        Response response;
                        try {
                            response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                String result = response.body().string();
                                Log.d(TAG, "run: " + result);
                                if (result=="login_ok") {
                                    intent.putExtra("email", email);
                                    intent.putExtra("name", "hzzone");
                                    startActivity(intent);
                                } else
                                    Toast.makeText(LoginActivity.this, "账户或密码错误", Toast.LENGTH_LONG);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "未联网", Toast.LENGTH_LONG);
                                Log.d(TAG, "run: " + "failed");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                }
            }
        });
    }
}
