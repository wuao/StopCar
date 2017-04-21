package com.sanxiongdi.stopcar.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @desc :基础Fragment的基类
 * @author: wuaomall@gmail.com
 * Created at 2016/11/2  14:50
 */
public abstract   class BaseFrament   extends Fragment implements View.OnClickListener{
    /**
     * 1 初始化布局文件
     * 2 初始化数据
     * 3mIsPrepare 是否懒加载
     * 4 onLazyLoad 懒加载数据
     * 5 setListener()：各种监听事件的统一设置
     */
    protected BaseActivity  mActivity;
    private   View  mrootView;
    protected boolean mIsPrepare;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }


    /**
     * 获取宿主Activity
     */
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          mrootView=inflater.inflate(setLayoutResouceId(),container,false);
          mIsPrepare=true;
         initDate(getArguments());
         initView();
         onsetListener();
        return  mrootView;
    }





    protected  abstract void initDate(Bundle  mbundle);

    protected abstract void initView();

    protected abstract void onsetListener();

    protected   abstract  int setLayoutResouceId();


    /**
     * 关闭返回上一个fragement
     */
    public  static  void  finsh(Context context){


    }



}
