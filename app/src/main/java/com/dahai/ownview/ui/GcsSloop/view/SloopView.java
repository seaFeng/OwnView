package com.dahai.ownview.ui.GcsSloop.view;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.dahai.ownview.utils.ScreenUtils;

/**
 * Created by xuan on 2018/6/14.
 */

public class SloopView extends View {
    private static final String TAG = "SloopView";

    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private int height;

    public SloopView(Context context) {
        super(context);
        init(context);
    }

    public SloopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SloopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        int[] wh = ScreenUtils.getWH(context);
        width = wh[0];
        height = wh[1];

        // 初始化画笔：
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //Log.e(TAG,"高的模式 == " + (heightMode == MeasureSpec.EXACTLY?"EXACTLY的模式":"AT_MOST的模式") + "\n" +
                    //"高的大小 == " + heightSize);

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawColor(canvas);
        drawPie(canvas);
    }

    /**
     *  绘制饼状图
     */
    private void drawPie(Canvas canvas){
        //canvas.translate(width/2,height/2);
        mPaint.setColor(mColors[0]);
        RectF rectF = new RectF(100, 100, 800, 800);
        canvas.drawArc(rectF,0,100,true,mPaint);
        mPaint.setColor(mColors[1]);
        canvas.drawArc(rectF,100,40,true,mPaint);
        mPaint.setColor(mColors[2]);
        canvas.drawArc(rectF,140,70,true,mPaint);
        mPaint.setColor(mColors[3]);
        canvas.drawArc(rectF,210,150,true,mPaint);
    }

    /**
     *  绘制圆弧
     */
    private void drawArc(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        // 从x轴正方向 --> 逆时针旋转
        canvas.drawArc(new RectF(100,100,800,600),90,60,false,mPaint);
    }

    /**
     *  绘制椭圆 和 圆
     */
    private void drawOval(Canvas canvas) {
        canvas.drawOval(new RectF(100,100,800,400),mPaint);
        canvas.drawCircle(600,600,200,mPaint);
    }

    /**
     *  绘制矩形
     */
    private void drawRect(Canvas canvas){
        canvas.drawRect(new Rect(100,100,500,500),mPaint);

        canvas.drawRoundRect(new RectF(100,550,500,950),1000f,1000f,mPaint);
    }

    /**
     *  绘制直线
     */
    private void drawLines(Canvas canvas) {
        canvas.drawLines(new float[]{100,100,500,100,300,100,300,800,150,450,450,450,100,800,500,800},mPaint);
    }

    /**
     *  绘制点
     */
    private void drawPoint(Canvas canvas) {
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(200,200,mPaint);

        canvas.drawPoints(new float[]{200,200,300,200,400,200},mPaint);
    }

    /**
     *  绘制颜色
     */
    private void drawColor(Canvas canvas){
        canvas.drawColor(Color.GRAY);
    }
}
