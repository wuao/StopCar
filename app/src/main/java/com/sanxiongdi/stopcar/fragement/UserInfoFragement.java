package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.activity.mine.CarInfoActivity;
import com.sanxiongdi.stopcar.activity.mine.UserMineSeting;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.uitls.RootLayout;
import com.sanxiongdi.stopcar.uitls.view.PupopWindowUitls;

/**
 * Created by wuaomall@gmail.com on 2017/4/7.
 */

public class UserInfoFragement extends BaseFrament implements View.OnClickListener {

    private Context mContext;
    private PupopWindowUitls pupopWindowUitls;
    private  View view,rootview;
    private Toolbar toolbar;
    private RootLayout mrootLayout;
    private RelativeLayout user_info_save;
    private LinearLayout customer_service,my_money,car_menage;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void  onClick(View v){

        if (v== user_info_save){

            startActivity(new Intent(mContext, UserMineSeting.class));
        }if (v ==car_menage){
            startActivity(new Intent(mContext, CarInfoActivity.class));

        }if (v ==my_money){
            startActivity(new Intent(mContext, CarInfoActivity.class));
        }if (v ==customer_service){
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:18677179827")));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.user_info_fragement, container, false);
        initView();
        onsetListener();
        return  view;
    }
    @Override
    protected void initDate(Bundle  mbundle) {
    }
    @Override
    protected void initView() {
        toolbar=(Toolbar)view.findViewById(R.id.tool_bar);
        user_info_save=(RelativeLayout)view.findViewById(R.id.user_info_save);
        customer_service =(LinearLayout) view.findViewById(R.id.customer_service);
        my_money =(LinearLayout) view.findViewById(R.id.my_money);
        car_menage =(LinearLayout) view.findViewById(R.id.car_menage);

    }
    @Override
    protected void onsetListener() {
        user_info_save.setOnClickListener(this);
        customer_service.setOnClickListener(this);
        my_money.setOnClickListener(this);
        car_menage.setOnClickListener(this);
    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.user_info_fragement;

    }

    }
