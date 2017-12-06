package com.dahai.ownview.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dahai.ownview.R;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 张海洋 on 2017-12-04.
 */

public class CalendarView extends FrameLayout implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private static final String TAG = "CalendarView";

    private Context context;
    private DisTouchViewpager viewPager;
    private TextView tv_previousMonth;
    private TextView tv_nextMonth;
    private TextView tv_content;
    private MonthView.SelectListener listener;

    private int year;            // 年
    private int month;           // 月
    private int day;             // 天；
    private int week;            // 星期；
    private int weekDayOff;     // 月初的星期的偏移量。

    private int VPpositoin;

    private int startMonth;     // 开始的月份
    private int endMonth;       // 结束的月份。
    private int currentMonth;   //

    public CalendarView(@NonNull Context context) {
        this(context,null);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.view_calendar, this, false);
        initData();
        setView(contentView);
        addView(contentView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Log.e("星期 = ",calendar.get(Calendar.DAY_OF_WEEK) + "");

        endMonth = month;
        initMonthStart(month);
        currentMonth = month;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*for (int i = 0;i < )
        setMeasuredDimension();*/
    }

    private void initMonthStart(int month) {
        for (int i = 0;i < 3;i++) {
            if (month == 0) {
                month = 12;
                year--;
            }
            month--;
        }

        startMonth = month;
    }

    private void setView(View contentView) {
        tv_previousMonth = (TextView) contentView.findViewById(R.id.tv_calendar_previous);
        tv_content = (TextView) contentView.findViewById(R.id.tv_calendar_content);
        tv_nextMonth = (TextView) contentView.findViewById(R.id.tv_calendar_next);
        viewPager = (DisTouchViewpager) contentView.findViewById(R.id.viewpager_calendar);
        viewPager.addOnPageChangeListener(this);

        tv_previousMonth.setOnClickListener(this);
        tv_nextMonth.setOnClickListener(this);
        tv_content.setText(getCurrentTime(currentMonth));

        viewPager.setAdapter(new VpAdapter(4));
        VPpositoin = 3;
        viewPager.setCurrentItem(3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_calendar_previous:
                if (currentMonth == startMonth) {
                    return;
                }
                if (currentMonth == 0) {
                    currentMonth = 11;
                } else {
                    currentMonth--;
                }
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

                tv_content.setText(getCurrentTime(currentMonth));
                break;
            case R.id.tv_calendar_next:
                if (currentMonth == endMonth) {
                    return;
                }
                if (currentMonth == 11) {
                    currentMonth = 0;
                } else {
                    currentMonth++;
                }

                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                tv_content.setText(getCurrentTime(currentMonth));
                break;
        }
    }

    private String getCurrentTime(int month) {
        return year + "年" + (month + 1) + "月";
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        /*if (position > VPpositoin) {
            month++;
        } else {
            month--;
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class VpAdapter extends PagerAdapter{
        private int count;

        public VpAdapter(int count) {
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e("position == ",position + "");
            int month = startMonth;
            for (int i = 0; i < position;i++) {
                month = changeMonth(month,true);
            }
            Log.e("选择的月份 = ","" + month);
            int monthDays = getMonthDays(month);
            weekDayOff = getWeekDayOff(year,month);
            View view = new MonthView(context,monthDays,weekDayOff - 1,month,year,listener);
            container.addView(view);
            //ViewGroup.LayoutParams params = view.getLayoutParams();
            int dp20 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,context.getResources().getDisplayMetrics());
            //view.setLayoutParams(params);
            container.setPadding(dp20,dp20,0,0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View)object));
        }
    }

    /**
     *  周日是1；周六是7；
     */
    private int getWeekDayOff(int year,int month) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year,month,1);

        Log.e(TAG,"月份：" + (month + 1));

        weekDayOff = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e(TAG,"星期的偏移量：" + weekDayOff);
        return weekDayOff;
    }

    private int getMonthDays(int month) {
        int monthDays = 31;
        switch (month + 1) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                monthDays = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                monthDays = 30;
                break;
            case 2:
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                break;
        }

        return monthDays;
    }

    /**
     *  月份加一和减一之后的实际月份。
     * @param month 改变之前的月份。
     * @param flag true == 》 加一；false == 》 减一；
     * @return 返回改变之后的月份。
     */
    public int changeMonth(int month,boolean flag){

        if (flag) {
            if (month == 11) {
                month = 0;
                year++;
            } else {
                ++month;
            }
        } else {
            if (month == 0) {
                month = 11;
                year--;
            } else {
                --month;
            }
        }
        return month;
    }

    public void setListener(MonthView.SelectListener listener) {
        this.listener = listener;
    }
}
