package com.dahai.ownview.ui.wheelView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;

import com.dahai.ownview.R;
import com.dahai.ownview.ui.wheelView.adapter.WheelAdapter;
import com.dahai.ownview.ui.wheelView.listener.LoopViewGestureListener;
import com.dahai.ownview.ui.wheelView.timer.MessageHandle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.security.auth.login.LoginException;

/**
 * Created by 张海洋 on 2018-06-07.
 *  https://github.com/Bigkoo/Android-PickerView
 */
public class WheelView extends View {
    private static final String TAG = "WheelView";

    public enum ACTION {        // 点击，滑翔（滑倒尽头），拖拽事件
        CLICK,FLING,DAGGLE
    }

    public enum DividerType {    // 分隔线类型
        FILL,WRAP
    }

    private int textSize;       // 选项的文字大小
    private Context context;
    private Handler handler;
    private GestureDetector gestureDetector;
    private boolean isLoop;

    // 分割线类型
    private DividerType dividerType;    //分割线类型。

    // 当前滚动总高度y值
    private float totalScrollY;
    // 每行高度
    private float itemHeight;

    // 初始化默认选中项
    private int initPosition;

    // Timer mTimer;
    private ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;
    // 画笔
    private Paint paintOuterText;
    private Paint paintCenterText;
    private Paint paintIndicator;

    private WheelAdapter adapter;

    private String label = "hh";   // 附加单位
    private int maxTextWidth;
    private int maxTextHeight;
    private int textOffset;
    private int textXOffset;
    private final float DEFAULT_TEXT_TARGET_SKEWX = 0.5f;
    // 偏移量
    private float CENTER_CONTENT_OFFSET;

    private int mGravity = Gravity.CENTER;
    private int drawCenterContentStart = 0; // 中间选中文字开始绘制位置
    private int drawOutContentStart = 0;    // 非中间文字开始绘制位置
    private static final float SCALE_CONTENT = 0.8f;   // 非中间文字则用

    private boolean isOptions = false;
    private boolean isCenterLabel = true;

    // 绘制几个条目，实际上第一项和最后一项Y轴压缩成了0%了，所以实际可见的
    // 数目实际为9
    private int itemsVisible = 7;
    // 半径
    private int radius;
    // 滚动偏移值，用于记录滚动了多少个item
    private int change;
    // 选中的Item 是第几个
    private int selectedItem;
    private int preCurrentIndex;
    // 第一条线的Y坐标值
    private float firstLineY;
    // 第二条线的Y坐标
    private float secondLinY;
    // 中间label绘制的Y坐标
    private float centerY;

