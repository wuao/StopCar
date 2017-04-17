package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.uitls.RootLayout;

/**
 * 取消列表界面
 *
 * Created by wuaomall@gmail.com on 2017/4/10.
 */

public class OrderCancelViewFragement extends BaseFrament {

    private Context mContext;
    private View view;

    private RootLayout mrootLayout;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_cancel_view, container, false);
        return  view;
    }

    @Override
    protected void initDate(Bundle  mbundle) {

    }
    @Override
    protected void initView() {



    }
    @Override
    protected void onsetListener() {

    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.order_cancel_view;

    }

}
