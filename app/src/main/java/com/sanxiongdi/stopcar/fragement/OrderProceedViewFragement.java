package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.activity.order.OrderDetailsActivity;
import com.sanxiongdi.stopcar.adapter.OrderListAdapter;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.event.OrderRrefeshEvent;
import com.sanxiongdi.stopcar.holder.ItemClickSupport;
import com.sanxiongdi.stopcar.holder.ProceedOrderHolder;
import com.sanxiongdi.stopcar.presenter.QueryOrderPresenter;
import com.sanxiongdi.stopcar.presenter.view.IQueryOrder;
import com.sanxiongdi.stopcar.uitls.recyclerview.OnLoadListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * 进行中的列表界面
 * Created by wuaomall@gmail.com on 2017/4/10.
 */
public class OrderProceedViewFragement extends BaseFrament implements IQueryOrder {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout layout_swipe_refresh;
    private OrderListAdapter adapter;
    private QueryOrderPresenter presenter;
    private LinearLayoutManager llm;
    private String ordername;
    private ProceedOrderHolder proceedOrderHolder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }


    @Override
    protected int setLayoutResouceId() {
        return R.layout.order_proceed_view;
    }

    @Override
    protected void initDate(Bundle mbundle) {
        presenter = new QueryOrderPresenter(mContext, this);
        adapter = new OrderListAdapter(mContext, null);
        mRecyclerView.setAdapter(adapter);
        presenter.queryProceedOrder();
        //        proceedOrderHolder =new ProceedOrderHolder(mrootView);
        //        proceedOrderHolder.setGetOrderNumber(this);

    }

    @Override
    protected void initView() {
        llm = new LinearLayoutManager(getContext());
        mRecyclerView = (RecyclerView) mrootView.findViewById(R.id.proceed_recycler_view);
        layout_swipe_refresh = (SwipeRefreshLayout) mrootView.findViewById(R.id.layout_swipe_refresh);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //            mRecyclerView.setBackgroundResource(R.drawable.bitmap_hot_1);
        layout_swipe_refresh.setProgressViewOffset(true, 0, 200);
        layout_swipe_refresh.setDistanceToTriggerSync(20);
        layout_swipe_refresh.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        layout_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.queryProceedOrder();

            }
        });
    }

    @Override
    protected void onsetListener() {
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, View itemView, int position) {
                //跳转到详情页面
                Intent intent = new Intent();
                intent.putExtra("ordername", adapter.getData().get(position).name);
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
                presenter.queryProceedOrderMore();
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

    @Subscribe
    public void onEvent(OrderRrefeshEvent orderRrefeshEvent) {
        if (orderRrefeshEvent.isSuccess()) {
            presenter.queryProceedOrder();
            Log.d("======", "执行了刷新");
        }
    }


    @Override
    public void queryOrderSuccess(List<QueryOrderEntity> list) {
        if (presenter.getOffset() == 0) {
            adapter.getData().clear();
        }
        adapter.getData().addAll(list);
        adapter.notifyDataSetChanged();

        //        Animator spruceAnimator = new Spruce
        //                .SpruceBuilder(mRecyclerView)
        //                .sortWith(new LinearSort(/*interObjectDelay=*/100L, /*reversed=*/false, LinearSort.Direction.TOP_TO_BOTTOM))
        //                .animateWith(new Animator[] {DefaultAnimations.shrinkAnimator(mRecyclerView, /*duration=*/800)})
        //                .start();
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

    }
}


