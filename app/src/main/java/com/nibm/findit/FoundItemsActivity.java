package com.nibm.findit;
import com.nibm.findit.admin.models.Item;
import com.nibm.findit.admin.adapters.ItemAdapter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;

public class FoundItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;

    private ProgressBar progressBar;
    private TextView tvEmptyState;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefreshLayout;
    private com.google.firebase.firestore.ListenerRegistration registration;
    private Query.Direction currentSortDirection = Query.Direction.DESCENDING;
    private String currentSortField = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_items);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Found Items");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList, item -> { android.content.Intent intent = new android.content.Intent(this, ItemDetailsActivity.class); intent.putExtra("ITEM_DATA", item); startActivity(intent); });
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this::loadData);

        loadData();
    }

    private void loadData() {
        if (registration != null) {
            registration.remove();
        }

        progressBar.setVisibility(View.VISIBLE);
        tvEmptyState.setVisibility(View.GONE);

        if (!isNetworkConnected()) {
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show();
            if (itemList.isEmpty()) {
                tvEmptyState.setText("No internet connection.");
                tvEmptyState.setVisibility(View.VISIBLE);
            }
            return;
        }

        com.google.firebase.firestore.CollectionReference ref = 
            com.google.firebase.firestore.FirebaseFirestore.getInstance().collection("found_items");

        registration = ref.orderBy(currentSortField, currentSortDirection)
            .addSnapshotListener((snapshots, e) -> {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (e != null) {
                    Toast.makeText(FoundItemsActivity.this, "Error loading data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    if (itemList.isEmpty()) {
                        tvEmptyState.setText("Error loading data.");
                        tvEmptyState.setVisibility(View.VISIBLE);
                    }
                    return;
                }

                if (snapshots != null) {
                    itemList.clear();
                    for (com.google.firebase.firestore.DocumentSnapshot document : snapshots.getDocuments()) {
                        Item item = document.toObject(Item.class);
                        if (item != null) {
                            item.setId(document.getId());
                            itemList.add(item);
                        }
                    }
                    adapter.notifyDataSetChanged();

                    if (itemList.isEmpty()) {
                        tvEmptyState.setText(R.string.no_items_available);
                        tvEmptyState.setVisibility(View.VISIBLE);
                    } else {
                        tvEmptyState.setVisibility(View.GONE);
                    }
                }
            });
    }

    private boolean isNetworkConnected() {
        android.net.ConnectivityManager cm = (android.net.ConnectivityManager) getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        android.net.NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registration != null) {
            registration.remove();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Newest First");
        menu.add(0, 2, 0, "Oldest First");
        menu.add(0, 3, 0, "A-Z");
        menu.add(0, 4, 0, "Z-A");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == 1) {
            currentSortField = "date";
            currentSortDirection = Query.Direction.DESCENDING;
            loadData();
            return true;
        } else if (item.getItemId() == 2) {
            currentSortField = "date";
            currentSortDirection = Query.Direction.ASCENDING;
            loadData();
            return true;
        } else if (item.getItemId() == 3) {
            currentSortField = "itemName";
            currentSortDirection = Query.Direction.ASCENDING;
            loadData();
            return true;
        } else if (item.getItemId() == 4) {
            currentSortField = "itemName";
            currentSortDirection = Query.Direction.DESCENDING;
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
