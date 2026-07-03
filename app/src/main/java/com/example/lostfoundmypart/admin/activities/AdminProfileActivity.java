package com.example.lostfoundmypart.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundmypart.R;
import com.example.lostfoundmypart.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AlertDialog;

public class AdminProfileActivity extends AppCompatActivity {

    TextView txtName, txtEmail;

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        btnLogout = findViewById(R.id.btnLogout);

        FirebaseUser user =
                FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){

            if(user.getDisplayName() != null){

                txtName.setText(user.getDisplayName());

            }else{

                txtName.setText("Administrator");

            }

            txtEmail.setText(user.getEmail());

        }

        btnLogout.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(
                                AdminProfileActivity.this,
                                LoginActivity.class);

                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        finish();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        });

    }
}