package com.dahai.ownview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dahai.ownview.ui.fragment.BlankFragment;
import com.dahai.ownview.ui.fragment.ViewgroupFragment;
import com.dahai.ownview.ui.view.ExpandableTextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //getSupportFragmentManager().beginTransaction().add(R.id.main_content,new ViewgroupFragment()).commit();
    }
}
