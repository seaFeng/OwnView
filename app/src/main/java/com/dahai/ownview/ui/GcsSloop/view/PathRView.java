package com.dahai.ownview.ui.GcsSloop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuan on 2018/6/15.
 */

public class PathRView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private int width;
    private int heigh;

    public PathRView(Context context) {
        super(context);
        init(context);
    }

    public PathRView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PathRView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        heigh = h;
    }

    private void init(Context context) {
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        setLayerType(LAYER_TYPE_SOFTWARE,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        pathR4(canvas);
    }

    /**
     *  path.op : 布尔操作(API19)
     */
    private void pathR4(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(width/2,heigh/2);
        Path path1 = new Path();
        path1.addCircle(-100,0,200, Path.Direction.CW);
        Path path2 = new Path();
        path2.addCircle(100,0,200, Path.Direction.CW);
        if (Build.VERSION.SDK_INT >= 19) {
            //path2.op(path1, Path.Op.DIFFERENCE);          // path2 减去交集
            //path2.op(path1,Path.Op.REVERSE_DIFFERENCE);     // path1 减去交集
            //path2.op(path1,Path.Op.INTERSECT);               // 显示两者交集。
            //path2.op(path1,Path.Op.UNION);                  // 全部显示。
            path2.op(path1,Path.Op.XOR);                    // 去除交集。
        }
        canvas.drawPath(path2,paint);
    }

    /**
     *  非零环绕数规则
     */
    private void pathR3(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(width/2,heigh/2);

        // 小正方形：
        path.addRect(-200,-200,200,200,Path.Direction.CCW);

        // 添加大正方形：
        path.addRect(-400,-400,400,400,Path.Direction.CW);

        path.setFillType(Path.FillType.WINDING);        // 非零环绕规则。

        canvas.drawPath(path,paint);
    }

    /**
     *  填充规则：奇偶规则和非奇偶规则
     */
    private void pathR2(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.translate(width/2,heigh/2);

        path.setFillType(Path.FillType.EVEN_ODD);               // 奇偶规则
        //path.setFillType(Path.FillType.INVERSE_EVEN_ODD);       // 反奇偶规则

        path.addRect(new RectF(-200,-200,200,200),Path.Direction.CCW);

        canvas.drawPath(path,paint);
    }

    /**
     *  rLineTo
     */
    private void pathR1(Canvas canvas) {

        //canvas.drawLine(200,200,400,400,paint);

        path.moveTo(100,100);
        path.rLineTo(200,200);          // 想对上一点位移
        //path.

        canvas.drawPath(path,paint);
    }
}
