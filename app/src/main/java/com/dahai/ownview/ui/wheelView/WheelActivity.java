package com.dahai.ownview.ui.wheelView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dahai.ownview.R;
import com.dahai.ownview.ui.wheelView.adapter.WheelAdapter;

import java.util.ArrayList;
import java.util.List;

public class WheelActivity extends AppCompatActivity {
    private WheelView wheelView;
    private WheelAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        init();
    }

    private void init(){
        wheelView = (WheelView) findViewById(R.id.wheelView);
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("刘六");
        wheelView.setAdapter(new WheelViewAdapter(list));
    }

    class WheelViewAdapter implements WheelAdapter<String> {
        private List<String> list;

        public WheelViewAdapter(List<String> list){
            this.list = list;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        public String getItem(int index) {
            return list.get(index);
        }

        @Override
        public int indexOf(String o) {
            return list.indexOf(o);
        }
    }
}
