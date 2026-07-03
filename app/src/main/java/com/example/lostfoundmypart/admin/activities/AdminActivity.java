package com.example.lostfoundmypart.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.lostfoundmypart.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.lostfoundmypart.admin.fragments.AdminClaimsFragment;
import com.example.lostfoundmypart.admin.fragments.AdminDashboardFragment;
import com.example.lostfoundmypart.admin.fragments.AdminFoundFragment;
import com.example.lostfoundmypart.admin.fragments.AdminLostFragment;
import com.example.lostfoundmypart.admin.fragments.AdminUsersFragment;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNav = findViewById(R.id.adminBottomNav);

        loadFragment(new AdminDashboardFragment());

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.navDashboard) {
                loadFragment(new AdminDashboardFragment());
            } else if (id == R.id.navClaims) {
                loadFragment(new AdminClaimsFragment());
            } else if (id == R.id.navLost) {
                loadFragment(new AdminLostFragment());
            } else if (id == R.id.navFound) {
                loadFragment(new AdminFoundFragment());
            } else if (id == R.id.navUsers) {
                loadFragment(new AdminUsersFragment());
            }

            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.adminContainer, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuProfile) {

            startActivity(new Intent(
                    AdminActivity.this,
                    AdminProfileActivity.class
            ));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}