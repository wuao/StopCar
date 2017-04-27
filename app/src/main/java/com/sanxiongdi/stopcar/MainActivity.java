package com.sanxiongdi.stopcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sanxiongdi.stopcar.activity.IndexActivity;


public class MainActivity extends AppCompatActivity {

    private Button startselect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startselect=(Button)findViewById(R.id.startselect);
        startselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IndexActivity.class));
            }
        });
    }
}
