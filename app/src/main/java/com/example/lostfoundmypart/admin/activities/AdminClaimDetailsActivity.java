package com.example.lostfoundmypart.admin.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.lostfoundmypart.R;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lostfoundmypart.admin.models.Notification;

public class AdminClaimDetailsActivity extends AppCompatActivity {

    TextView txtItemName,
            txtClaimantName,
            txtEmail,
            txtProof,
            txtStatus;

    ImageView imgDetailsItem;
    Button btnApprove,
            btnReject;

    String claimId;
    String claimantId;
    String itemId;
    String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_claim_details);

        txtItemName = findViewById(R.id.txtItemName);
        txtClaimantName = findViewById(R.id.txtClaimantName);
        txtEmail = findViewById(R.id.txtEmail);
        txtProof = findViewById(R.id.txtProof);
        txtStatus = findViewById(R.id.txtStatus);

        imgDetailsItem = findViewById(R.id.imgDetailsItem);

        btnApprove = findViewById(R.id.btnApprove);
        btnReject = findViewById(R.id.btnReject);

        // Intent Data
        claimId = getIntent().getStringExtra("claimId");
        claimantId = getIntent().getStringExtra("claimantId");
        itemId = getIntent().getStringExtra("itemId");
        itemName = getIntent().getStringExtra("itemName");

        txtItemName.setText(itemName);
        txtClaimantName.setText(getIntent().getStringExtra("claimantName"));
        txtEmail.setText(getIntent().getStringExtra("claimantEmail"));
        txtProof.setText(getIntent().getStringExtra("proof"));
        txtStatus.setText(getIntent().getStringExtra("status"));

        // Load Image
        String itemImage = getIntent().getStringExtra("itemImage");

        if (itemImage != null && !itemImage.isEmpty()) {

            Glide.with(this)
                    .load(itemImage)
                    .placeholder(R.drawable.ic_found)
                    .into(imgDetailsItem);

        } else {

            imgDetailsItem.setImageResource(R.drawable.ic_found);

        }

        // APPROVE
        btnApprove.setOnClickListener(v -> {

            FirebaseDatabase db = FirebaseDatabase.getInstance();

            db.getReference("Claims")
                    .child(claimId)
                    .child("status")
                    .setValue("Approved");

            db.getReference("LostItems")
                    .child(itemId)
                    .child("status")
                    .setValue("Recovered");

            db.getReference("Notifications")
                    .child(claimantId)
                    .push()
                    .setValue(new Notification(
                            "Claim Approved",
                            "Your claim for " + itemName + " was approved.",
                            "Today"
                    ));

            Toast.makeText(
                    this,
                    "Claim Approved",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        });

        // REJECT
        btnReject.setOnClickListener(v -> {

            FirebaseDatabase db = FirebaseDatabase.getInstance();

            db.getReference("Claims")
                    .child(claimId)
                    .child("status")
                    .setValue("Rejected");

            db.getReference("Notifications")
                    .child(claimantId)
                    .push()
                    .setValue(new Notification(
                            "Claim Rejected",
                            "Your claim for " + itemName + " was rejected.",
                            "Today"
                    ));

            Toast.makeText(
                    this,
                    "Claim Rejected",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        });

    }
}