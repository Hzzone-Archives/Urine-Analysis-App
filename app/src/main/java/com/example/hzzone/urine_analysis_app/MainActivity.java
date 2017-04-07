package com.example.hzzone.urine_analysis_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start_button = (Button)findViewById(R.id.start_button);
        Button stop_button = (Button)findViewById(R.id.stop_button);
        start_button.setOnClickListener(this);
        stop_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:
                startService(new Intent(this, ChatService.class));
                break;
            case R.id.stop_button:
                stopService(new Intent(this, ChatService.class));
                break;
            default:
                Log.d(TAG, "onClick: error");
                break;

        }

    }
}
