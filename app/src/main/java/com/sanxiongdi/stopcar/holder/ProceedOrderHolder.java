package com.sanxiongdi.stopcar.holder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseRecyclerViewHolder;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;

import java.util.List;

/**
 * 设置item  界面需要渲染的字段
 * <p>
 * Created by wuaomall@gmail.com on 2017/4/15.
 */

public class ProceedOrderHolder extends BaseRecyclerViewHolder<QueryOrderEntity> {
    public TextView user_info_name;      //订单创建人
    public TextView user_info_order_time;// 订单创建时间
    public TextView order_info_proceed;  //是否在进行中
    public LinearLayout inc_order_number;    //订单编号
    public LinearLayout inc_order_time;      //订单创建时间
    public LinearLayout inc_order_money;     //金额
    public Button button1;  //取消
    public Button button2;  //授权
    public Button button3;  //查看

    private TextView tvNumName;
    private TextView tvNumValue;

    private QueryOrderEntity bean;

    public ProceedOrderHolder(View itemView) {
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
        this.inc_order_number = (LinearLayout) view.findViewById(R.id.inc_order_number);
        this.inc_order_time = (LinearLayout) view.findViewById(R.id.inc_order_time);
        this.inc_order_money = (LinearLayout) view.findViewById(R.id.inc_order_money);
        button1 = (Button) view.findViewById(R.id.cancle_btn_order);
        button2 = (Button) view.findViewById(R.id.authorize_btn_order);
        button3 = (Button) view.findViewById(R.id.see_btn_order);
        tvNumValue = (TextView) inc_order_number.getChildAt(0);
        tvNumName = (TextView) inc_order_number.getChildAt(1);

        inc_order_time.setVisibility(View.INVISIBLE);
        inc_order_money.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onBindViewHolder(final int position, List<QueryOrderEntity> mData) {
        bean = mData.get(position);
        user_info_name.setText(bean.display_name);
        user_info_order_time.setText(bean.car_order_start_date);
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
    }
}
