package com.example.weatherappsm.activities.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFeatureRequestActivity extends AppCompatActivity {

    private EditText idETRequestFeature;
    private LinearLayout idBtnReset, idBtnSubmit;
    private String issueReport;
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
            issueReport = idETRequestFeature.getText().toString();
            if (issueReport.isEmpty()) {

                showSnackbar("Please enter a feature request");
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
