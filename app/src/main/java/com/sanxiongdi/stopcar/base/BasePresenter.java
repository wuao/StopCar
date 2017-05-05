package com.sanxiongdi.stopcar.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin.woo on 2017/5/4.
 */

public class BasePresenter<T> {
    protected Context mContext;
    protected T view;
    public BasePresenter(Context context,T view){
        this.view = view;
        this.mContext = context;
    }
}
