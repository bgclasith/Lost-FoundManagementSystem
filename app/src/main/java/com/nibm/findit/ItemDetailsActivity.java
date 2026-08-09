package com.nibm.findit;
import com.nibm.findit.admin.models.Item;
import com.nibm.findit.admin.adapters.ItemAdapter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Item Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView ivItemDetailImage = findViewById(R.id.ivItemDetailImage);
        TextView tvDetailItemName = findViewById(R.id.tvDetailItemName);
        TextView tvDetailStatus = findViewById(R.id.tvDetailStatus);
        TextView tvDetailCategory = findViewById(R.id.tvDetailCategory);
        TextView tvDetailLocation = findViewById(R.id.tvDetailLocation);
        TextView tvDetailDate = findViewById(R.id.tvDetailDate);
        TextView tvDetailDescription = findViewById(R.id.tvDetailDescription);
        TextView tvDetailReporterName = findViewById(R.id.tvDetailReporterName);
        TextView tvDetailContact = findViewById(R.id.tvDetailContact);
        Button btnClaimItem = findViewById(R.id.btnClaimItem);

        Item item = (Item) getIntent().getSerializableExtra("ITEM_DATA");

        if (item != null) {
            tvDetailItemName.setText(item.getTitle());
            tvDetailCategory.setText("Category: " + item.getCategory());
            tvDetailLocation.setText("Location: " + item.getLocation());
            tvDetailDate.setText("Date: " + item.getDate());
            tvDetailDescription.setText(item.getDescription());
            tvDetailReporterName.setText("Reporter: " + item.getUserName());
            tvDetailContact.setText("Contact: " + item.getUserPhone());

            // Style tvDetailStatus as a pill badge dynamically
            tvDetailStatus.setText(item.getStatus());
            if (item.getStatus() != null) {
                if (item.getStatus().equalsIgnoreCase("Lost")) {
                    tvDetailStatus.setTextColor(android.graphics.Color.parseColor("#DC2626"));
                    tvDetailStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FEE2E2")));
                    btnClaimItem.setText("Contact Owner");
                    btnClaimItem.setEnabled(true);
                } else if (item.getStatus().equalsIgnoreCase("Found")) {
                    tvDetailStatus.setTextColor(android.graphics.Color.parseColor("#16A34A"));
                    tvDetailStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#D1FAE5")));
                    btnClaimItem.setText("Claim Item");
                    btnClaimItem.setEnabled(true);
                } else {
                    tvDetailStatus.setTextColor(android.graphics.Color.parseColor("#9333EA"));
                    tvDetailStatus.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#F3E8FF")));
                    btnClaimItem.setText("Already Claimed");
                    btnClaimItem.setEnabled(false);
                }
            }

            btnClaimItem.setOnClickListener(v -> {
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle(item.getStatus().equalsIgnoreCase("Lost") ? "Contact Owner" : "Claim Item")
                        .setMessage("To contact the reporter, please call or message:\n\nName: " 
                                + item.getUserName() + "\nPhone: " + item.getUserPhone())
                        .setPositiveButton("Call", (dialog, which) -> {
                            try {
                                android.content.Intent dialIntent = new android.content.Intent(android.content.Intent.ACTION_DIAL);
                                dialIntent.setData(android.net.Uri.parse("tel:" + item.getUserPhone()));
                                startActivity(dialIntent);
                            } catch (Exception e) {
                                android.widget.Toast.makeText(this, "Could not open dialer.", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Close", null)
                        .show();
            });

            Glide.with(this)
                    .load(item.getImageUri())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .fallback(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(ivItemDetailImage);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
