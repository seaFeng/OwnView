package com.dahai.ownview.ui.GcsSloop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dahai.ownview.utils.ScreenUtils;

/**
 * Created by xuan on 2018/6/14.
 */

public class OperateCanvasView extends View {
    private int width;
    private int height;
    private Paint mPaint = new Paint();

    public OperateCanvasView(Context context) {
        super(context);
        init(context);
    }

    public OperateCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OperateCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        int[] wh = ScreenUtils.getWH(context);
        width = wh[0];
        height = wh[1];

        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasSkew(canvas);
    }

    /**
     *  错切（skew）
     */
    private void canvasSkew(Canvas canvas) {
        canvas.drawLine(0,height/2,width,height/2,mPaint);
        canvas.drawLine(width/2,0,width/2,height,mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);
        canvas.drawPoint(width/2,height/2,mPaint);

        // 错切
        RectF rectF = new RectF(0,-400,400,0);
        canvas.translate(width/2,height/2);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,mPaint);

        canvas.skew(1,0);
        canvas.drawRect(rectF,mPaint);
    }

    /**
     *  画布的旋转
     */
    private void canvasRotate(Canvas canvas) {
        canvas.drawLine(0,height/2,width,height/2,mPaint);
        canvas.drawLine(width/2,0,width/2,height,mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);
        canvas.drawPoint(width/2,height/2,mPaint);

        // 旋转
        RectF rectF = new RectF(0,-400,400,0);
        canvas.translate(width/2,height/2);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,mPaint);

        canvas.rotate(180);             // 旋转是顺时针。
        canvas.drawRect(rectF,mPaint);

    }

    /**
     *  画布的缩放
     */
    private void canvasScale(Canvas canvas){
        canvas.drawLine(0,height/2,width,height/2,mPaint);
        canvas.drawLine(width/2,0,width/2,height,mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);
        canvas.drawPoint(width/2,height/2,mPaint);

        // 缩放
        RectF rectF = new RectF(0,-400,400,0);
        canvas.translate(width/2,height/2);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,mPaint);

        //canvas.scale(-0.8f,-0.8f,200,0);
        canvas.scale(-0.5f,-0.5f);   // 围绕原点旋转180度，再缩放或则扩大。
        canvas.drawRect(rectF,mPaint);

    }

    /**
     *  画布的平移
     */
    private void canvasTranslate(Canvas canvas) {
        canvas.save();
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        canvas.translate(200,200);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(0,0,200,mPaint);
    }
}











