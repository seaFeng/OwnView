package com.dahai.ownview.ui.GcsSloop.view;

import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuan on 2018/6/14.
 */

public class BitmapAndTextView extends View {
    private static final String TAG = "bitmapAndTextView";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Picture mPicture = new Picture();

    public BitmapAndTextView(Context context) {
        super(context);
        init(context);
    }

    public BitmapAndTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BitmapAndTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        recording();
    }

    // 录制内容
    private void recording(){
        Canvas canvas = mPicture.beginRecording(500, 500);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.translate(250,250);
        canvas.drawCircle(0,0,100,mPaint);

        mPicture.endRecording();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawPicture(mPicture,new RectF(0,0,mPicture.getWidth(),200));
        //mPicture.draw(canvas);

        // 3.将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。
        PictureDrawable drawable = new PictureDrawable(mPicture);


    }
}
