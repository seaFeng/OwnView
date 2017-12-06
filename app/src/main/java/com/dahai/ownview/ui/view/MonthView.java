package com.dahai.ownview.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahai.ownview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张海洋 on 2017-12-04.
 */

public class MonthView extends RecyclerView{
    private Context context;
    private int monthDays;                                   // 一个月的天数。
    private int weekOff;                                     // 星期的偏移量。
    private int year;
    private int month;
    private SelectListener listener;

    private List<String> dayList = new ArrayList<>();        //
    private boolean[] selects;

    public MonthView(Context context) {
        this(context,null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public MonthView(Context context,int monthDays,int weekOff,int month,int year,SelectListener listener) {
        this(context);
        this.monthDays = monthDays;
        this.weekOff = weekOff;
        this.month = month;
        this.year = year;
        this.listener = listener;
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(context,7));
        int maxSize = monthDays + 7 + weekOff;
        selects = new boolean[maxSize];
        for (int i = 0;i < maxSize;i++) {
            selects[i] = false;
        }

        /*for (int i = 0;i <= maxSize;i++){
            switch (i) {
                case 0:
                    dayList.add("日");
                    break;
                case 1:
                    dayList.add("一");
                    break;
                case 2:
                    dayList.add("二");
                    break;
                case 3:
                    dayList.add("三");
                    break;
                case 4:
                    dayList.add("四");
                    break;
                case 5:
                    dayList.add("五");
                    break;
                case 6:
                    dayList.add("六");
                    break;
            }

            if (i > 6 && i <= (6+weekOff)) {
                dayList.add("");
                return;
            }

            dayList.add(String.valueOf(i - 6 - weekOff));
        }*/

        setAdapter(new MonthAdapter());
    }

    class MonthAdapter extends Adapter<MonthAdapter.MonthViewHolder> {
        // 一个月的天数
        /*private int monthDays;

        public MonthAdapter(int monthDays) {
            this.monthDays = monthDays;
        }*/

        @Override
        public MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            TextView textView = new TextView(context);
            /*ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();

            textView.setLayoutParams(layoutParams);*/
            return new MonthViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(MonthViewHolder holder, int position) {

            if (selects[position]) {
                holder.tv_day.setBackgroundResource(R.drawable.circle_ff9600);
                holder.tv_day.setTextColor(0xFFFFFFFF);
            } else {
                holder.tv_day.setBackgroundColor(0xFFFFFFFF);
                holder.tv_day.setTextColor(0xFF333333);
            }


            if (position >= 0 && position <= 6) {
                switch (position) {
                    case 0:
                        holder.tv_day.setText("日");
                        holder.tv_day.setTextColor(0xFF848484);
                        break;
                    case 1:
                        holder.tv_day.setText("一");
                        holder.tv_day.setTextColor(0xFF848484);
                        break;
                    case 2:
                        holder.tv_day.setText("二");
                        holder.tv_day.setTextColor(0xFF848484);
                        break;
                    case 3:
                        holder.tv_day.setText("三");
                        holder.tv_day.setTextColor(0xFF848484);
                        break;
                    case 4:
                        holder.tv_day.setText("四");
                        holder.tv_day.setTextColor(0xFF848484);
                        break;
                    case 5:
                        holder.tv_day.setText("五");
                        holder.tv_day.setTextColor(0xFF848484);
                        break;
                    case 6:
                        holder.tv_day.setText("六");
                        holder.tv_day.setTextColor(0xFF848484);
                        break;
                }
                return;
            }

            if (position > 6 && position <= (6+weekOff)) {
                dayList.add("");
                return;
            }

            holder.tv_day.setText(String.valueOf(position - 6 - weekOff));
        }

        @Override
        public int getItemCount() {
            return monthDays + 7 + weekOff;
        }

        private void initSelectd() {
            for (int i = 0;i < getItemCount(); i++) {
                selects[i] = false;
            }
        }

        class MonthViewHolder extends ViewHolder implements OnClickListener{
            TextView tv_day;
            //private boolean selected;

            public MonthViewHolder(View itemView) {
                super(itemView);
                tv_day = (TextView) itemView;
                int dp27 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, context.getResources().getDisplayMetrics());
                tv_day.setLayoutParams(new ViewGroup.LayoutParams(dp27,dp27));
                tv_day.setGravity(Gravity.CENTER);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int adapterPosition = getAdapterPosition();

                if (adapterPosition >= 0 && adapterPosition <= (weekOff + 6)) {
                    return;
                }

                initSelectd();

                int day = adapterPosition - (weekOff + 6);
                listener.selected(String.valueOf(year),String.valueOf(month + 1),String.valueOf(day));

                if (selects[adapterPosition]) {
                    selects[adapterPosition] = false;
                } else {
                    selects[adapterPosition] = true;
                }
                notifyDataSetChanged();
            }
        }
    }

    public interface SelectListener{
        void selected(String year,String month,String day);
    }
}
