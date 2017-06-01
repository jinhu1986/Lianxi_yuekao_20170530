package com.jinhu.lianxi_yuekao_20170530.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jinhu.lianxi_yuekao_20170530.R;
import com.jinhu.lianxi_yuekao_20170530.adapter.RvZxAdapter;
import com.jinhu.lianxi_yuekao_20170530.bean.InfoBean;
import com.jinhu.lianxi_yuekao_20170530.bean.ZuiXinBean;
import com.jinhu.lianxi_yuekao_20170530.bean.ZuiXinMoreBean;
import com.jinhu.lianxi_yuekao_20170530.util.GlideImageLoader;
import com.jinhu.lianxi_yuekao_20170530.util.GsonUtils;
import com.jinhu.lianxi_yuekao_20170530.util.HttpUtils;
import com.jinhu.lianxi_yuekao_20170530.util.Url;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  19:12
 */

public class Zuixin extends Fragment {
    private Banner banner;
    private RecyclerView recycler_main;
    private SwipeRefreshLayout swipe_refresh_layout;
    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private RvZxAdapter mAdapter;
    boolean isLoading;
    private List<InfoBean> mList = new ArrayList<InfoBean>();
    ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zuixin, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getServerData();
        setListener();
    }

    private void setListener() {
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        getServerData();
                        Toast.makeText(mContext, "刷新了数据", Toast.LENGTH_SHORT).show();
                        swipe_refresh_layout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //
        recycler_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipe_refresh_layout.isRefreshing();
                    if (isRefreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //加载
                                getServerData2();
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
    }

    private void getServerData2() {
        HttpUtils.getEnqueue(Url.ZUI_XIN_MORE, null, new HttpUtils.MyCallback() {
            @Override
            public void onSuccess(String result) {
                ZuiXinMoreBean zuiXinMoreBean = GsonUtils.gsonToBean(result, ZuiXinMoreBean.class);
                List<ZuiXinMoreBean.StoriesBean> stories = zuiXinMoreBean.getStories();
                for (int i = 0; i < stories.size(); i++) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setImage(stories.get(i).getImages().get(0));
                    infoBean.setText(stories.get(i).getTitle());
                    mList.add(infoBean);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    private void getServerData() {
        HttpUtils.getEnqueue(Url.ZUI_XIN, null, new HttpUtils.MyCallback() {
            @Override
            public void onSuccess(String result) {
                ZuiXinBean zuiXinBean = GsonUtils.gsonToBean(result, ZuiXinBean.class);
                List<ZuiXinBean.TopStoriesBean> top_stories = zuiXinBean.getTop_stories();
                List<ZuiXinBean.StoriesBean> stories = zuiXinBean.getStories();
                Logger.d(top_stories.size());
                initBanner(top_stories);

                for (int i = 0; i < stories.size(); i++) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setImage(stories.get(i).getImages().get(0));
                    infoBean.setText(stories.get(i).getTitle());
                    mList.add(infoBean);
                }
                initRv(mList);
                swipe_refresh_layout.setRefreshing(false);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    Handler mHandler = new Handler();

    private void initRv(List<InfoBean> list) {
        mLayoutManager = new LinearLayoutManager(mContext);
        recycler_main.setLayoutManager(mLayoutManager);
        mAdapter = new RvZxAdapter(list, mContext);
        recycler_main.setAdapter(mAdapter);
    }

    private void initBanner(List<ZuiXinBean.TopStoriesBean> top_stories) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < top_stories.size(); i++) {
            images.add(top_stories.get(i).getImage());
            titles.add(top_stories.get(i).getTitle());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initView(View view) {
        banner = (Banner) view.findViewById(R.id.banner);
        recycler_main = (RecyclerView) view.findViewById(R.id.recycler_main);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(true);
            }
        });
    }
}
