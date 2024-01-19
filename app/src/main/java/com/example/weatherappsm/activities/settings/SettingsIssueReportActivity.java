package com.example.weatherappsm.activities.settings;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.google.android.material.snackbar.Snackbar;

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
            issueReport = idETIssueReport.getText().toString();
            if (issueReport.isEmpty()) {

                showSnackbar(getString(R.string.issue_report_empty));
            } else {
                System.out.println("Issue report submitted: " + issueReport);

            }
        });
    }

    private void showSnackbar(String message) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
