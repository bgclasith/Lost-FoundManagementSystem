package com.example.lostfoundmypart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundmypart.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    MaterialCardView cardProfile, cardLost, cardFound, cardReports;
    Button logoutBtn;

    FirebaseAuth auth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // 🔥 If user not logged in → send to login
        if (currentUser == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        // views
        cardProfile = findViewById(R.id.cardProfile);
        cardLost = findViewById(R.id.cardLost);
        cardFound = findViewById(R.id.cardFound);
        cardReports = findViewById(R.id.cardReports);
        logoutBtn = findViewById(R.id.logoutBtn);

        // logout
        logoutBtn.setOnClickListener(v -> {

            auth.signOut();

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        });

        // card clicks (optional)
        cardProfile.setOnClickListener(v ->
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        );

        cardLost.setOnClickListener(v ->
                Toast.makeText(this, "Lost Item", Toast.LENGTH_SHORT).show()
        );

        cardFound.setOnClickListener(v ->
                Toast.makeText(this, "Found Item", Toast.LENGTH_SHORT).show()
        );

        cardReports.setOnClickListener(v ->
                Toast.makeText(this, "Reports", Toast.LENGTH_SHORT).show()
        );
    }

    // 🔥 auto login protection
    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}