package com.example.reminderapplicatiom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class splashactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay for 2 seconds (2000 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Move from SplashActivity to LoginActivity
                Intent intent = new Intent(splashactivity.this, LoginActivity.class);
                startActivity(intent);

                // Close SplashActivity so user can't go back to it
                finish();
            }
        }, 2000);
    }
}