package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.sanxiongdi.stopcar.activity.setting.HelpInfoActivity;
import com.sanxiongdi.stopcar.base.BaseApplication;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.uitls.DataCleanManagerUitls;
import com.sanxiongdi.stopcar.uitls.RootLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by wuaomall@gmail.com on 2017/4/7.
 */

public class SetingFragement extends BaseFrament  implements  View.OnClickListener{
    private Context mContext;
    private  View view;
    private Toolbar toolbar;
    private RootLayout mrootLayout;
    private TextView cash_size;
    private LinearLayout rly_clear_cache,pake_help,help_center,about_start;
    private Button btn_etit_login;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.seting_fragement, container, false);
        initView();
        onsetListener();
        return  view;
    }

    @Override
    public void  onClick(View view){
        if (view==rly_clear_cache){
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
        }else if (view==pake_help){
            startActivity(new Intent(mContext, HelpInfoActivity.class));


        }else if (view==btn_etit_login){
                //退出登录
            android.os.Process.killProcess(android.os.Process.myPid());
        }else  if (view==help_center){
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:18677179827")));
        }else if (view==about_start){



        }




    }


    @Override
    protected void initDate(Bundle  mbundle) {





    }
    @Override
    protected void initView() {
        toolbar=(Toolbar)view.findViewById(R.id.tool_bar);
        mrootLayout=(RootLayout) view.findViewById(R.id.rootoobar);
        btn_etit_login=(Button)view.findViewById(R.id.btn_etit_login);
        cash_size=(TextView)view.findViewById(R.id.cash_size);
        pake_help=(LinearLayout) view.findViewById(R.id.pake_help);
        help_center=(LinearLayout) view.findViewById(R.id.help_center);
        about_start=(LinearLayout) view.findViewById(R.id.about_start);
        try {
            cash_size.setText(DataCleanManagerUitls.getTotalCacheSize(BaseApplication.mContext).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rly_clear_cache=(LinearLayout)view.findViewById(R.id.rly_clear_cache);

    }
    @Override
    protected void onsetListener() {
        rly_clear_cache.setOnClickListener(this);
        pake_help.setOnClickListener(this);
        btn_etit_login.setOnClickListener(this);
        help_center.setOnClickListener(this);
        about_start.setOnClickListener(this);

    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.seting_fragement;

    }




}
