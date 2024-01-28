package com.example.weatherappsm.activities.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SettingsIssueReportActivity extends AppCompatActivity {

    private EditText idETIssueReport;
    private LinearLayout idBtnReset, idBtnSubmit;
    private String issueReport;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_report);

        idETIssueReport = findViewById(R.id.idETIssueReport);
        idBtnReset = findViewById(R.id.idLLReset);
        idBtnSubmit = findViewById(R.id.idLLSubmit);

        idBtnReset.setOnClickListener(v -> {
            idETIssueReport.setText("");
        });

        idBtnSubmit.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference newIssueRef = db.collection("issueReport").document();

            // Map with issue details
            Map<String, Object> issueData = new HashMap<>();
            issueData.put("msg", idETIssueReport.getText().toString());
            issueData.put("timestamp", new Date().toString());

            // Add issue to Firestore
            newIssueRef.set(issueData)
                    .addOnSuccessListener(aVoid -> {
                        // Success: Issue added
                        //Log.d("Settings", "Zgłoszenie dodane pomyślnie: " + newIssueRef.getId());
                        showSnackbar(getString(R.string.issue_report_success) + " " + newIssueRef.getId());
                        idETIssueReport.setText("");
                    })
                    .addOnFailureListener(e -> {
                        // Error: Issue not added
                        //Log.e("Settings", "Błąd podczas dodawania zgłoszenia", e);
                        showSnackbar(getString(R.string.issue_report_error) + " " + e.getMessage());
                    });
        });
    }

    private void showSnackbar(String message) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
