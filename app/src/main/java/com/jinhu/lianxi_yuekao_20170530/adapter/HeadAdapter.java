package com.jinhu.lianxi_yuekao_20170530.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinhu.lianxi_yuekao_20170530.R;
import com.jinhu.lianxi_yuekao_20170530.bean.InfoBean;
import com.jinhu.lianxi_yuekao_20170530.bean.ZuiXinBean;
import com.jinhu.lianxi_yuekao_20170530.util.GlideImageLoader;
import com.jinhu.lianxi_yuekao_20170530.util.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/6/1  20:29
 */

public class HeadAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private Context mContext;
    private List<InfoBean> mList;
    List<ZuiXinBean.TopStoriesBean> top_stories;

    public HeadAdapter(Context context, List<InfoBean> list, List<ZuiXinBean.TopStoriesBean> top_stories) {
        mContext = context;
        mList = list;
        this.top_stories = top_stories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = View.inflate(mContext, R.layout.item_zx, null);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = View.inflate(mContext, R.layout.item_foot, null);
            return new FootViewHolder(view);
        } else if (viewType == TYPE_HEAD) {
            View view = View.inflate(mContext, R.layout.item_head, null);
            return new HeadViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case 0:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                Banner banner = headViewHolder.mBanner;
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
                break;
            case 1:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                if (position != 0 || (position + 2) != getItemCount()) {
                    Logger.d(position);
                    ImageLoader.getInstance().displayImage(mList.get(position - 1).getImage(), itemViewHolder.mImageView, ImageUtils.getOptions());
                    itemViewHolder.mTextView.setText(mList.get(position - 1).getText());
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position + 2 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public ItemViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_item_zx);
            mImageView = (ImageView) view.findViewById(R.id.iv_item_zx);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        Banner mBanner;

        public HeadViewHolder(View view) {
            super(view);
            mBanner = (Banner) view.findViewById(R.id.banner);
        }
    }
}
