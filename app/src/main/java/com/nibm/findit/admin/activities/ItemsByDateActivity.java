package com.nibm.findit.admin.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.findit.R;
import com.nibm.findit.admin.adapters.ItemAdapter;
import com.nibm.findit.admin.database.FirestoreHelper;
import com.nibm.findit.admin.models.Item;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ItemsByDateActivity extends AppCompatActivity {

    private MaterialButton btnPickDate;
    private TextView tvResultLabel, tvResultCount, tvEmpty;
    private ProgressBar progressBar;
    private RecyclerView recyclerItems;

    private FirestoreHelper dbHelper;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_by_date);

        dbHelper = new FirestoreHelper();
        itemList = new ArrayList<>();

        ImageView btnBack = findViewById(R.id.btnBack);
        btnPickDate = findViewById(R.id.btnPickDate);
        tvResultLabel = findViewById(R.id.tvResultLabel);
        tvResultCount = findViewById(R.id.tvResultCount);
        tvEmpty = findViewById(R.id.tvEmpty);
        progressBar = findViewById(R.id.progressBar);
        recyclerItems = findViewById(R.id.recyclerItems);

        itemAdapter = new ItemAdapter(this, itemList, item -> {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra("itemId", item.getId());
            startActivity(intent);
        });
        recyclerItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerItems.setAdapter(itemAdapter);

        btnBack.setOnClickListener(v -> finish());
        btnPickDate.setOnClickListener(v -> openDatePicker());
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Format: yyyy-MM-dd (matches the date format stored in Firestore)
            String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                    selectedYear, selectedMonth + 1, selectedDay);

            String displayDate = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                    selectedDay, selectedMonth + 1, selectedYear);

            btnPickDate.setText("📅  " + displayDate);
            loadItemsForDate(selectedDate, displayDate);

        }, year, month, day);

        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dialog.show();
    }

    private void loadItemsForDate(String dateStr, String displayDate) {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        recyclerItems.setVisibility(View.GONE);
        tvResultLabel.setText("Items on " + displayDate);
        tvResultCount.setText("");

        itemList.clear();
        itemAdapter.notifyDataSetChanged();

        dbHelper.getItemsByDate(dateStr, new FirestoreHelper.Callback<List<Item>>() {
            @Override
            public void onSuccess(List<Item> items) {
                progressBar.setVisibility(View.GONE);
                itemList.clear();
                itemList.addAll(items);
                itemAdapter.notifyDataSetChanged();

                if (items.isEmpty()) {
                    tvEmpty.setText("No items were reported on " + displayDate + ".");
                    tvEmpty.setVisibility(View.VISIBLE);
                    recyclerItems.setVisibility(View.GONE);
                    tvResultCount.setText("0 items");
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    recyclerItems.setVisibility(View.VISIBLE);
                    tvResultCount.setText(items.size() + " item" + (items.size() == 1 ? "" : "s"));
                }
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.GONE);
                tvEmpty.setText("Failed to load items. Please try again.");
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerItems.setVisibility(View.GONE);
            }
        });
    }
}
