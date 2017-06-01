package com.jinhu.lianxi_yuekao_20170530.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinhu.lianxi_yuekao_20170530.R;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  19:17
 */

public class Zhuanlan extends Fragment {
    private TextView tv_frag_kong;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kong, null);
        initView(view);
        return view;
    }

/*    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        tv_frag_kong.setText("专栏页");
    }

    private void initView(View view) {
        tv_frag_kong = (TextView) view.findViewById(R.id.tv_frag_kong);
    }
}
