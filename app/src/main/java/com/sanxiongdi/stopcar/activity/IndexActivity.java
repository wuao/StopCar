package com.sanxiongdi.stopcar.activity;


import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sanxiongdi.StopContext;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.base.BaseApplication;
import com.sanxiongdi.stopcar.entity.Balance;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.fragement.OrderFragement;
import com.sanxiongdi.stopcar.fragement.SearchFragement;
import com.sanxiongdi.stopcar.fragement.SetingFragement;
import com.sanxiongdi.stopcar.fragement.UserInfoFragement;
import com.sanxiongdi.stopcar.presenter.CreateOrderPresenter;
import com.sanxiongdi.stopcar.presenter.GetRandomIdPresenter;
import com.sanxiongdi.stopcar.presenter.ModifyOrderPresenter;
import com.sanxiongdi.stopcar.presenter.UserInfoSetingPresenter;
import com.sanxiongdi.stopcar.presenter.view.ICreateAccount;
import com.sanxiongdi.stopcar.presenter.view.ICreateOrder;
import com.sanxiongdi.stopcar.presenter.view.IGetRandomId;
import com.sanxiongdi.stopcar.presenter.view.IUserInfoSeting;
import com.sanxiongdi.stopcar.uitls.GsonUtils;
import com.sanxiongdi.stopcar.uitls.PhoneUtils;
import com.sanxiongdi.stopcar.uitls.SharedPreferenceUtils;
import com.sanxiongdi.stopcar.uitls.StringUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/**
 * 首页
 * <p>
 * Created by wuaomall@gmail.com on 2017/4/6.
 */

public class IndexActivity extends BaseActivity implements View.OnClickListener, IGetRandomId,
        ICreateAccount, ICreateOrder, IUserInfoSeting {
    private PagerBottomTabLayout page_botton_tavlayout;
    int[] testColors = {0xFF00796B, 0xFF8D6E63, 0xFF2196F3, 0xFF607D8B, 0xFFF57C00};
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE};

    Controller controller;
    //获取随机号
    private GetRandomIdPresenter presenter;
    //创建账号
    private CreateOrderPresenter orderPresenter;
    private ModifyOrderPresenter modifyOrderPresenter;
    private UserInfoSetingPresenter userInfoSetingPresenter;
    private List<String> randomIds;
    private UserInfoEntity userInfoEntity;
    private NotificationManager mNotificationManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.index_main);
        super.onCreate(savedInstanceState);

        BaseApplication.getInstance().getBRTBeaconManager().startService();
        presenter = new GetRandomIdPresenter(this, this);
        orderPresenter = new CreateOrderPresenter(this, this);
        modifyOrderPresenter = new ModifyOrderPresenter(this, this);
        userInfoSetingPresenter = new UserInfoSetingPresenter(this, this);
        presenter.getRandomId();
        findView();
    }

    @Override
    protected void findView() {

        //         userInfoSetingPresenter.querybyphoneuserinfo(PhoneUtils.getDeviceId(BaseApplication.mContext));
        page_botton_tavlayout = (PagerBottomTabLayout) findViewById(R.id.tab_page);
        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(R.drawable.order)
                .setText("订单")
                .setSelectedColor(testColors[0])
                .build();
        //构建导航栏,得到Controller进行后续控制
        controller = page_botton_tavlayout.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(R.drawable.search, "搜索", testColors[1])
                .addTabItem(R.drawable.user, "用户", testColors[2])
                .addTabItem(R.drawable.seting, "设置", testColors[3])
                .setMode(TabLayoutMode.HIDE_TEXT | TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();
        controller.addTabItemClickListener(listener);

        isNotification(getIntent().getStringExtra("input"));
    }


    @Override
    protected void getInstance() {


    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setUpDate(Bundle savedInstanceState) {

    }


    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            Log.i("asd", "onSelected:" + index + "   TAG: " + tag.toString());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
            switch (index) {
                case 0:
                    transaction.replace(R.id.frameLayout, new OrderFragement());
                    transaction.commitAllowingStateLoss();
                    break;
                case 1:
                    transaction.replace(R.id.frameLayout, new SearchFragement());
                    transaction.commitAllowingStateLoss();
                    break;
                case 2:
                    transaction.replace(R.id.frameLayout, new UserInfoFragement());
                    transaction.commitAllowingStateLoss();
                    break;
                case 3:
                    transaction.replace(R.id.frameLayout, new SetingFragement());
                    transaction.commitAllowingStateLoss();
                    break;
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd", "onRepeatClick:" + index + "   TAG: " + tag.toString());
        }
    };

    //获取随机号
    @Override
    public void getRandomIdSuccess(List<String> list) {
        randomIds = list;
        // startPupopwindow(list);
    }

    @Override
    public void getRandomIdFailure(boolean isRequest, int code, String msg) {

    }

    //创建账号
    @Override
    public void createAccountSuccess() {

    }

    @Override
    public void createAccountFailure(boolean isRequest, int code, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createOrderSuccess() {

    }

    @Override
    public void createOrderFailure(boolean isRequest, int code, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void queryUserInfoFailure(boolean isRequest, int code, String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void queryUserInfoSuccess(List<UserInfoEntity> list) {
        //查询到用户信息成功
        if (list.size() != 0) {
            StopContext.getInstance().setUserInfo(GsonUtils.gsonString(list.get(0)));
            //将其转换成json 字符串 保存到偏好设置 使用 可转成对象在使用
            //            SharedPreferenceUtils.setStringDataIntoSP("UserInfo", "UserInfo", GsonUtils.gsonString(list.get(0)));
        }

    }


    @Override
    public void updataUserInfoSuccess(WrapperEntity list) {
    }


    @Override
    public void queryByPhoneUserInfoSuccess(List<UserInfoEntity> list) {
        //查询用户信息成功返回
        if (list.size() != 0) {
            //保用户信息到偏好设置
            StopContext.getInstance().setUserInfo(GsonUtils.gsonString(list.get(0)));
        } else if (list.size() == 0) {
            userInfoEntity = new UserInfoEntity();
            userInfoEntity.car_user_phone_id = PhoneUtils.getDeviceId(this);
            //证明没有查询到用户信息 需要传入设备号进行创建用户
            userInfoSetingPresenter.queryCreatePhoneUserInfo(userInfoEntity);
        }
    }

    @Override
    public void queryByPhoneUserInfoFailure(boolean isRequest, int code, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void creatUserInfoSuccess(WrapperEntity list) {
        if (!StringUtils.checkNull(list.result)) {
            //如果创建成功就 根据返回的id 在去查询用户信息 将其保存到偏好设置
            userInfoSetingPresenter.queryuserinfo(Integer.valueOf(list.result.toString()));
        }

    }

    @Override
    public void getUserByIdBalance(List<Balance> list) {

    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }


    private void isNotification(String data) {

        if (data != null && data.equals("input")) {
            new SharedPreferenceUtils().setIntDataIntoSP("NotificationManager", "NotificationManager", 0);
            //弹出对话框 创建订单
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("是否停车")
                    .setCancelText("取消")
                    .setConfirmText("确认")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(final SweetAlertDialog sDialog) {
                            // 创建订单 清楚当前adpter  获取当前数据  刷新界面
                            sDialog.cancel();
                        }
                    }).show();
        }
    }


}
