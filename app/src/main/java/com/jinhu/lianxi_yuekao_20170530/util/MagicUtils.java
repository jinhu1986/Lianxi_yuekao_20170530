package com.jinhu.lianxi_yuekao_20170530.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.List;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  18:50
 */

public class MagicUtils {

    private static CommonNavigator sMCommonNavigator;

    /**
     * 指示器初始化类
     *
     * @param context         上下文
     * @param mMagicIndicator 指示器控件
     * @param mViewPager      viewpager
     * @param mDataList       指示器的数据
     * @return
     */
    public static void initMagicIndicator(Context context, MagicIndicator mMagicIndicator, final ViewPager mViewPager, final List<String> mDataList) {
        mMagicIndicator.setBackgroundColor(Color.parseColor("#3F51B5"));
        sMCommonNavigator = new CommonNavigator(context);
        sMCommonNavigator.setSkimOver(true);
        sMCommonNavigator.setAdjustMode(true);//设置宽度可变
        sMCommonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(sMCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }
}
