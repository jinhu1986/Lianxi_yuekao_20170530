package com.jinhu.lianxi_yuekao_20170530.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinhu.lianxi_yuekao_20170530.R;
import com.jinhu.lianxi_yuekao_20170530.bean.ZhuTiBean;
import com.jinhu.lianxi_yuekao_20170530.util.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/31  9:54
 */

public class RvZtAdapter extends RecyclerView.Adapter<RvZtAdapter.ViewHolder> {
    //点击
    private OnItemClickListener mOnItemClickListener;
    //长按
    private OnItemLongClickListener mOnItemLongClickListener;
    //点击
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //长按
    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    //点击监听接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    //长按监听接口
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }
    private Context mContext;
    private List<ZhuTiBean.OthersBean> mList;

    public RvZtAdapter(Context context, List<ZhuTiBean.OthersBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_zhuti, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(mList.get(position).getThumbnail(), holder.mImageView, ImageUtils.getOptions());
        holder.mTextView.setText(mList.get(position).getName());
        //单击判断
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_zt);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_zt);
        }
    }
}
