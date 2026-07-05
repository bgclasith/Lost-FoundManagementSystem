package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SubmitClaimActivity extends AppCompatActivity {

    private EditText etEvidenceDescription;
    private View btnUploadEvidence; // Changed from Button to View to match LinearLayout in XML
    private Button btnSubmitClaim;
    private ImageView ivEvidencePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_claim);

        etEvidenceDescription = findViewById(R.id.et_evidence_description);
        btnUploadEvidence = findViewById(R.id.btn_upload_evidence);
        btnSubmitClaim = findViewById(R.id.btn_submit_claim);
        ivEvidencePreview = findViewById(R.id.iv_evidence_preview);

        btnUploadEvidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to open gallery or camera to upload evidence
                Toast.makeText(SubmitClaimActivity.this, "Upload Evidence Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnSubmitClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etEvidenceDescription.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(SubmitClaimActivity.this, "Please provide evidence description", Toast.LENGTH_SHORT).show();
                } else {
                    // Logic to submit claim to database
                    Toast.makeText(SubmitClaimActivity.this, "Claim Submitted Successfully", Toast.LENGTH_LONG).show();
                    finish(); // Close activity after submission
                }
            }
        });
    }
}