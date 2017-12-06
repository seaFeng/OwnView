package com.dahai.ownview.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 张海洋 on 2017-12-04.
 */

public class DisTouchViewpager extends ViewPager {


    public DisTouchViewpager(Context context) {
        super(context);
    }

    public DisTouchViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
