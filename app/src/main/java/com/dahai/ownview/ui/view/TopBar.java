package com.dahai.ownview.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dahai.ownview.R;

/**
 * Created by 张海洋 on 2017-08-26.
 */

public class TopBar extends RelativeLayout {

    // 包含topbar上的元素：做按钮、有按钮、标题。
    private Button mLeftButton,mRightButton;
    private TextView mTitleView;

    // 左按钮的属性值，即我们在atts.xml文件中设置的属性
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    // 右按钮的属性值，即我们在atts.xml文件中设置的属性
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    // 标题的属性值，即我们在atts.xml文件中定义的属性
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;

    // 回调接口：
    private TopbarClickListener mListener;

    //布局属性，用来控制控件在ViewGroup中的位置。
    private LayoutParams mLeftParams,mTitleParams,mRightParams;

    public TopBar(Context context) {
        this(context,null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 设置topbar的背景。
        setBackgroundColor(0xFFF59563);
        // 通过这个方法，将你在attrs.xml中定义的declare-styleable
        // 的所有属性的值存储到TypedArray中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        // 从TypedArray中取出对应的值来为要设置的属性赋值。
        mLeftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor,0);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_leftBackgroud);
        mLeftText = ta.getString(R.styleable.TopBar_leftText);

        mRightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor,0);
        mRightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        mRightText = ta.getString(R.styleable.TopBar_rightText);

        mTitleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize,10);
        mTitleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor,0);
        mTitle = ta.getString(R.styleable.TopBar_title);

        ta.recycle();
        // 初始化View.
        initView(context);
    }

    private void initView(Context context) {
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new Button(context);

        // 左按钮
        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackgroundDrawable(mLeftBackground);
        mLeftButton.setText(mLeftText);

        mLeftParams = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLeftParams.addRule(ALIGN_PARENT_LEFT,TRUE);
        addView(mLeftButton,mLeftParams);

        // 右按钮
        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackgroundDrawable(mRightBackground);
        mRightButton.setText(mRightText);

        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(mRightButton,mRightParams);

        // 标题
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setText(mTitle);
        mTitleView.setGravity(Gravity.CENTER);

        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(mTitleView,mTitleParams);

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.rightClick();
                }
            }
        });

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.leftClick();
                }
            }
        });
    }

    public void setOnTopbarClickListener(TopbarClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     *  设置左右按钮的显示或隐藏，
     * @param id            0 --> 左按钮；1 --> 右按钮。
     * @param flag          是否显示。
     */
    public void setButtonVisiable(int id,boolean flag) {
        if (flag) {
            if (id == 0) {
                mLeftButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (id == 0) {
                mLeftButton.setVisibility(View.GONE);
            } else{
                mRightButton.setVisibility(View.GONE);
            }
        }
    }

    /**
     *  回调接口
     */
    public interface TopbarClickListener{
        // 左按钮点击事件
        void leftClick();
        // 有按钮点击事件
        void rightClick();
    }
}
