package com.dahai.ownview.ui.wheelView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;

import com.dahai.ownview.R;
import com.dahai.ownview.ui.wheelView.timer.MessageHandle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by 张海洋 on 2018-06-07.
 *  https://github.com/Bigkoo/Android-PickerView
 */
public class WheelView extends View {
    private int textSize;       // 选项的文字大小
    private Context context;
    private MessageHandle handler;
    private GestureDetector gestureDetector;

    // Timer mTimer;
    private ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    public WheelView(Context context) {
        this(context,null);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textSize = (int) getResources().getDimension(R.dimen.pickerView_textSize);

        //todo 适配先不做：

        // todo 自定义属性：先不做

        judgeLineSpace();
        initLoopView(context);
    }

    private void initLoopView(Context context) {
        this.context = context;
        handler = new MessageHandle(this);
        //gestureDetector = new GestureDetector(context,)
    }

    /**
     *  判断间距是否在1.0-4.0之间
     */
    private void judgeLineSpace() {
        //todo 延后
    }

    /**
     *  滚动惯性的实现
     */
    public final void scrollBy(float velocityY) {
        cancelFuture();
        //mFuture = mExecutor.scheduleWithFixedDelay()
    }

    public void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }
}
