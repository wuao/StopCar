package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.uitls.RootLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuaomall@gmail.com on 2017/4/6.
 */

public class OrderFragement extends BaseFrament{

    private  Context mContext;
    private  View view ,view1,  view2, view3,view4;
    private  List<Fragment>  viewList;
    private  ViewPager viewPager;
    private  LayoutInflater inflater;
    private  List<String> tabTitleArray = new ArrayList<String>();
    private  RootLayout mrootLayout;
    private  TabLayout tableLayout;
    private Toolbar  toolbar;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_main, container, false);
        viewPager=(ViewPager) view.findViewById(R.id.viewpager);
        tableLayout=(TabLayout) view.findViewById(R.id.tablayout);
        toolbar=(Toolbar)view.findViewById(R.id.tool_bar);
        viewList = new ArrayList<Fragment>();
        initView();
        OrderViewPageFragement  fragmentPagerAdapter=new OrderViewPageFragement(getChildFragmentManager(),tabTitleArray,viewList);
        viewPager.setAdapter(fragmentPagerAdapter);
        tableLayout.setupWithViewPager(viewPager);
        return  view;
    }
    @Override
    protected void initDate(Bundle  mbundle) {
    }
    protected void initView() {
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        viewList.add(new OrderProceedViewFragement() );
        viewList.add(new OrderOverViewFragement());
        viewList.add(new OrderAuthorizeViewFragement());
        viewList.add(new OrderCancelViewFragement());
        tabTitleArray.add("进行");
        tabTitleArray.add("完成");
        tabTitleArray.add("授权");
        tabTitleArray.add("取消");
    }
    @Override
    protected void onsetListener() {

    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.order_main;

    }








}
