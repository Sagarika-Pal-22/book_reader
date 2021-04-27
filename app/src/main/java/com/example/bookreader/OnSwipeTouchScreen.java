package com.example.bookreader;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchScreen implements View.OnTouchListener {

    @SuppressWarnings("deprecation")
    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            float yDiff = Math.abs(Math.abs(event1.getRawY()) - Math.abs(event2.getRawY()));
            float xDiff = Math.abs(Math.abs(event1.getRawX()) - Math.abs(event2.getRawX()));
            if (yDiff > 250 || yDiff > xDiff) {
                return false;
            }
            if (event1.getRawX() > event2.getRawX()) {
                onSwipeLeft();
            } else {
                onSwipeRight();
            }
            return true;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }


}
