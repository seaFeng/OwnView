package com.dahai.ownview.ui.GcsSloop.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xuan on 2018/6/15.
 */

public class PathMeasureView extends View implements View.OnClickListener{
    private static final String TAG = "PathMeasureView";

    private Paint paint = new Paint();
    private Path path = new Path();
    private Path dst = new Path();
    PathMeasure measure = new PathMeasure();
    private int width,height;
    private float length;
    private PointF point;

    public PathMeasureView(Context context) {
        super(context);
        init(context);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void init(Context context) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        path.lineTo(0,200);
        path.lineTo(200,200);
        path.lineTo(200,0);

        dst.moveTo(-100,-100);
        dst.lineTo(-200,-200);

        //PathMeasure measure1 = new PathMeasure(path,false);
        measure.setPath(path,false);

        //Log.e(TAG,"false时的长度：" + measure1.getLength());
        Log.e(TAG,"true时的长度：" + measure.getLength());
        length =  measure.getLength();

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(width/2,height/2);

        canvas.drawPath(dst,paint);
    }

    private float startValue = 0;
    private float endValue = 0;

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                endValue = value * length;
                measure.getSegment(startValue,endValue,dst,false);
                startValue = endValue;
                invalidate();
            }
        });
        animator.start();
    }
}
