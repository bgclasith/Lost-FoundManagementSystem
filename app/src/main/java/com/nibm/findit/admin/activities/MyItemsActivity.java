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
import com.nibm.findit.admin.utils.SessionManager;

import java.util.List;

public class MyItemsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RecyclerView rvMyItems;
    private TextView tvEmptyState;

    private FirestoreHelper firestoreHelper;
    private SessionManager sessionManager;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_items);

        firestoreHelper = new FirestoreHelper();
        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.btnBack);
        rvMyItems = findViewById(R.id.rvMyItems);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        btnBack.setOnClickListener(
                v -> finish()
        );

        rvMyItems.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadMyItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadMyItems();
    }

    private void loadMyItems() {

        String userId =
                sessionManager.getUserId();

        if (userId == null || userId.isEmpty()) {
            return;
        }

        firestoreHelper.getItemsByUserId(
                userId,
                new FirestoreHelper.Callback<List<Item>>() {

                    @Override
                    public void onSuccess(
                            List<Item> items) {

                        if (isFinishing()
                                || isDestroyed()) {
                            return;
                        }

                        bindItems(items);
                    }

                    @Override
                    public void onFailure(
                            Exception e) {

                        if (isFinishing()
                                || isDestroyed()) {
                            return;
                        }

                        Toast.makeText(
                                MyItemsActivity.this,
                                "Failed to load your items.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    private void bindItems(List<Item> items) {

        if (items == null || items.isEmpty()) {

            tvEmptyState.setVisibility(
                    View.VISIBLE
            );

            rvMyItems.setVisibility(
                    View.GONE
            );

            return;
        }

        tvEmptyState.setVisibility(
                View.GONE
        );

        rvMyItems.setVisibility(
                View.VISIBLE
        );

        adapter = new ItemAdapter(
                this,
                items,
                item -> {
                    // Item details navigation belongs
                    // to Member 2. No action is required
                    // here for Member 1.
                }
        );

        adapter.setActionListener(
                "Mark as Recovered",
                item -> markItemAsRecovered(item)
        );

        rvMyItems.setAdapter(adapter);
    }

    private void markItemAsRecovered(Item item) {

        if (item == null
                || item.getId() == null
                || item.getId().isEmpty()) {

            return;
        }

        firestoreHelper.markItemAsRecovered(
                item.getId(),
                new FirestoreHelper.Callback<Boolean>() {

                    @Override
                    public void onSuccess(
                            Boolean success) {

                        if (isFinishing()
                                || isDestroyed()) {
                            return;
                        }

                        if (Boolean.TRUE.equals(success)) {

                            runOnUiThread(() -> {

                                Toast.makeText(
                                        MyItemsActivity.this,
                                        "Item marked as RECOVERED!",
                                        Toast.LENGTH_SHORT
                                ).show();

                                loadMyItems();
                            });

                        } else {

                            runOnUiThread(() ->
                                    Toast.makeText(
                                            MyItemsActivity.this,
                                            "Failed to update item status.",
                                            Toast.LENGTH_SHORT
                                    ).show()
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            Exception e) {

                        if (isFinishing()
                                || isDestroyed()) {
                            return;
                        }

                        runOnUiThread(() ->
                                Toast.makeText(
                                        MyItemsActivity.this,
                                        "Failed to update item status.",
                                        Toast.LENGTH_SHORT
                                ).show()
                        );
                    }
                }
        );
    }
}