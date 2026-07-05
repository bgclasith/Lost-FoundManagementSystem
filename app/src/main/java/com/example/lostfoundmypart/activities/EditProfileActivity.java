package com.example.lostfoundmypart.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundmypart.R;
import com.google.android.material.button.MaterialButton;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private MaterialButton btnChangePhoto;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageButton btnBack = findViewById(R.id.btnBack);
        imgProfile = findViewById(R.id.imgProfile);
        btnChangePhoto = findViewById(R.id.btnChangePhoto);

        btnBack.setOnClickListener(v -> finish());

        imagePickerLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {

                            if (result.getResultCode() == Activity.RESULT_OK &&
                                    result.getData() != null) {

                                Uri imageUri = result.getData().getData();

                                imgProfile.setImageURI(imageUri);
                                imgProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                        });

        btnChangePhoto.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);

    }
}