package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.adapter.AuthorizeRecyclerAdapter;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.base.BaseRecyclerAdapter;
import com.sanxiongdi.stopcar.uitls.RootLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 取消列表界面
 *
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class OrderCancelViewFragement extends BaseFrament {

    private Context mContext;
    private View view;
    private BaseRecyclerAdapter madapter;
    private List<String> mData;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout layout_swipe_refresh;
    private RootLayout rootLayout;
    private AuthorizeRecyclerAdapter authorizeRecyclerAdapter;
    private RootLayout mrootLayout;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.order_cancel_view, container, false);
        init_View(inflater);

        return  view;
    }

    @Override
    protected void initDate(Bundle  mbundle) {

    }

    @Override
    public void  onClick(View view){


    }

    @Override
    protected void initView() {
    }
    protected void init_View(LayoutInflater inflater) {
        mData = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mData.add("" + (char) i);
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cancle_recycler_view);
        layout_swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.layout_cancel_swipe_refresh);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        authorizeRecyclerAdapter = new AuthorizeRecyclerAdapter(mData, mContext, inflater);
        mRecyclerView.setAdapter(authorizeRecyclerAdapter);
        layout_swipe_refresh.setProgressViewOffset(true,0,200);
        layout_swipe_refresh.setDistanceToTriggerSync(20);
        layout_swipe_refresh.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        layout_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for (int i = 'A'; i < 'z'; i++) {
                    mData.add((char) i + "加载数据");
                }
                authorizeRecyclerAdapter.notifyDataSetChanged();
                layout_swipe_refresh.setRefreshing(false);
            }
        });
    }
    @Override
    protected void onsetListener() {

    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.order_cancel_view;

    }

}
