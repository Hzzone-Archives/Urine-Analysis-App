package edu.scu.hzzon.urine_analysis_app.Activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.kymjs.rxvolley.*;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import edu.scu.hzzon.urine_analysis_app.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private String login_url = "119.29.16.200:6789/auth/login";
    //控件
    private Toolbar toolbar = null;
    private EditText input_phone_number = null;
    private EditText input_password = null;
    private Button login_button = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        input_password = (EditText) findViewById(R.id.login_input_password);
        input_phone_number = (EditText) findViewById(R.id.login_input_phone_number);
        setSupportActionBar(toolbar);
        toolbar.setTitle("登陆");
        
        login_button.setOnClickListener(login_listener);
    }
    
    //登陆按钮监听器
    private View.OnClickListener login_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO 登陆验证
            String user_id = input_phone_number.getText().toString();
            String user_password = input_password.getText().toString();
            is_password_effective(user_id, user_password);
            
        }
    };

    /**
     * 验证密码是否有效，不为空
     * @param id
     * @param password
     */
    private void is_password_effective(String id, String password){
        if (id==null || password==null){
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("warning").setMessage("手机号或密码不能为空").setPositiveButton("确定", null).show();
        } else {
            login(id, password);
        }
    }

    /**
     * 登录
     * @param id
     * @param password
     */
    private void login(String id, String password){
        //TODO 登陆验证
        Log.d(TAG, "login: ");
        final String temp = id;
        HttpParams params = new HttpParams();
        params.put("email", id);
        params.put("password", password);
        params.put("remember_me", "y");
        params.put("submit", "login");
        RxVolley.post(login_url, params, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Log.d(TAG, "onSuccess: " + t);
                if(t.equals("ok")){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("登录成功").setMessage("转入主界面").setPositiveButton("确定", null).show();
                    //TODO 转入主界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("id", temp);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("登录失败").setMessage("用户名或密码错误").setPositiveButton("确定", null).show();
                }
            }
        });
    }
}
