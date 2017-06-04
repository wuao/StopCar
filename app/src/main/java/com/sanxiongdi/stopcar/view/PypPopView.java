package com.sanxiongdi.stopcar.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sanxiongdi.stopcar.R;

/**
 * Created by wuaomall@gmail.com on 2017/6/1.
 */

public class PypPopView extends PopupWindow implements View.OnClickListener {


    private Context context;

    private View view;
    private LinearLayout Wechat,zhifubao_pay;
    private ImageView img_close;
    private EditText my_edit_wallet;

    public PypPopView(Context context) {
        super(context);
        this.context=context;
        initview();
    }





    private void initview() {

        view = View.inflate(context, R.layout.top_up_pop, null);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.transparent));
        Wechat=(LinearLayout) view.findViewById(R.id.Wechat);
        zhifubao_pay=(LinearLayout) view.findViewById(R.id.zhifubao_pay);
        img_close=(ImageView)view.findViewById(R.id.img_close);
        my_edit_wallet=(EditText)view.findViewById(R.id.my_edit_wallet);
        initListener();


    }


    public void initListener() {
        Wechat.setOnClickListener(this);
        zhifubao_pay.setOnClickListener(this);
        img_close.setOnClickListener(this);
    }

    public void show() {
        this.showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    @Override
    public void onClick(View v) {


        if (v==Wechat){

            if (my_edit_wallet.getText().equals("")&&my_edit_wallet.getText()!=null){
               //调用微信充值界面
                //  new PaymentTask().execute(new PaymentRequest(CHANNEL_UPACP, amount));


            }

        }else  if (v == zhifubao_pay){
            if (my_edit_wallet.getText().equals("")&&my_edit_wallet.getText()!=null){
                //调用支付宝充值界面



            }


        }else if (v ==img_close ){

            dismiss();
        }

    }
}


