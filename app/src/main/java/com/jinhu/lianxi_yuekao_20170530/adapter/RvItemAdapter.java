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
 * 2017/5/31  13:35
 */

public class RvItemAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<InfoBean> mList;

    public RvItemAdapter(Context context, List<InfoBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view_01 = View.inflate(mContext, R.layout.item_item, null);
                return new ViewHolder_01(view_01);
            case 1:
                View view_02 = View.inflate(mContext, R.layout.item_item_02, null);
                return new ViewHolder_02(view_02);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                ViewHolder_01 holder_01 = (ViewHolder_01) holder;
                ImageLoader.getInstance().displayImage(mList.get(position).getImage(), holder_01.mImageView, ImageUtils.getOptions());
                holder_01.mTextView.setText(mList.get(position).getText());
                break;
            case 1:
                ViewHolder_02 holder_02 = (ViewHolder_02) holder;
                holder_02.mTextView.setText(mList.get(position).getText());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder_01 extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder_01(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_item);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_item);
        }
    }

    static class ViewHolder_02 extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder_02(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_item_02);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mList.get(position).getImage()) {
            return 1;
        } else {
            return 0;
        }
    }

}
