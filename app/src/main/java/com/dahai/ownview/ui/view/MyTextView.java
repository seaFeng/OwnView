package com.dahai.ownview.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 张海洋 on 2017-08-23.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView{

    private Paint mPaint;
    private Paint mPaint2;

    public MyTextView(Context context) {
        super(context);
        init(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width + 20,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制外层矩形
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        // 绘制内层矩形。
        canvas.drawRect(10,10,getMeasuredWidth() - 10,getMeasuredHeight() - 10,mPaint2);
        canvas.save();
        // 绘制文字钱向前平移10像素。{其实是内容右移。}
        canvas.translate(10,0);
        // 完成文本绘制。
        super.onDraw(canvas);
        canvas.restore();
    }
}
