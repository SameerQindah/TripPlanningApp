package com.example.tripplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    private final String CORRECT_EMAIL = "sameer";
    private final String CORRECT_PASSWORD = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> checkLogin());
    }

    private void checkLogin() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();

        if (email.equals(CORRECT_EMAIL) && pass.equals(CORRECT_PASSWORD)) {

            Toast.makeText(this, "Welcome Sameer!", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish(); // عشان يمنع الرجوع للّوجن

        } else {
            Toast.makeText(this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
        }
    }
}
