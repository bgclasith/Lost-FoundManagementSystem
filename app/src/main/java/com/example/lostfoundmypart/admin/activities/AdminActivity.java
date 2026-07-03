package com.example.lostfoundmypart.admin.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);

        bottomNav =
                findViewById(R.id.adminBottomNav);

        loadFragment(
                new AdminDashboardFragment());

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if(id == R.id.navDashboard){

                loadFragment(
                        new AdminDashboardFragment()
                );

                return true;

            } else if(id == R.id.navClaims){

                loadFragment(
                        new AdminClaimsFragment()
                );

                return true;

            } else if(id == R.id.navLost){

                loadFragment(
                        new AdminLostFragment()
                );

                return true;
            } else if(id == R.id.navFound){

                loadFragment(
                        new AdminFoundFragment()
                );

                return true;
            }else if(id == R.id.navUsers){

                loadFragment(
                        new AdminUsersFragment()
                );

                return true;
            }

            return false;
        });
    }

    private void loadFragment(
            Fragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.adminContainer,
                        fragment)
                .commit();
    }
}