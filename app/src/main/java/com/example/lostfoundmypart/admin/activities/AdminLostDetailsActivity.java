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

public class AdminLostDetailsActivity extends AppCompatActivity {

    TextView txtItemName,
            txtDescription,
            txtLocation,
            txtDate,
            txtStatus;

    ImageView imgDetailsItem;
    Button btnApprove,
            btnDelete;

    String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lost_details);

        txtItemName = findViewById(R.id.txtItemName);
        txtDescription = findViewById(R.id.txtDescription);
        txtLocation = findViewById(R.id.txtLocation);
        txtDate = findViewById(R.id.txtDate);
        txtStatus = findViewById(R.id.txtStatus);
        imgDetailsItem = findViewById(R.id.imgDetailsItem);
        btnApprove = findViewById(R.id.btnApprove);
        btnDelete = findViewById(R.id.btnDelete);

        itemId = getIntent().getStringExtra("itemId");

        txtItemName.setText(
                getIntent().getStringExtra("itemName"));

        txtDescription.setText(
                getIntent().getStringExtra("description"));

        txtLocation.setText(
                getIntent().getStringExtra("location"));

        txtDate.setText(
                getIntent().getStringExtra("dateLost"));

        txtStatus.setText(
                getIntent().getStringExtra("status"));

        // Load Image
        String itemImage =
                getIntent().getStringExtra("itemImage");

        if (itemImage != null && !itemImage.isEmpty()) {

            Glide.with(this)
                    .load(itemImage)
                    .placeholder(R.drawable.ic_found)
                    .into(imgDetailsItem);

        } else {

            imgDetailsItem.setImageResource(
                    R.drawable.ic_found);
        }

        // Approve
        btnApprove.setOnClickListener(v -> {

            FirebaseDatabase.getInstance()
                    .getReference("LostItems")
                    .child(itemId)
                    .child("status")
                    .setValue("Approved");

            Toast.makeText(
                    this,
                    "Lost Item Approved",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        });

        // Delete
        btnDelete.setOnClickListener(v -> {

            FirebaseDatabase.getInstance()
                    .getReference("LostItems")
                    .child(itemId)
                    .removeValue();

            Toast.makeText(
                    this,
                    "Lost Item Deleted",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        });

    }
}