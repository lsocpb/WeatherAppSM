package com.example.weatherappsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class FavoriteLocationActivity extends AppCompatActivity implements SwipeGestureListener.SwipeCallback{

    private GestureDetector gestureDetector;
    private RadioButton checkedRadioButton, uncheckedRadioButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_location);

        gestureDetector = new GestureDetector(this, new SwipeGestureListener(this));
        checkedRadioButton = findViewById(R.id.checked_radiobutton);
        uncheckedRadioButton = findViewById(R.id.unchecked_radiobutton);

        boolean isChecked = getIntent().getBooleanExtra("isChecked", false);
        checkedRadioButton.setChecked(isChecked);
        uncheckedRadioButton.setChecked(!isChecked);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    public void changeToNextView() {
        Intent intent = new Intent(this, FavoriteLocationActivity.class);
        startActivity(intent);
        finish();
    }

    public void changeToPreviousView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSwipeLeft() {
        changeToNextView();
    }

    @Override
    public void onSwipeRight() {
        changeToPreviousView();
    }
}
