package com.sanxiongdi.stopcar.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseRecyclerViewHolder;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;

import java.util.List;

/**
 *
 * 设置item  界面需要渲染的字段
 *
 * Created by wuaomall@gmail.com on 2017/4/15.
 */

public class ProceedRecylerHolder  extends BaseRecyclerViewHolder<QueryOrderEntity> {
    public   TextView  user_info_name;      //订单创建人
    public  TextView  user_info_order_time;// 订单创建时间
    public  TextView  order_info_proceed;  //是否在进行中
    public  LinearLayout  inc_order_number;    //订单编号
    public  LinearLayout  inc_order_time;      //订单创建时间
    public  LinearLayout  inc_order_money;     //金额
    public Button  button1;  //取消
    public Button  button2;  //授权
    public Button  button3;  //查看
    private Context context;
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
        this.inc_order_number=(LinearLayout) view.findViewById(R.id.inc_order_number);
        this.inc_order_time=(LinearLayout) view.findViewById(R.id.inc_order_time);
        this.inc_order_money=(LinearLayout) view.findViewById(R.id.inc_order_money);
        button1=(Button)view.findViewById(R.id.cancle_btn_order);
        button2=(Button)view.findViewById(R.id.authorize_btn_order);
        button3=(Button)view.findViewById(R.id.see_btn_order);
   }


    @Override
    public void onBindViewHolder(final int position, List<QueryOrderEntity> mData) {
         button1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(mContext,"第"+position+"个按钮",Toast.LENGTH_SHORT).show();
             }
         });

         button2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(mContext,"第"+position+"个按钮",Toast.LENGTH_SHORT).show();

             }
         });


         button3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(mContext,"第"+position+"个按钮",Toast.LENGTH_SHORT).show();

             }
         });
    }
}
