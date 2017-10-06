package edu.scu.hzzon.urine_analysis_app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.scu.hzzon.urine_analysis_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Toast.makeText(MainActivity.this, id,Toast.LENGTH_SHORT).show();
    }
}
