package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.uitls.DataCleanManagerUitls;
import com.sanxiongdi.stopcar.uitls.RootLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by wuaomall@gmail.com on 2017/4/7.
 */

public class SetingFragement extends BaseFrament {
    private Context mContext;
    private  View view;
    private Toolbar toolbar;
    private RootLayout mrootLayout;
    private TextView cash_size;
    private LinearLayout rly_clear_cache;
    private Button btn_etit_login;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seting_fragement, container, false);
        toolbar=(Toolbar)view.findViewById(R.id.tool_bar);
        mrootLayout=(RootLayout) view.findViewById(R.id.rootoobar);
        btn_etit_login=(Button)view.findViewById(R.id.btn_etit_login);
        cash_size=(TextView)view.findViewById(R.id.cash_size);
        try {
            cash_size.setText(DataCleanManagerUitls.getTotalCacheSize(mContext).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rly_clear_cache=(LinearLayout)view.findViewById(R.id.rly_clear_cache);
        rly_clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog =new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
                pDialog.setTitleText("是否清理?")
                        .setContentText("将删除缓存")
                        .setCancelText("取消")
                        .setConfirmText("确认")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(final SweetAlertDialog sDialog) {
                                DataCleanManagerUitls.clearAllCache(mContext);
                                // 更新界面
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(mContext,"清理完毕!",Toast.LENGTH_SHORT).show();
                                            cash_size.setText(DataCleanManagerUitls.getTotalCacheSize(mContext).toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, 1);
                                sDialog.cancel();

                            }
                        }).show();
            }
        });
        btn_etit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出登录
            }
        });
        return  view;
    }

    @Override
    public void  onClick(View view){


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

        return R.layout.seting_fragement;

    }




}
