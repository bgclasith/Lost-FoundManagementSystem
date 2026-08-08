package com.nibm.findit.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nibm.findit.R;
import com.nibm.findit.admin.database.FirestoreHelper;
import com.nibm.findit.admin.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etProfileName;
    private EditText etProfileEmail;
    private EditText etProfilePhone;
    private EditText etOldPassword;
    private EditText etNewPassword;

    private Button btnUpdateProfile;
    private Button btnChangePassword;
    private Button btnLogout;

    private FirestoreHelper firestoreHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firestoreHelper = new FirestoreHelper();
        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.btnBack);
        etProfileName = findViewById(R.id.etProfileName);
        etProfileEmail = findViewById(R.id.etProfileEmail);
        etProfilePhone = findViewById(R.id.etProfilePhone);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);

        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnLogout = findViewById(R.id.btnLogout);

        btnBack.setOnClickListener(v -> finish());

        loadUserData();

        btnUpdateProfile.setOnClickListener(v -> handleUpdateProfile());
        btnChangePassword.setOnClickListener(v -> handleChangePassword());

        btnLogout.setOnClickListener(v -> {
            sessionManager.logoutUser();

            Intent intent = new Intent(
                    ProfileActivity.this,
                    LoginActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }

    private void loadUserData() {

        String userId = sessionManager.getUserId();

        if (userId == null || userId.isEmpty()) {
            return;
        }

        firestoreHelper.getUserById(
                userId,
                new FirestoreHelper.Callback<com.nibm.findit.admin.models.User>() {

                    @Override
                    public void onSuccess(
                            com.nibm.findit.admin.models.User user) {

                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        if (user != null) {
                            etProfileName.setText(user.getFullName());
                            etProfileEmail.setText(user.getEmail());
                            etProfilePhone.setText(user.getPhone());
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        Toast.makeText(
                                ProfileActivity.this,
                                "Failed to load profile.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    private void handleUpdateProfile() {

        String name = etProfileName.getText().toString().trim();
        String email = etProfileEmail.getText().toString().trim();
        String phone = etProfilePhone.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please fill in all details",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String userId = sessionManager.getUserId();

        if (userId == null || userId.isEmpty()) {
            return;
        }

        btnUpdateProfile.setEnabled(false);

        firestoreHelper.updateProfile(
                userId,
                name,
                email,
                phone,
                new FirestoreHelper.Callback<Boolean>() {

                    @Override
                    public void onSuccess(Boolean success) {

                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        btnUpdateProfile.setEnabled(true);

                        if (Boolean.TRUE.equals(success)) {

                            sessionManager.updateUserDetails(
                                    name,
                                    email,
                                    phone
                            );

                            Toast.makeText(
                                    ProfileActivity.this,
                                    "Profile updated successfully!",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            Toast.makeText(
                                    ProfileActivity.this,
                                    "Failed to update profile.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        btnUpdateProfile.setEnabled(true);

                        Toast.makeText(
                                ProfileActivity.this,
                                "Failed to update profile.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    private void handleChangePassword() {

        String oldPassword =
                etOldPassword.getText().toString().trim();

        String newPassword =
                etNewPassword.getText().toString().trim();

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please enter current and new password",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(
                    this,
                    "New password must be at least 6 characters",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String userId = sessionManager.getUserId();

        if (userId == null || userId.isEmpty()) {
            return;
        }

        btnChangePassword.setEnabled(false);

        firestoreHelper.changePassword(
                userId,
                oldPassword,
                newPassword,
                new FirestoreHelper.Callback<Boolean>() {

                    @Override
                    public void onSuccess(Boolean success) {

                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        btnChangePassword.setEnabled(true);

                        if (Boolean.TRUE.equals(success)) {

                            Toast.makeText(
                                    ProfileActivity.this,
                                    "Password changed successfully!",
                                    Toast.LENGTH_SHORT
                            ).show();

                            etOldPassword.setText("");
                            etNewPassword.setText("");

                        } else {

                            Toast.makeText(
                                    ProfileActivity.this,
                                    "Current password is incorrect.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        btnChangePassword.setEnabled(true);

                        Toast.makeText(
                                ProfileActivity.this,
                                "Failed to change password.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}