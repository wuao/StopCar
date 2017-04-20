package com.sanxiongdi.stopcar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.holder.AuthorizeRecylerHolder;

import java.util.List;


/**
 *
 *   授权的order 的Adapter 数据类型
 *
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class AuthorizeRecyclerAdapter extends  RecyclerView.Adapter<AuthorizeRecylerHolder>{

    private static final int NORMAL_ITEM = 0;
    private static final int GROUP_ITEM = 1;
    private List<String> mDatas;
    private Context  mcontext;
    private LayoutInflater mLayoutInflater;
      AuthorizeRecylerHolder authorizeRecylerHolder;
    public AuthorizeRecyclerAdapter(List<String> mDatas, Context mcontext, LayoutInflater mLayoutInflater) {
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
    public AuthorizeRecylerHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view =mLayoutInflater.from(mcontext).inflate(R.layout.order_authorize_list_item_view,viewGroup,false);
        authorizeRecylerHolder=new AuthorizeRecylerHolder(view);
        return  authorizeRecylerHolder ;
    }


    @Override
    public void onBindViewHolder(AuthorizeRecylerHolder holder, int position) {
        holder.user_info_name.setText(mDatas.get(position).toString());
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
