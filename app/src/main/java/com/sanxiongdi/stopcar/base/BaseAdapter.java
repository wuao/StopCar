package com.sanxiongdi.stopcar.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin.woo on 2017/5/5.
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected Context mContext;
    protected List<T> data = new ArrayList<>();
    protected LayoutInflater inflater;

    public BaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        if (list != null) {
            this.data.addAll(list);
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.onBindViewHolder(position, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> list) {
        if (list != null) {
            this.data = list;
        }
    }
}
