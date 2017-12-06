package com.dahai.ownview.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dahai.ownview.R;
import com.dahai.ownview.ui.view.CalendarView;
import com.dahai.ownview.ui.view.MonthView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewgroupFragment extends Fragment {
    private View rootView;
    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_viewgroup,container,false);
        initView();
        return rootView;
    }

    private void initView() {
        calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setListener(new MonthView.SelectListener() {
            @Override
            public void selected(String year, String month, String day) {
                Toast.makeText(getContext(), year + ":" + month + ":" + day , Toast.LENGTH_SHORT).show();
            }
        });
    }


}
