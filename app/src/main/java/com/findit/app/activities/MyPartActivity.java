package com.findit.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.findit.app.R;
import com.findit.app.models.User;
import com.findit.app.utils.SessionManager;

public class MyPartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_part);

        // Ensure we have a mock user session so that claim submission and notifications work.
        SessionManager sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            User mockUser = new User();
            mockUser.setId("mock_user_id_123");
            mockUser.setFullName("Test User");
            mockUser.setEmail("test@user.com");
            mockUser.setPhone("0712345678");
            mockUser.setRole("USER");
            sessionManager.createLoginSession(mockUser);
        }

        Button btnTestSubmitClaim = findViewById(R.id.btnTestSubmitClaim);
        Button btnTestNotifications = findViewById(R.id.btnTestNotifications);

        btnTestSubmitClaim.setOnClickListener(v -> {
            Intent intent = new Intent(MyPartActivity.this, SubmitClaimActivity.class);
            // Mock item data for the claim activity
            intent.putExtra("ITEM_ID", "mock_item_id_999");
            intent.putExtra("ITEM_TITLE", "Test Item (Keys)");
            startActivity(intent);
        });

        btnTestNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(MyPartActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });
    }
}
