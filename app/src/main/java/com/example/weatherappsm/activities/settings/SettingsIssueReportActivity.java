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

            // Tworzymy mapę zawierającą szczegóły zgłoszenia
            Map<String, Object> issueData = new HashMap<>();
            issueData.put("msg", idETIssueReport.getText().toString()); // Treść zgłoszenia
            issueData.put("timestamp", new Date().toString()); // Data utworzenia zgłoszenia - możesz dostosować to do swoich potrzeb

            // Dodajemy zgłoszenie do Firestore
            newIssueRef.set(issueData)
                    .addOnSuccessListener(aVoid -> {
                        // Sukces: Zgłoszenie zostało dodane pomyślnie
                        Log.d("Settings", "Zgłoszenie dodane pomyślnie: " + newIssueRef.getId());
                    })
                    .addOnFailureListener(e -> {
                        // Błąd: Nie udało się dodać zgłoszenia
                        Log.e("Settings", "Błąd podczas dodawania zgłoszenia", e);
                        // Tutaj możesz wyświetlić użytkownikowi komunikat o błędzie
                    });
        });
    }

    private void showSnackbar(String message) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
