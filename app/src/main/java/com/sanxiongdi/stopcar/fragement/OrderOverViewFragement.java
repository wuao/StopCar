package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.adapter.OrderListAdapter;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.presenter.QueryOrderPresenter;
import com.sanxiongdi.stopcar.presenter.view.IQueryOrder;
import com.sanxiongdi.stopcar.uitls.RootLayout;
import com.sanxiongdi.stopcar.uitls.recyclerview.OnLoadListener;

import java.util.List;

/**
 * 完成列表界面
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class OrderOverViewFragement extends BaseFrament implements IQueryOrder {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout layout_swipe_refresh;
    private QueryOrderPresenter presenter;
    private OrderListAdapter adapter;
    private LinearLayoutManager llm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.order_over_view;
    }

    @Override
    protected void initDate(Bundle mbundle) {
        adapter = new OrderListAdapter(mContext, null);
        mRecyclerView.setAdapter(adapter);
        presenter = new QueryOrderPresenter(mContext, this);
        presenter.queryFinishOrder();
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    protected void initView() {
        llm = new LinearLayoutManager(getContext());
        mRecyclerView = (RecyclerView) mrootView.findViewById(R.id.over_recycler_view);
        layout_swipe_refresh = (SwipeRefreshLayout) mrootView.findViewById(R.id.layout_over_swipe_refresh);
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
                presenter.queryFinishOrder();
            }
        });
    }

    @Override
    protected void onsetListener() {
        mRecyclerView.addOnScrollListener(new OnLoadListener(llm) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.queryFinishOrderMore();
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
}
