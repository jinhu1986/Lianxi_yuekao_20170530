package com.jinhu.lianxi_yuekao_20170530.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  19:34
 */

public class VpAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public VpAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