    private int widthMeasureSpec;
    // 控件的宽高：
    private int measureWidth;   // 宽
    private int measureHeigh;   // 高

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
        gestureDetector = new GestureDetector(context,new LoopViewGestureListener(this));
        gestureDetector.setIsLongpressEnabled(false);
        isLoop = true;
        totalScrollY = 0;
        initPosition = -1;
        initPaints();
    }

    private void initPaints(){
        paintOuterText = new Paint();
        paintOuterText.setColor(Color.RED);
        paintOuterText.setAntiAlias(true);
        //paintOuterText.setTypeface(typeface)
        paintOuterText.setTextSize(textSize);

        paintCenterText = new Paint();
        paintCenterText.setColor(0xffff0000);
        paintCenterText.setAntiAlias(true);
        paintCenterText.setTextScaleX(1.1F);
        //paintCenterText.setTypeface(typeface);
        paintCenterText.setTextSize(textSize);

        paintIndicator = new Paint();
        paintIndicator.setColor(0xFF0000FF);
        paintIndicator.setAntiAlias(true);

        setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        //Log.e(TAG,"控件的高 == " + measureHeigh + "     控件的宽 == " + measureWidth);
        setMeasuredDimension(measureWidth,measureHeigh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (adapter == null){
            return;
        }
        //initPosition越界会造成preCurrentIndex的值不正确
        initPosition = Math.min(Math.max(0,initPosition),adapter.getItemsCount() - 1);

        // 可见的item数组
        Object visibles[] = new Object[itemsVisible];
        // 滚动的Y值高度除去每行Item的高度，得到滚动了多少个item，即change数
        change = (int)(totalScrollY / itemHeight);

        try {
            // 滚动中实际的预选中的item（即经过了中间位置的item） = 滑动前的位置 + 滑动相对位置
            preCurrentIndex = initPosition + change %adapter.getItemsCount();
        } catch(ArithmeticException e) {
            Log.e("WheelView", "出错了！adapter.getItemsCount() == 0，联动数据不匹配");
        }

        if (!isLoop) {      // 不循环的情况
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {
                preCurrentIndex = adapter.getItemsCount() - 1;
            }
        } else {        // 循环
            if (preCurrentIndex < 0) {  // 举个例子：如果总数是5，precurrentIndex = -1,那么precurrentIndex按循环来说，其实是0的上面，也就是4的位置
                preCurrentIndex = adapter.getItemsCount() + preCurrentIndex;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {        // 同理同上
                preCurrentIndex = preCurrentIndex - adapter.getItemsCount();
            }
        }
        // 跟流动流畅度有关，总滑动距离与每个item高度取余，即并不是一格格的滚动
        // 每个item不一定滚到对应Rect里的，这个item对应格子的偏移值
        float itemHeightOffset = (totalScrollY % itemHeight);

        // 设置数组中每个元素的值
        int counter = 0;
        while(counter < itemsVisible) {
            // 索引值，即当前在控件中间的item看作数据源的中间，计算出相对数据源的index值
            int index = preCurrentIndex - (itemsVisible / 2 - counter);
            if (isLoop) {
                index = getLoopMappingIndex(index);
                visibles[counter] = adapter.getItem(index);
            } else if (index < 0) {
                visibles[counter] = "";
            } else if (index > adapter.getItemsCount() - 1) {
                visibles[counter] = "";
            } else {
                visibles[counter] = adapter.getItem(index);
            }

            counter++;
        }

        // 绘制中间两条横线 todo : 分割线类型暂停
        if (dividerType == DividerType.WRAP) {  // 横线长度仅包裹内容
            float startX;
            float endX;
        } else {
            canvas.drawLine(0.0F,firstLineY,measureWidth,firstLineY,paintIndicator);
            canvas.drawLine(0.0F,secondLinY,measureWidth,secondLinY,paintIndicator);
        }

        // 只显示选中项Label文字的模式，并且Label文字不为空，则进行绘制
        if (!TextUtils.isEmpty(label) && isCenterLabel) {
            // 绘制文字，靠右并留出空隙
            int drawRightContentStart = measureWidth - getTextWidth(paintCenterText,label);
            canvas.drawText(label,drawRightContentStart - 0,centerY,paintCenterText);   //todo : 忽略偏移量
        }

        counter = 0;
        while(counter < itemsVisible) {
            canvas.save();
            // 弧长 L = item * couner - itemHeightOffset
            // 求弧度 α= L / r (弧长/半径) 【0，Π】
            double radian = (itemHeight * counter - itemHeightOffset)/radius;
            // 弧度转换成角度（把半圆以Y轴为轴心向右转90度，使其处于第一象限及第四象限）
            // angle[-90,90]
            float angle = (float)(90D - (radian / Math.PI) * 180D); // item第一项，从90度开始，逐渐递减到 -90度。
            //Log.e(TAG,"角度 == " + angle);  //[90 --> -90]
            //计算取值可能有细微偏差，保证-90度到90度意外的不绘制
            if (angle >= 90F || angle <= -90F) {
                canvas.restore();
            } else {
                // 根据当前角度计算出偏差系数，用以在绘制时控制文字的 水平移动 透明度 倾斜程度
                float offsetCoefficient = (float) Math.pow(Math.abs(angle) / 90f,2.2);
                // 获取内容文字
                String contentText;

                // 如果时label每项都显示的模式，并且item内容不为空/label也不为空
                if (!isCenterLabel && !TextUtils.isEmpty(label) && !TextUtils.isEmpty(getContentText(visibles[counter]))) {
                    contentText = getContentText(visibles[counter]) + label;
                } else {
                    contentText = getContentText(visibles[counter]);
                }

                remeasureTextSize(contentText);
                // 计算开始绘制的位置
                measuredCenterContentStart(contentText);
                measuredOutContentStart(contentText);
                //Log.e(TAG,"半径 == " + radius + "弧度 == " + radian);
                float translateY = (float)(radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                //根据Math.sin(radian)来更改canvas坐标系原点，然后缩放画布，使得文字高度进行缩放，形成弧形3D视觉差
                /*Log.e(TAG,"canvas 的平移距离 == " + translateY + "    文本的最大的高度 == " + maxTextHeight +
                        "        第一条线的Y == " + firstLineY + "      第二条线的Y == " + secondLinY);*/
                canvas.translate(0.0f,translateY);
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    // 条目经过第一条线
                    canvas.save();
                    canvas.clipRect(0,0,measureWidth,firstLineY - translateY);
                    canvas.scale(1.0f, (float) (Math.sin(radian) * SCALE_CONTENT));
                    canvas.drawText(contentText,drawOutContentStart,maxTextHeight,paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0,firstLineY - translateY,measureWidth,(int)(itemHeight));
                    canvas.scale(1.0f, (float) (Math.sin(radian) * 1.0f));
                    canvas.drawText(contentText,drawCenterContentStart,maxTextHeight - 0,paintCenterText);  // todo 偏移量
                    canvas.restore();
                } else if (translateY <= secondLinY && maxTextHeight + translateY >= secondLinY) {
                    // 条目经过第二条线
                    canvas.save();
                    canvas.clipRect(0,0,measureWidth,secondLinY - translateY);
                    canvas.scale(1.0f, (float) (Math.sin(radian) * 1.0f));
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0,secondLinY - translateY,measureWidth,itemHeight);
                    canvas.scale(1.0f, (float) (Math.sin(radian) * SCALE_CONTENT));
                    canvas.drawText(contentText,drawOutContentStart,maxTextHeight,paintOuterText);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLinY) {
                    // 中间条目
                    canvas.clipRect(0,0,measureWidth,maxTextHeight);
                    // 让文字居中
                    // 因为圆弧度角换算的向下取值，导致角度稍微有点偏差，加上画笔的基线会偏上，因此需要偏移量修正
                    float Y = maxTextHeight - 0;        // todo : 偏移量置0
                    Log.e(TAG,contentText + "x = " + drawCenterContentStart + "  y == " + Y);
                    canvas.drawText(contentText,drawCenterContentStart,Y,paintCenterText);

                    // 设置选中项
                    selectedItem = preCurrentIndex - (itemsVisible / 2 - counter);
                } else {
                    // 其他条目
                    canvas.save();
                    canvas.clipRect(0,0,measureWidth,itemHeight);
                    canvas.scale(1.0f, (float) (Math.sin(radian) * SCALE_CONTENT));
                    // 控制文字倾斜角度
                    paintOuterText.setTextSkewX((textXOffset == 0 ? 0 : (textXOffset > 0 ? 1 : -1)) * (angle > 0 ? -1 : 1) * DEFAULT_TEXT_TARGET_SKEWX * offsetCoefficient);
                    // 控制透明度
                    paintOuterText.setAlpha((int) ((1 - offsetCoefficient) * 255));
                    // 控制文字水平偏移距离
                    canvas.drawText(contentText,drawOutContentStart + textXOffset * offsetCoefficient,maxTextHeight,paintOuterText);
                    canvas.restore();
                }
                canvas.restore();
                paintCenterText.setTextSize(textSize);
            }
            counter++;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        paintOuterText.getTextBounds(content, 0, content.length(), rect);
        //todo 现在文字只居中
        if (isOptions || label == null || label.equals("") || !isCenterLabel) {
            drawOutContentStart = (int) ((measureWidth - rect.width()) * 0.5);
        } else {//只显示中间label时，时间选择器内容偏左一点，留出空间绘制单位标签
            drawOutContentStart = (int) ((measureWidth - rect.width()) * 0.25);
        }
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(content,0,content.length(),rect);
        // todo 先让文字居中
        if (isOptions || label == null || label.equals("") || !isCenterLabel) {
            drawCenterContentStart = (int)((measureWidth - rect.width()) * 0.5);
        } else {
            // 只显示中间label时，时间选择器内容偏左一点，留出空间绘制单位标签
            drawCenterContentStart = (int) ((measureWidth - rect.width()) * 0.25);
        }
    }

    /**
     *  重置文字的大小，让文字全部显示
     */
    private void remeasureTextSize(String contentText) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(contentText,0,contentText.length(),rect);
        int width = rect.width();
        int size = textSize;
        while(width > measureWidth) {
            size--;
            // 设置两条横线中间的文字大小
            paintCenterText.setTextSize(size);
            paintCenterText.getTextBounds(contentText,0,contentText.length(),rect);
            width = rect.width();
        }
        // 设置2条横线外边的文字大小
        paintOuterText.setTextSize(size);
    }

    /**
     *  计算文字宽度
     */
    public int getTextWidth(Paint paint,String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str,widths);
            for (int j = 0;j < len;j++) {
                iRet += (int)Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     *  递归计算出对应的index
     */
    private int getLoopMappingIndex(int index) {
        if (index < 0) {
            index = index + adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        } else if (index > adapter.getItemsCount() - 1) {
            index = index - adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        }
        return index;
    }

    private void remeasure(){
        if (adapter == null) {
            return;
        }

        measureTextWidthHeight();

        // 半圆的周长 = itme高度乘以item数目 - 1；（近似半径）
        int halfCircumference = (int)(itemHeight * (itemsVisible - 1));

        double hudu = Math.PI / (2 * (itemsVisible - 1));
        double r = itemHeight / (2 * Math.sin(hudu));
        Log.e(TAG,"半径 == " + r + "     半圆周长 == " + (Math.PI * r));
        // 整个圆的周长除以PI得到直径，这个直径用于控件的总高度
        measureHeigh = (int)((halfCircumference * 2) / Math.PI);
        Log.e(TAG,"近似控件的高度 == " + measureHeigh);
        double height = 2 * r * Math.cos(hudu);
        Log.e(TAG,"精确控件的高度 == " + height);
        // 控件的宽度，这里支持weight
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);

        // 求出半径
        radius = (int)(halfCircumference / Math.PI);
        Log.e(TAG,"近似算出来的半圆周长 == " + halfCircumference + "      半径 == " + radius);

        // 计算两条横线 和 选中项画笔的基线Y位置
        firstLineY = (measureHeigh - itemHeight) / 2.0f;
        secondLinY = (measureHeigh + itemHeight) / 2.0f;
        centerY = secondLinY - (itemHeight - maxTextHeight) / 2.0f;// todo 不处理偏移量

        // 初始化显示的item的position
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (adapter.getItemsCount() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }

        preCurrentIndex = initPosition;
    }

    /**
     *  计算最大length的Text的宽高度
     */
    private void measureTextWidthHeight(){
        Rect rect = new Rect();
        for (int i = 0;i < adapter.getItemsCount();i++) {
            String s1 = getContentText(adapter.getItem(i));
            paintCenterText.getTextBounds(s1,0,s1.length(),rect);
            int textWidth = rect.width();
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
            paintCenterText.getTextBounds("\u661F\u671F", 0, 2, rect); // 星期的字符编码（以它为标准高度）
            maxTextHeight = rect.height() + 2;
        }
        itemHeight = 1 * maxTextHeight + 20;     // todo lineSpacingMultiplier * maxTextHeight
        //Log.e(TAG,)
    }

    /**
     *  获取所显示的数据源
     */
    private String getContentText(Object item) {
        if (item == null) {
            return "";
        } else {            // todo 先默认都是字符串类型
            return item.toString();
        }
    }

    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        remeasure();
        invalidate();
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

    /**
     *  获取item的个数
     * @return
     */
    public int getItemsCount(){
        // todo 需要adapter；
        return 0;
    }

    public boolean isLoop(){
        return isLoop;
    }

    public float getTotalScrollY(){
        return totalScrollY;
    }

    public void setTotalScrollY(float totalScrollY) {
        this.totalScrollY = totalScrollY;
    }

    public float getItemHeight(){
        return itemHeight;
    }

    public int getInitPosition(){
        return initPosition;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}
