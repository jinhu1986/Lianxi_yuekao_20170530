package com.jinhu.lianxi_yuekao_20170530.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.jinhu.lianxi_yuekao_20170530.R;
import com.jinhu.lianxi_yuekao_20170530.adapter.VpAdapter;
import com.jinhu.lianxi_yuekao_20170530.fragment.Remen;
import com.jinhu.lianxi_yuekao_20170530.fragment.ZhuTi;
import com.jinhu.lianxi_yuekao_20170530.fragment.Zhuanlan;
import com.jinhu.lianxi_yuekao_20170530.fragment.Zuixin;
import com.jinhu.lianxi_yuekao_20170530.util.MagicUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {


    private MagicIndicator magic;
    private ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMagic();
        initViewPager();
    }

    private void initViewPager() {
        Zuixin zuixin = new Zuixin();
        Remen remen = new Remen();
        Zhuanlan zhuanlan = new Zhuanlan();
        ZhuTi zhuTi = new ZhuTi();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(zuixin);
        fragments.add(remen);
        fragments.add(zhuanlan);
        fragments.add(zhuTi);
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        view_pager.setAdapter(adapter);
    }

    private void initMagic() {
        List<String> datas = new ArrayList<>();
        datas.add("最新日报");
        datas.add("专栏");
        datas.add("热门");
        datas.add("主题日报");
        MagicUtils.initMagicIndicator(this, magic, view_pager, datas);
    }

    private void initView() {
        magic = (MagicIndicator) findViewById(R.id.magic);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
    }
}
