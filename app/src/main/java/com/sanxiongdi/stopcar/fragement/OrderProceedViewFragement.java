package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.adapter.ProceedRecyclerAdapter;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.base.BaseRecyclerAdapter;
import com.sanxiongdi.stopcar.uitls.RootLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 进行中的列表界面
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class OrderProceedViewFragement extends BaseFrament {

    private Context mContext;
    private View view;
    private BaseRecyclerAdapter madapter;
    private List<String> mData;
    private RecyclerView mRecyclerView;
    private RootLayout mrootLayout;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.order_proceed_view, container, false);

        mData = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mData.add("" + (char) i);
        }
        mRecyclerView=(RecyclerView) view.findViewById(R.id.proceed_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new ProceedRecyclerAdapter(mData, getContext(),inflater));
        return  view;
    }


    @Override
    protected void initDate(Bundle  mbundle) {




    }
    protected void initView() {



    }
    @Override
    protected void onsetListener() {

    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.order_proceed_view;

    }




}
