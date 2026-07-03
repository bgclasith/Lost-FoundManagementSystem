package com.example.lostfoundmypart.activities;

import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lostfoundmypart.admin.models.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText fullName, email, phone, password;
    Button registerBtn;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);

        databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("Users");

        registerBtn.setOnClickListener(v -> {

            String userEmail = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            if(userEmail.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(
                    userEmail,
                    userPassword
            ).addOnCompleteListener(task -> {

                if(task.isSuccessful()){

                    String uid =
                            auth.getCurrentUser().getUid();

                    User user = new User(
                            fullName.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString()
                    );

                    databaseReference
                            .child(uid)
                            .setValue(user)
                            .addOnCompleteListener(task1 -> {

                                Toast.makeText(
                                        RegisterActivity.this,
                                        "Registration Successful",
                                        Toast.LENGTH_SHORT
                                ).show();

                                startActivity(
                                        new Intent(
                                                RegisterActivity.this,
                                                LoginActivity.class
                                        )
                                );

                                finish();

                            });

                }else{

                    Toast.makeText(
                            RegisterActivity.this,
                            task.getException().getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }

            });

        });
    }
}