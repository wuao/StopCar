package com.sanxiongdi.stopcar.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin.woo on 2017/5/5.
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected Context mContext;
    protected List<T> list = new ArrayList<>();

    public BaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        if (list != null) {
            this.list.addAll(list);
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.onBindViewHolder(position, list);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
