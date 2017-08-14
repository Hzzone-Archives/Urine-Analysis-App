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

import edu.scu.hzzon.urine_analysis_app.R;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private Button register_button = null;
    private EditText input_phone_number = null;
    private EditText input_verify_code = null;
    private Button get_code_button = null;

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
        get_code_button = (Button)findViewById(R.id.get_code_button);

        // Listener
        register_button.setOnClickListener(register_button_listener);
        get_code_button.setOnClickListener(get_code_button_listener);
    }

    private View.OnClickListener register_button_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String code = input_verify_code.getText().toString();
            String phone_number  = input_phone_number.getText().toString();
            if (code==null || phone_number==null){
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("warning").setMessage("手机号或验证码不能为空").setPositiveButton("确定", null).show();
            }else {
                Boolean flag = false;
                // TODO 判断验证码是否正确

                if (flag){
                    // TODO 直接进入主界面
                }else {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("warning").setMessage("验证码错误").setPositiveButton("确定", null).show();
                }
            }
        }
    };

    private View.OnClickListener get_code_button_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO 发送验证码
        }
    };
}
