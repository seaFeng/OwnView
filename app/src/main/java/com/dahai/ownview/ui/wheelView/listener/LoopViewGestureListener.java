package com.dahai.ownview.ui.wheelView.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.dahai.ownview.ui.wheelView.WheelView;

/**
 * Created by 张海洋 on 2018-06-08.
 */
public class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final WheelView wheelView;

    public LoopViewGestureListener(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelView.scrollBy(velocityY);
        return true;
    }
}
