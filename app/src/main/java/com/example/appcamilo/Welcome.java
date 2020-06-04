package com.example.appcamilo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Welcome extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent siguiente =new Intent(Welcome.this,MainActivity.class);
                startActivity(siguiente);
                finish();
            };
        }, SPLASH_TIME_OUT);
    }
}
