package com.dahai.ownview.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 张海洋 on 2017-08-27.
 */

public class CircleProgressView extends View {
    // view的宽高。
    private int mMeasureHeight;
    private int mMeasureWidth;

    private Paint mCirclePaint;     // 圆的画笔。
    private float mCircleXY;        // 圆心的坐标。
    private float mRadius;          // 圆的半径。

    private Paint mArcPaint;
    private RectF mArcRectF;
    private float mSweepAngle;
    private float mSweepValue = 66;

    private Paint mTextPaint;
    private String mShowText;
    private float mShowTextSize;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(mMeasureWidth,mMeasureHeight);
        initView();
    }

    int currentAngle = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        currentAngle += mSweepAngle/10;
        // 绘制圆
        canvas.drawCircle(mCircleXY,mCircleXY,mRadius,mCirclePaint);
        // 绘制弧线
        canvas.drawArc(mArcRectF,270,currentAngle,false,mArcPaint);
        // 绘制文字
        canvas.drawText(mShowText,0,mShowText.length(),mCircleXY,
                mCircleXY + (mShowTextSize / 4),mTextPaint);
        Log.e("circleProgress","当前的角度：" + currentAngle);
        if (currentAngle >= 360) {
            return;
        }
        postInvalidateDelayed(2000);
    }

    private void initView() {
        float length = 0;
        // 取view的宽高的最小值，做View半径
        length = Math.min(mMeasureHeight,mMeasureWidth);

        mCircleXY = length / 2;
        mRadius = (float)(length * 0.5 / 2);        // 1/4 view 的半径。

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_light));

        mArcRectF = new RectF((float) (length * 0.1),(float)(length * 0.1),
                                (float)(length * 0.9),(float)(length * 0.9));
        mSweepAngle = (mSweepValue / 100f) * 360f;
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        mArcPaint.setStrokeWidth((float)(length * 0.1));
        mArcPaint.setStyle(Paint.Style.STROKE);

        mShowText = setShowText();
        mShowTextSize = setShowTextSize();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mShowTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    private float setShowTextSize() {
        this.invalidate();
        return 50;
    }

    private String setShowText(){
        this.invalidate();
        return "Android skill";
    }

    public void forceInvalidate(){
        this.invalidate();
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
            mSweepValue = sweepValue;
        } else {
            mSweepValue = 25;
        }
        this.invalidate();
    }
}
