package com.sanxiongdi.stopcar.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sanxiongdi.StopContext;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseRecyclerViewHolder;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;

import java.util.List;

/**
 * 设置item  界面需要渲染的字段
 * <p>
 * Created by wuaomall@gmail.com on 2017/4/15.
 */

public class CancelOrderHolder extends BaseRecyclerViewHolder<QueryOrderEntity> {
    public TextView user_info_name;      //订单创建人
    public TextView user_info_order_time;// 订单创建时间
    public TextView order_info_proceed;  //是否在进行中
    public RelativeLayout inc_order_number;    //订单编号
    public RelativeLayout inc_order_time;      //订单创建时间
    public RelativeLayout inc_order_money;     //金额

    private TextView tvNumName,tvdata;
    private TextView tvNumValue,tvdataname;

    private QueryOrderEntity bean;
    public  GetOrderNumber getOrderNumber=null;
    public CancelOrderHolder(View itemView) {
        super(itemView);
        init(itemView);
    }

    /**
     * 初始化
     *
     * @param view
     */
    protected void init(View view) {
        this.user_info_name = (TextView) view.findViewById(R.id.user_info_name);
        this.user_info_order_time = (TextView) view.findViewById(R.id.user_info_order_time);
        this.order_info_proceed = (TextView) view.findViewById(R.id.order_info_proceed);
        this.inc_order_number = (RelativeLayout) view.findViewById(R.id.inc_order_number);
        this.inc_order_time = (RelativeLayout) view.findViewById(R.id.inc_order_time);
        this.inc_order_money = (RelativeLayout) view.findViewById(R.id.inc_order_money);
        tvNumValue = (TextView) inc_order_number.getChildAt(1);
        tvNumName = (TextView) inc_order_number.getChildAt(0);
        tvdata=(TextView) inc_order_time.getChildAt(1);
        tvdataname=(TextView) inc_order_time.getChildAt(0);

        inc_order_time.setVisibility(View.VISIBLE);
        inc_order_money.setVisibility(View.INVISIBLE);

    }


    @Override
    public void onBindViewHolder(final int position, List<QueryOrderEntity> mData) {
        bean = mData.get(position);
        user_info_name.setText(StopContext.getInstance().getUserInfo().name);
        tvdataname.setText("车辆状态");
        if ("false".equals(bean.car_order_start_date)){
            user_info_order_time.setText("2017-09-12 12:21:20");
            user_info_order_time.setVisibility(View.INVISIBLE);
        }else {
            user_info_order_time.setText(bean.car_order_start_date);
        }

        tvNumValue.setText(bean.name);
        switch (bean.car_order_state){
            case "0":
                order_info_proceed.setText("完成状态");
                break;
            case "1":
                order_info_proceed.setText("未完成");
                break;
            case "2":
                order_info_proceed.setText("取消");
                break;
            case "3":
                order_info_proceed.setText("转移");
                break;
        }
        switch (bean.car_order_stop_state){
            case "0":
                tvdata.setText("停放");
                break;
            case "1":
                tvdata.setText("离开");
                break;
            case "2":
                tvdata.setText("进场");
                break;
        }



    }



    public  interface  GetOrderNumber{

        void GetOrderNumber(String name);

    }


    public void setGetOrderNumber(GetOrderNumber getOrderNumber) {
        this.getOrderNumber = getOrderNumber;
    }
}
