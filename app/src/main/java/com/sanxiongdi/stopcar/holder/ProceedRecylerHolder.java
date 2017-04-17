package com.sanxiongdi.stopcar.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.sanxiongdi.stopcar.R;

/**
 *
 * 设置item  界面需要渲染的字段
 *
 * Created by wuaomall@gmail.com on 2017/4/15.
 */

public class ProceedRecylerHolder  extends RecyclerView.ViewHolder{


    public   TextView  user_info_name;      //订单创建人
    public  TextView  user_info_order_time;// 订单创建时间
    public  TextView  order_info_proceed;  //是否在进行中
    public  TextView  inc_order_number;    //订单编号
    public  TextView  inc_order_time;      //订单创建时间
    public  TextView  inc_order_money;     //金额
    public  ViewStub  btu_viewstub_uitls;  // 按钮

    public ProceedRecylerHolder(View itemView) {
        super(itemView);
        init(itemView);
    }

    /**
     *  初始化
     * @param view
     */
    protected  void   init(View  view){
        this.user_info_name=(TextView) view.findViewById(R.id.user_info_name);
        this.user_info_order_time=(TextView) view.findViewById(R.id.user_info_order_time);
        this.order_info_proceed=(TextView) view.findViewById(R.id.order_info_proceed);
        this.inc_order_number=(TextView) view.findViewById(R.id.inc_order_number);
        this.inc_order_time=(TextView) view.findViewById(R.id.inc_order_time);
        this.inc_order_money=(TextView) view.findViewById(R.id.inc_order_money);


   }


}
