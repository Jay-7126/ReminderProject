package com.example.reminderapplicatiom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editPassword;
    private String savedPassword;
    private static final String PREFS_NAME = "login_prefs";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editPassword = findViewById(R.id.editPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Load existing password
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        savedPassword = prefs.getString(KEY_PASS, null);

        if (savedPassword == null) {
            btnLogin.setText("Set Password");
        }

        btnLogin.setOnClickListener(v -> {
            String input = editPassword.getText().toString();

            if (savedPassword == null) {
                // First time setup
                if (input.length() < 4) {
                    Toast.makeText(this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    prefs.edit().putString(KEY_PASS, input).apply();
                    goToMain();
                }
            } else {
                // Regular Login
                if (input.equals(savedPassword)) {
                    goToMain();
                } else {
                    Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Closes LoginActivity so user can't "back" into it
    }
}