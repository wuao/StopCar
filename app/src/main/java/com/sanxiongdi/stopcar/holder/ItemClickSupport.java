package com.sanxiongdi.stopcar.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wuaomall@gmail.com on 2017/4/20.
 */

public class ItemClickSupport {

    private static final int KEY = 0x99999999;
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, v, holder.getAdapterPosition());
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, v, holder.getAdapterPosition());
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {

        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
        }
    };

    /**
     * ItemClickSupport的私有构造方法
     */
    private ItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(KEY, this);
        // 为RecyclerView设置OnChildAttachStateChangeListener事件监听
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    /**
     * 为RecyclerView设置ItemClickSupport
     */
    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(KEY);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    /**
     * 为RecyclerView移除ItemClickSupport
     */
    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(KEY);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    /**
     * 为RecyclerView设置点击事件监听
     */
    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    /**
     * 为RecyclerView设置长按事件监听
     */
    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    /**
     * 为RecyclerView移除OnChildAttachStateChangeListener事件监听
     */
    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(KEY, null);
    }

    /**
     * RecyclerView的点击事件监听接口
     */
    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, View itemView, int position);
    }

    /**
     * RecyclerView的长按事件监听接口
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, View itemView, int position);
    }
}