package com.sanxiongdi.stopcar.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * 通用的Recyler的viewHolder
 *
 * Created by wuaomall@gmail.com on 2017/4/14.
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;
    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }
    public abstract void onBindViewHolder(int position,List<T> mData);



}