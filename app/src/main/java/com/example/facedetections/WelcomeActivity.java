package com.example.facedetections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportActionBar().hide();


        Thread thread = new Thread() {

            public void run() {
                try {
                    sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };
        thread.start();
    }
}