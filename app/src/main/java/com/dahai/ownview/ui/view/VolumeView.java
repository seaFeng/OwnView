package com.dahai.ownview.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 张海洋 on 2017-08-28.
 */

public class VolumeView extends View {
    public static final String TAG = "volumeView";

    private Paint mPaint;
    private int mRectCount;
    private int mWidth;
    private int mRectWidth;
    private int mRectHeight;
    private LinearGradient mLinearGradient;

    private double mRandom;
    private int offset = 15;

    public VolumeView(Context context) {
        this(context,null);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mRectCount = 12;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        Log.e(TAG,"View的宽度 == " + mWidth);
        mRectHeight = getHeight();
        Log.e(TAG,"View的高度 == " + mRectHeight);
        mRectWidth = (int)(mWidth * 0.6 / mRectCount);
        Log.e(TAG,"方条的宽度 == " + mRectWidth);
        mLinearGradient = new LinearGradient(0,0,mRectWidth,mRectHeight,Color.YELLOW,Color.BLUE,
                Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mRectCount; i++) {
            mRandom = Math.random();
            float currentHeight = (float)(mRectHeight * mRandom);
            canvas.drawRect((float)(mWidth * 0.4 / 2 + mRectWidth * i + offset),
                    currentHeight,
                    (float)(mWidth * 0.4 / 2 + mRectWidth * (i + 1)),
                    mRectHeight,
                    mPaint);
        }
    }
}
