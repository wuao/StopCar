package com.sanxiongdi.stopcar.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.Pingpp;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.entity.Balance;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.Wallet;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.presenter.UserInfoSetingPresenter;
import com.sanxiongdi.stopcar.presenter.WalletPresenter;
import com.sanxiongdi.stopcar.presenter.view.IUserInfoSeting;
import com.sanxiongdi.stopcar.presenter.view.Iwallet;
import com.sanxiongdi.stopcar.uitls.StringUtils;
import com.sanxiongdi.stopcar.view.PypPopView;

import java.util.List;

import static com.sanxiongdi.stopcar.base.BaseApplication.context;

/**
 * Created by wuaomall@gmail.com on 2017/6/1.
 */

public class PayMyWalletActivity extends BaseActivity implements View.OnClickListener ,IUserInfoSeting,Iwallet,PypPopView.OnBackGetBranch{

    private TextView my_yue,edit_uitl_save;
    private Button  btn;
    private PypPopView pypPopView;
    private ImageView imageView;
    private UserInfoSetingPresenter userInfoSetingPresenter;
    private WalletPresenter walletPresenter;
    private String branch;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_main_view);
        pypPopView=new PypPopView(this);
        userInfoSetingPresenter=new UserInfoSetingPresenter(this,this);
        walletPresenter=new WalletPresenter(this,this);
        userInfoSetingPresenter.getUserByIdBalance(1);
        pypPopView.setOnBackGetBranch(PayMyWalletActivity.this);
        findView();
        setListeners();
    }


    @Override
    protected void findView() {
        imageView=(ImageView)findViewById(R.id.my_wallet_tool_bar).findViewById(R.id.img_back);
        edit_uitl_save=(TextView) findViewById(R.id.my_wallet_tool_bar).findViewById(R.id.edit_uitl_save);
        btn=(Button)findViewById(R.id.my_wallet_btn);
        my_yue=(TextView)findViewById(R.id.my_yue);
        edit_uitl_save.setVisibility(View.GONE);

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


    @Override
    public void queryUserInfoFailure(boolean isRequest, int code, String msg) {

    }

    @Override
    public void queryUserInfoSuccess(List<UserInfoEntity> list) {

    }

    @Override
    public void queryByPhoneUserInfoSuccess(List<UserInfoEntity> list) {

    }

    @Override
    public void updataUserInfoSuccess(WrapperEntity list) {

    }

    @Override
    public void creatUserInfoSuccess(WrapperEntity list) {

    }

    @Override
    public void getUserByIdBalance(List<Balance> list) {

        if (list.size()!=0){
            my_yue.setText("￥"+list.get(0).balance.toString());
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                if (result.equals("success")){
                    //支付成功就发送数据到钱包表
                    Log.i("YYDD",branch);
                    Wallet wallet=new Wallet();
                    wallet.amount=branch;
                    wallet.state="1";
                    wallet.user_id="1";
                    walletPresenter.createWallet(wallet);

                }else if (result.equals("fail")){
                    Log.i("PAY",data.getExtras().getString("error_msg").toString());
                    Toast.makeText(this,"充值失败",Toast.LENGTH_SHORT).show();

                }else if (result.equals("cancel")){
                    Toast.makeText(this,"充值取消",Toast.LENGTH_SHORT).show();


                }else if (result.equals("invalid")){
                    Toast.makeText(this,"充值无效",Toast.LENGTH_SHORT).show();


                }



                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
//                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
//                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                showMsg(result, errorMsg, extraMsg);
            }
        }
    }


    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null !=msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null !=msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }


    @Override
    public void createWallet(WrapperEntity list) {

        if (!StringUtils.checkNull(list.result)){
            Toast.makeText(context,"充值成功",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void Walletfaile(boolean isRequest, int code, String msg) {
        if (code == -1) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void createTransaction(WrapperEntity list) {
        if (!StringUtils.checkNull(list.result)){
            Toast.makeText(context,"支付成功",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void getBranch(String branch) {
        this.branch=branch;
    }
}
