package com.sanxiongdi.stopcar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.holder.ProceedRecylerHolder;

import java.util.List;

/**
 *
 *  蓝牙列表
 *
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class BlueRecyclerAdapter extends  RecyclerView.Adapter<ProceedRecylerHolder>{

    private static final int NORMAL_ITEM = 0;
    private static final int GROUP_ITEM = 1;
    private List<QueryOrderEntity>  mDatas;
    private Context  mcontext;
    private LayoutInflater mLayoutInflater;
    private ProceedRecylerHolder mProceedRecylerHolder;

    public BlueRecyclerAdapter(List<QueryOrderEntity> mDatas, Context mcontext, LayoutInflater mLayoutInflater) {
        this.mDatas = mDatas;
        this.mcontext = mcontext;
        this.mLayoutInflater = mLayoutInflater;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    /**
     *    渲染具体的holder
      * @param viewGroup    ViewHolder的容器 ViewHolder的容器
     * @param viewType   一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
     * @return
     */
    @Override
    public ProceedRecylerHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view =mLayoutInflater.from(mcontext).inflate(R.layout.order_uitls_item_view,viewGroup,false);
         mProceedRecylerHolder=new ProceedRecylerHolder(view);
        return  mProceedRecylerHolder ;
    }

    @Override
    public void onBindViewHolder(final ProceedRecylerHolder holder, final int position) {
        holder.user_info_name.setText(mDatas.get(position).toString());
        holder.onBindViewHolder(position,mDatas);
      }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }



}
