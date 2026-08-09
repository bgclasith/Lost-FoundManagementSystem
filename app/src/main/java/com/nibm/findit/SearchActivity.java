package com.nibm.findit;
import com.nibm.findit.admin.models.Item;
import com.nibm.findit.admin.adapters.ItemAdapter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private Spinner spinnerCategory, spinnerStatus;
    private EditText etLocation;
    private TextView tvDatePicker, tvSearchEmptyState;
    private Button btnSearch, btnReset;
    private RecyclerView recyclerViewSearch;
    private ProgressBar progressBarSearch;
    
    private ItemAdapter adapter;
    private List<Item> itemList;
    private String selectedDate = "";

    private com.google.firebase.firestore.ListenerRegistration lostReg, foundReg;
    private List<Item> allLostItems = new ArrayList<>();
    private List<Item> allFoundItems = new ArrayList<>();
    
    private String currentSortField = "date";
    private com.google.firebase.firestore.Query.Direction currentSortDirection = com.google.firebase.firestore.Query.Direction.DESCENDING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Search Items");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        searchView = findViewById(R.id.searchView);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        etLocation = findViewById(R.id.etLocation);
        tvDatePicker = findViewById(R.id.tvDatePicker);
        btnSearch = findViewById(R.id.btnSearch);
        btnReset = findViewById(R.id.btnReset);
        recyclerViewSearch = findViewById(R.id.recyclerViewSearch);
        progressBarSearch = findViewById(R.id.progressBarSearch);
        tvSearchEmptyState = findViewById(R.id.tvSearchEmptyState);

        setupSpinners();
        
        tvDatePicker.setOnClickListener(v -> showDatePicker());

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList, item -> { android.content.Intent intent = new android.content.Intent(this, ItemDetailsActivity.class); intent.putExtra("ITEM_DATA", item); startActivity(intent); });
        recyclerViewSearch.setAdapter(adapter);

        setupListeners();

        btnSearch.setOnClickListener(v -> applyFiltersAndSort());
        btnReset.setOnClickListener(v -> resetFilters());
    }

    private void setupSpinners() {
        String[] categories = {"All Categories", "Electronics", "Documents", "Accessories", "Clothing", "Other"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(catAdapter);

        String[] statuses = {"All Status", "Lost", "Found", "Claimed"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        spinnerCategory.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                applyFiltersAndSort();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        spinnerStatus.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                applyFiltersAndSort();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFiltersAndSort();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFiltersAndSort();
                return true;
            }
        });

        etLocation.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFiltersAndSort();
            }
            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void setupListeners() {
        progressBarSearch.setVisibility(View.VISIBLE);
        tvSearchEmptyState.setVisibility(View.GONE);

        if (!isNetworkConnected()) {
            progressBarSearch.setVisibility(View.GONE);
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show();
            tvSearchEmptyState.setText("No internet connection.");
            tvSearchEmptyState.setVisibility(View.VISIBLE);
            return;
        }

        com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();

        lostReg = db.collection("lost_items").addSnapshotListener((snapshots, e) -> {
            progressBarSearch.setVisibility(View.GONE);
            if (e != null) {
                Toast.makeText(SearchActivity.this, "Error loading lost items: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (snapshots != null) {
                allLostItems.clear();
                for (com.google.firebase.firestore.DocumentSnapshot doc : snapshots.getDocuments()) {
                    Item item = doc.toObject(Item.class);
                    if (item != null) {
                        item.setId(doc.getId());
                        allLostItems.add(item);
                    }
                }
                applyFiltersAndSort();
            }
        });

        foundReg = db.collection("found_items").addSnapshotListener((snapshots, e) -> {
            progressBarSearch.setVisibility(View.GONE);
            if (e != null) {
                Toast.makeText(SearchActivity.this, "Error loading found items: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (snapshots != null) {
                allFoundItems.clear();
                for (com.google.firebase.firestore.DocumentSnapshot doc : snapshots.getDocuments()) {
                    Item item = doc.toObject(Item.class);
                    if (item != null) {
                        item.setId(doc.getId());
                        allFoundItems.add(item);
                    }
                }
                applyFiltersAndSort();
            }
        });
    }

    private void applyFiltersAndSort() {
        String query = searchView.getQuery().toString().toLowerCase().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String status = spinnerStatus.getSelectedItem().toString();
        String location = etLocation.getText().toString().toLowerCase().trim();

        List<Item> filteredList = new ArrayList<>();

        List<Item> combined = new ArrayList<>();
        combined.addAll(allLostItems);
        combined.addAll(allFoundItems);

        for (Item item : combined) {
            boolean matchQuery = query.isEmpty()
                    || (item.getTitle() != null && item.getTitle().toLowerCase().contains(query))
                    || (item.getCategory() != null && item.getCategory().toLowerCase().contains(query))
                    || (item.getLocation() != null && item.getLocation().toLowerCase().contains(query))
                    || (item.getDate() != null && item.getDate().toLowerCase().contains(query));

            boolean matchCategory = category.equals("All Categories")
                    || (item.getCategory() != null && item.getCategory().equalsIgnoreCase(category));

            boolean matchStatus = status.equals("All Status")
                    || (item.getStatus() != null && item.getStatus().equalsIgnoreCase(status));

            boolean matchLocation = location.isEmpty()
                    || (item.getLocation() != null && item.getLocation().toLowerCase().contains(location));

            boolean matchDate = selectedDate.isEmpty()
                    || (item.getDate() != null && item.getDate().equals(selectedDate));

            if (matchQuery && matchCategory && matchStatus && matchLocation && matchDate) {
                filteredList.add(item);
            }
        }

        java.util.Collections.sort(filteredList, (o1, o2) -> {
            int compare = 0;
            if (currentSortField.equals("date")) {
                String d1 = o1.getDate() != null ? o1.getDate() : "";
                String d2 = o2.getDate() != null ? o2.getDate() : "";
                compare = d1.compareTo(d2);
            } else if (currentSortField.equals("itemName")) {
                String n1 = o1.getTitle() != null ? o1.getTitle() : "";
                String n2 = o2.getTitle() != null ? o2.getTitle() : "";
                compare = n1.compareToIgnoreCase(n2);
            }
            return currentSortDirection == com.google.firebase.firestore.Query.Direction.ASCENDING ? compare : -compare;
        });

        itemList.clear();
        itemList.addAll(filteredList);
        adapter.notifyDataSetChanged();

        if (itemList.isEmpty()) {
            tvSearchEmptyState.setText(R.string.no_matching_items_found);
            tvSearchEmptyState.setVisibility(View.VISIBLE);
        } else {
            tvSearchEmptyState.setVisibility(View.GONE);
        }
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String formattedMonth = (monthOfYear + 1 < 10) ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                    String formattedDay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                    selectedDate = year1 + "-" + formattedMonth + "-" + formattedDay;
                    tvDatePicker.setText(selectedDate);
                    applyFiltersAndSort();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void resetFilters() {
        searchView.setQuery("", false);
        spinnerCategory.setSelection(0);
        spinnerStatus.setSelection(0);
        etLocation.setText("");
        tvDatePicker.setText(getString(R.string.select_date));
        selectedDate = "";
        applyFiltersAndSort();
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
        if (lostReg != null) lostReg.remove();
        if (foundReg != null) foundReg.remove();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
            currentSortDirection = com.google.firebase.firestore.Query.Direction.DESCENDING;
            applyFiltersAndSort();
            return true;
        } else if (item.getItemId() == 2) {
            currentSortField = "date";
            currentSortDirection = com.google.firebase.firestore.Query.Direction.ASCENDING;
            applyFiltersAndSort();
            return true;
        } else if (item.getItemId() == 3) {
            currentSortField = "itemName";
            currentSortDirection = com.google.firebase.firestore.Query.Direction.ASCENDING;
            applyFiltersAndSort();
            return true;
        } else if (item.getItemId() == 4) {
            currentSortField = "itemName";
            currentSortDirection = com.google.firebase.firestore.Query.Direction.DESCENDING;
            applyFiltersAndSort();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
