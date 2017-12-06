package com.dahai.ownview.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dahai.ownview.R;
import com.dahai.ownview.ui.view.CircleProgressView;
import com.dahai.ownview.ui.view.TopBar;
import com.dahai.ownview.ui.view.VolumeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    TopBar topbar;
    CircleProgressView circleProgressView;
    VolumeView volumeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank,container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        topbar = (TopBar) rootView.findViewById(R.id.topbar);
        topbar.setOnTopbarClickListener(new TopBar.TopbarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(getActivity(), "左键被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(getContext(), "右键被点击", Toast.LENGTH_SHORT).show();
            }
        });

        // circleProgressView:
        /*circleProgressView = (CircleProgressView) rootView.findViewById(R.id.circleProgressView);
        circleProgressView.setSweepValue(300);*/
        // 声音控件。
        volumeView = (VolumeView) rootView.findViewById(R.id.volumeView);

    }

}
