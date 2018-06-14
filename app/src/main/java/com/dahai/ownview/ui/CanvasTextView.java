package com.dahai.ownview.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 张海洋 on 2018-06-14.
 */
public class CanvasTextView extends View {
    private int width;
    private int height;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    public CanvasTextView(Context context) {
        super(context);
        init(context);
    }

    public CanvasTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }

    private void init(Context context) {
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE,mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPath3(canvas);
    }

    /**
     *  CW(顺时针)；CCW（逆时针）
     */
    private void drawPath3(Canvas canvas) {
        canvas.translate(width/2,height/2);
        path.addRect(-200,-200,200,200, Path.Direction.CCW);
        path.setLastPoint(-300,300);
        canvas.drawPath(path,mPaint);
    }

    /**
     *  addXXX,arcTo
     */
    private void drawPath2(Canvas canvas) {
        canvas.translate(width/2,height/2);
        // cw:顺时针，ccw：逆时针
        path.addCircle(0,0,100, Path.Direction.CCW);
        path.addOval(new RectF(-200,100,200,300), Path.Direction.CW);

        canvas.drawPath(path,mPaint);
    }

    /**
     *  moveTo,lineTo,setLastPoint,close,canvas.drawPath()
     */
    private void drawPath1(Canvas canvas) {
        canvas.translate(width/2,height/2);
        path.lineTo(200,200);
        //path.moveTo(200,100);
        path.setLastPoint(200,100);         // 更改最新的一个点的位置
        path.lineTo(200,0);
        path.close();
        canvas.drawPath(path,mPaint);
    }
}
