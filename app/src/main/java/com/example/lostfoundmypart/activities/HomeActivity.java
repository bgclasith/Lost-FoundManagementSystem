package com.example.lostfoundmypart.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundmypart.R;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    MaterialCardView cardProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardProfile = findViewById(R.id.cardProfile);
    }
}