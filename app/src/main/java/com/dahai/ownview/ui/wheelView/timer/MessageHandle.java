package com.dahai.ownview.ui.wheelView.timer;

import android.os.Handler;
import android.os.Message;

import com.dahai.ownview.ui.wheelView.WheelView;

/**
 * Created by 张海洋 on 2018-06-07.
 */
public class MessageHandle extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    public static final int WHAT_ITEM_SELECTED = 3000;

    private final WheelView wheelView;

    public MessageHandle(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                wheelView.invalidate();
                break;
            case WHAT_SMOOTH_SCROLL:

                break;
            case WHAT_ITEM_SELECTED:

                break;
        }
    }
}
