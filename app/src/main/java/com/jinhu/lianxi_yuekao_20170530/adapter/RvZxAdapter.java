package com.jinhu.lianxi_yuekao_20170530.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinhu.lianxi_yuekao_20170530.R;
import com.jinhu.lianxi_yuekao_20170530.bean.InfoBean;
import com.jinhu.lianxi_yuekao_20170530.util.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  20:38
 */

public class RvZxAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<InfoBean> mList;
    private Context mContext;

    public RvZxAdapter(List<InfoBean> list, Context context) {
        mList = list;
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = View.inflate(mContext, R.layout.item_zx, null);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = View.inflate(mContext, R.layout.item_foot, null);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ImageLoader.getInstance().displayImage(mList.get(position).getImage(), itemViewHolder.mImageView, ImageUtils.getOptions());
            itemViewHolder.mTextView.setText(mList.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
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

}
