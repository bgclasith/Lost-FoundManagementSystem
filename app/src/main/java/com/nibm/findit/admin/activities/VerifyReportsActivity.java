package com.nibm.findit.admin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.findit.R;
import com.nibm.findit.admin.adapters.ItemAdapter;
import com.nibm.findit.admin.database.FirestoreHelper;
import com.nibm.findit.admin.models.Item;

import java.util.List;

public class VerifyReportsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RecyclerView rvReports;
    private TextView tvEmptyReports;

    private FirestoreHelper dbHelper;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_reports);

        dbHelper = new FirestoreHelper();

        btnBack = findViewById(R.id.btnBack);
        rvReports = findViewById(R.id.rvReports);
        tvEmptyReports = findViewById(R.id.tvEmptyReports);

        btnBack.setOnClickListener(v -> finish());

        rvReports.setLayoutManager(new LinearLayoutManager(this));

        loadReports();
    }

    private void loadReports() {
        // Load all active items for verification
        dbHelper.searchAndFilterItems("", "All", "", "", "ALL", "ALL", "Newest",
                new FirestoreHelper.Callback<List<Item>>() {
                    @Override
                    public void onSuccess(List<Item> items) {
                        if (items.isEmpty()) {
                            tvEmptyReports.setVisibility(View.VISIBLE);
                            rvReports.setVisibility(View.GONE);
                        } else {
                            tvEmptyReports.setVisibility(View.GONE);
                            rvReports.setVisibility(View.VISIBLE);

                            adapter = new ItemAdapter(VerifyReportsActivity.this, items, item -> {
                            });

                            adapter.setActionListener("Remove Fraudulent Report", item -> {
                                dbHelper.deleteItem(item.getId(), new FirestoreHelper.Callback<Boolean>() {
                                    @Override
                                    public void onSuccess(Boolean deleted) {
                                        if (deleted) {
                                            Toast.makeText(VerifyReportsActivity.this, "Fraudulent report removed!",
                                                    Toast.LENGTH_SHORT).show();
                                            loadReports();
                                        } else {
                                            Toast.makeText(VerifyReportsActivity.this, "Failed to remove report.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(VerifyReportsActivity.this, "Error: " + e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            });

                            rvReports.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        tvEmptyReports.setText("Failed to load reports");
                        tvEmptyReports.setVisibility(View.VISIBLE);
                    }
                });
    }
}
