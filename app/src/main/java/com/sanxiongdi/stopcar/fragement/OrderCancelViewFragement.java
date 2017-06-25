package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.activity.order.OrderDetailsActivity;
import com.sanxiongdi.stopcar.adapter.OrderListAdapter;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.holder.ItemClickSupport;
import com.sanxiongdi.stopcar.presenter.QueryOrderPresenter;
import com.sanxiongdi.stopcar.presenter.view.IQueryOrder;
import com.sanxiongdi.stopcar.uitls.recyclerview.OnLoadListener;

import java.util.List;

/**
 * 取消列表界面
 * <p>
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class OrderCancelViewFragement extends BaseFrament implements IQueryOrder {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout layout_swipe_refresh;
    private OrderListAdapter adapter;
    private QueryOrderPresenter presenter;
    private LinearLayoutManager llm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected int setLayoutResouceId() {

        return R.layout.order_cancel_view;

    }

    @Override
    protected void initDate(Bundle mbundle) {
        adapter = new OrderListAdapter(mContext, null);
        presenter = new QueryOrderPresenter(mContext, this);
        presenter.queryCancelOrder();
    }

    @Override
    protected void onsetListener() {

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, View itemView, int position) {
                //跳转到详情页面
                Intent intent=new Intent();
                intent.putExtra("ordername",adapter.getData().get(position).name);
                intent.setClass(getContext(), OrderDetailsActivity.class);
                startActivity(intent);
            }
        });
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, View itemView, int position) {
                //                Toast.makeText(mContext, "support long click name:" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mRecyclerView.addOnScrollListener(new OnLoadListener(llm) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.queryCancelOrderMore();
            }
        });
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    protected void initView() {
        llm = new LinearLayoutManager(getContext());
        mRecyclerView = (RecyclerView) mrootView.findViewById(R.id.cancle_recycler_view);
        layout_swipe_refresh = (SwipeRefreshLayout) mrootView.findViewById(R.id.layout_cancel_swipe_refresh);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        layout_swipe_refresh.setProgressViewOffset(true, 0, 200);
        layout_swipe_refresh.setDistanceToTriggerSync(20);
        layout_swipe_refresh.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        layout_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.queryCancelOrder();
            }
        });
    }

    @Override
    public void queryOrderSuccess(List<QueryOrderEntity> list) {
        if (presenter.getOffset() == 0) {
            adapter.getData().clear();
        }
        adapter.getData().addAll(list);
        adapter.notifyDataSetChanged();
        layout_swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                layout_swipe_refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void queryOrderFailure(boolean isRequest, int code, String msg) {
        layout_swipe_refresh.post(new Runnable() {
            @Override
            public void run() {
                layout_swipe_refresh.setRefreshing(false);
            }
        });
    }
    @Override
    public void queryOrderDetailsFailure(boolean isRequest, int code, String msg) {

    }

    @Override
    public void queryOrderDetailsSuccess(List<QueryOrderEntity> list) {
        adapter.setData(list);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
