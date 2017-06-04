package com.sanxiongdi.stopcar.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.view.PypPopView;

/**
 * Created by wuaomall@gmail.com on 2017/6/1.
 */

public class PayMyWalletActivity extends BaseActivity implements View.OnClickListener {

    private TextView my_yue,edit_uitl_save;
    private Button  btn;
    private PypPopView pypPopView;
    private ImageView imageView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_main_view);
        pypPopView=new PypPopView(this);
        findView();


    }


    @Override
    protected void findView() {
        imageView=(ImageView)findViewById(R.id.my_wallet_tool_bar).findViewById(R.id.img_back);
        edit_uitl_save=(TextView) findViewById(R.id.my_wallet_tool_bar).findViewById(R.id.edit_uitl_save);
        btn=(Button)findViewById(R.id.my_wallet_btn);
        edit_uitl_save.setVisibility(View.GONE);
        setListeners();
    }

    @Override
    protected void getInstance() {

    }

    @Override
    protected void setListeners() {
        btn.setOnClickListener(this);
        imageView.setOnClickListener(this);

    }

    @Override
    protected void setUpDate(Bundle savedInstanceState) {

    }

    @Override
    protected void protectApp() {
        super.protectApp();
    }

    @Override
    protected void kickOut() {
        super.kickOut();
    }

    @Override
    public void onClick(View v) {

        if (v == btn){
            if (!pypPopView.isShowing()){
                pypPopView.show();
            }else{
                pypPopView=new PypPopView(this);
                pypPopView.show();
            }
        }else if (v ==imageView){
            finish();

        }

    }
}
