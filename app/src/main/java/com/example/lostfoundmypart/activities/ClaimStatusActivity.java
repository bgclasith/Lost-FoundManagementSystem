package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ClaimStatusActivity extends AppCompatActivity {

    private RecyclerView rvClaims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_status);

        rvClaims = findViewById(R.id.rv_claims);
        rvClaims.setLayoutManager(new LinearLayoutManager(this));
        
        // Sample Data
        List<Claim> claimList = new ArrayList<>();
        claimList.add(new Claim("iPhone 13", "2023-10-25", "Pending"));
        claimList.add(new Claim("Leather Wallet", "2023-10-20", "Approved"));
        claimList.add(new Claim("Car Keys", "2023-10-18", "Rejected"));

        ClaimAdapter adapter = new ClaimAdapter(claimList);
        rvClaims.setAdapter(adapter);
    }
}