package com.example.weatherappsm.ui.activites.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SettingsFeatureRequestActivity extends AppCompatActivity {

    private EditText idETRequestFeature;
    private LinearLayout idBtnReset, idBtnSubmit;
    private String featureRequest;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_request);

        idETRequestFeature = findViewById(R.id.idETRequestFeature);
        idBtnReset = findViewById(R.id.idLLReset);
        idBtnSubmit = findViewById(R.id.idLLSubmit);

        idBtnReset.setOnClickListener(v -> {
            idETRequestFeature.setText("");
        });

        idBtnSubmit.setOnClickListener(v -> {
            featureRequest = idETRequestFeature.getText().toString();
            if (featureRequest.isEmpty()) {

                showSnackbar(getString(R.string.feature_request_empty));
            } else {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference newIssueRef = db.collection("featureRequests").document();

                // Map with issue details
                Map<String, Object> issueData = new HashMap<>();
                issueData.put("msg", featureRequest);
                issueData.put("timestamp", new Date().toString());

                // Add issue to Firestore
                newIssueRef.set(issueData)
                        .addOnSuccessListener(aVoid -> {
                            // Success: Issue added
                            //Log.d("Settings", "Zgłoszenie dodane pomyślnie: " + newIssueRef.getId());
                            showSnackbar(getString(R.string.feature_request_success) + " " + newIssueRef.getId());
                            idETRequestFeature.setText("");
                        })
                        .addOnFailureListener(e -> {
                            // Error: Issue not added
                            //Log.e("Settings", "Błąd podczas dodawania zgłoszenia", e);
                            showSnackbar(getString(R.string.feature_request_error) + " " + e.getMessage());
                        });

            }
        });

    }

    private void showSnackbar(String message) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
