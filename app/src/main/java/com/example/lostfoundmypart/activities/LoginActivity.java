package com.example.lostfoundmypart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.activities.HomeActivity;
import com.example.lostfoundmypart.admin.activities.AdminActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView registerText;
    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerText = findViewById(R.id.registerText);
        progressBar = findViewById(R.id.progressBar); // add in XML

        loginBtn.setOnClickListener(v -> loginUser());

        registerText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {

        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(userEmail)) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setEnabled(false);

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {

                    progressBar.setVisibility(View.GONE);
                    loginBtn.setEnabled(true);

                    if (task.isSuccessful()) {

                        String currentEmail = auth.getCurrentUser().getEmail();

                        // ADMIN CHECK
                        if ("admin@gmail.com".equalsIgnoreCase(currentEmail)) {

                            Toast.makeText(this, "Admin Login Success", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(
                                    LoginActivity.this,
                                    AdminActivity.class
                            ));

                        } else {

                            Toast.makeText(this, "User Login Success", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(
                                    LoginActivity.this,
                                    HomeActivity.class
                            ));
                        }

                        finish();

                    } else {

                        Toast.makeText(
                                LoginActivity.this,
                                "Login Failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    // AUTO LOGIN
    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null) {

            String email = auth.getCurrentUser().getEmail();

            if ("admin@gmail.com".equalsIgnoreCase(email)) {

                startActivity(new Intent(this, AdminActivity.class));

            } else {

                startActivity(new Intent(this, HomeActivity.class));
            }

            finish();
        }
    }
}