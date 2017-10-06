package edu.scu.hzzon.urine_analysis_app.Activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import edu.scu.hzzon.urine_analysis_app.R;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    //登录url
    private static final String register_url = "119.29.16.200:6789/auth/register";
    private Button register_button = null;
    private EditText input_phone_number = null;
    private EditText input_verify_code = null;
    private EditText input_password = null;
    private Button get_code_button = null;
    // 本地验证码保存
    private static String local_code = "666666";

    // 判断注册是否成功
    private static Boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // return button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: return button");
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        // TODO set return button context

        register_button = (Button)findViewById(R.id.register_button);
        input_phone_number = (EditText)findViewById(R.id.input_phone_number);
        input_verify_code = (EditText)findViewById(R.id.input_verify_code);
        input_password = (EditText)findViewById(R.id.input_password);
        get_code_button = (Button)findViewById(R.id.get_code_button);

        // Listener
        register_button.setOnClickListener(register_button_listener);
        get_code_button.setOnClickListener(get_code_button_listener);
    }

    /**
     * 注册按钮
     * 验证输入是否有效，并且验证码是否正确，正确则将id传给主界面
     */
    private View.OnClickListener register_button_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String code = input_verify_code.getText().toString();
            String phone_number  = input_phone_number.getText().toString();
            String password  = input_password.getText().toString();
            if (code==null || phone_number==null || password==null){
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("warning").setMessage("手机号或验证码不能为空").setPositiveButton("确定", null).show();
            }else {
                Boolean flag = false;
                // TODO 判断验证码是否正确
                flag = is_code_effective(code);
                HttpParams params = new HttpParams();
                params.put("username", "1111");
                params.put("email", phone_number);
                params.put("password", password);
                params.put("password2", password);
                params.put("submit", "register");
                RxVolley.post(register_url, params, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        /**
                         * 验证用户已存在，并且success表示是否存在
                         */
                        if (t.equals("fail")) {
                            success = false;
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("注册失败").setMessage("用户已存在").setPositiveButton("确定", null).show();
                        }
                        else
                            success = true;
                    }
                });
                /**
                 * flag验证码是否正确，同时正确且注册成功才跳转
                 */
                if (flag && success){
                    // TODO 直接进入主界面
                    // TODO 传送给服务器注册
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("注册成功").setMessage("手机号或验证码不能为空").setPositiveButton("确定", null).show();
                    // TODO 只有注册成功或失败，没有用户是否存在？？
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("id", phone_number);
                    startActivity(intent);
                }else if (!flag && success){
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("注册失败").setMessage("验证码错误").setPositiveButton("确定", null).show();
                }
            }
        }
    };

    /**
     * 获取验证码按钮监听
     */
    private View.OnClickListener get_code_button_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO 发送验证码
            // 并且获得返回的验证码
            local_code = "666666";
        }
    };

    /**
     * 验证验证码是否正确
     */
    private Boolean is_code_effective(String code){
        if (local_code.equals(code))
            return true;
        else
            return false;
    }
}
