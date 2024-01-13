package com.example.weatherappsm;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private final SwipeCallback swipeCallback;

    public SwipeGestureListener(SwipeCallback swipeCallback) {
        this.swipeCallback = swipeCallback;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        if (Math.abs(deltaX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (deltaX > 0) {
                swipeCallback.onSwipeRight();
            } else {
                swipeCallback.onSwipeLeft();
            }
            return true;
        }
        return false;
    }

    public interface SwipeCallback {
        void onSwipeLeft();

        void onSwipeRight();
    }
}