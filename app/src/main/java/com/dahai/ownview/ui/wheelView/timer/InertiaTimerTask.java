package com.dahai.ownview.ui.wheelView.timer;

import com.dahai.ownview.ui.wheelView.WheelView;

import java.util.TimerTask;

/**
 * Created by 张海洋 on 2018-06-08.
 */
public class InertiaTimerTask extends TimerTask {
    private float mCurrentVelocityY;        // 当前滑动速度
    private final float mFirstVelocityY;    //手指离开屏幕的初始速度
    private final WheelView mWheelView;

    public InertiaTimerTask(WheelView wheelView,float velocityY){
        this.mWheelView = wheelView;
        this.mFirstVelocityY = velocityY;
        mCurrentVelocityY = Integer.MAX_VALUE;
    }

    @Override
    public void run() {
        // 防止闪动，对速度做一个限制
        if (mCurrentVelocityY == Integer.MAX_VALUE) {
            if (Math.abs(mFirstVelocityY) > 2000F) {
                mCurrentVelocityY = mFirstVelocityY > 0 ? 2000F : -2000F;
            } else {
                mCurrentVelocityY = mFirstVelocityY;
            }
        }

        // 发送Handler消息，处理平顺停止滚动逻辑
        if (Math.abs(mCurrentVelocityY) >= 0.0F && Math.abs(mCurrentVelocityY) <= 20F) {
            mWheelView.cancelFuture();
            mWheelView.getHandler().sendEmptyMessage(MessageHandle.WHAT_SMOOTH_SCROLL);
            return;
        }

        int dy = (int) (mCurrentVelocityY / 100F);
        mWheelView.setTotalScrollY(mWheelView.getTotalScrollY() - dy);
        if (!mWheelView.isLoop()) {
            float itemHeight = mWheelView.getItemHeight();
            float top = (-mWheelView.getInitPosition()) * itemHeight;
            float bottom = (mWheelView.getItemsCount() - 1 - mWheelView.getInitPosition()) * itemHeight;
        }
    }
}
