package com.sanxiongdi.stopcar.activity;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.entity.Balance;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.fragement.OrderFragement;
import com.sanxiongdi.stopcar.fragement.SearchFragement;
import com.sanxiongdi.stopcar.fragement.SetingFragement;
import com.sanxiongdi.stopcar.fragement.UserInfoFragement;
import com.sanxiongdi.stopcar.presenter.CreateAccountPresenter;
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
import com.sanxiongdi.stopcar.view.PupopWindowUitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;
import okhttp3.RequestBody;

/**
 * 首页
 * <p>
 * Created by wuaomall@gmail.com on 2017/4/6.
 */

public class IndexActivity extends BaseActivity implements View.OnClickListener, IGetRandomId,
        ICreateAccount, ICreateOrder, IUserInfoSeting {
    private PagerBottomTabLayout page_botton_tavlayout;
    private ArrayList<HashMap<String, Object>> pr;
    private SpeechSynthesizer speechSynthesizer;
    private PupopWindowUitls pupopWindowUitls;
    private AlertDialog dialog;
    private RequestBody body;
    private TextView textView1, textView2, textView3, textView4;
    private View pupopview;
    int[] testColors = {0xFF00796B, 0xFF8D6E63, 0xFF2196F3, 0xFF607D8B, 0xFFF57C00};
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE};

    Controller controller;
    List<Fragment> mFragments;
    //获取随机号
    private GetRandomIdPresenter presenter;
    //创建账号
    private CreateAccountPresenter createAccountPresenter;
    private CreateOrderPresenter orderPresenter;
    private ModifyOrderPresenter modifyOrderPresenter;
    private UserInfoSetingPresenter userInfoSetingPresenter;
    private List<String> randomIds;
    private UserInfoEntity userInfoEntity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.index_main);
        super.onCreate(savedInstanceState);
        findView();
        presenter = new GetRandomIdPresenter(this, this);
        createAccountPresenter = new CreateAccountPresenter(this, this);
        orderPresenter = new CreateOrderPresenter(this, this);
        modifyOrderPresenter = new ModifyOrderPresenter(this, this);
        userInfoSetingPresenter = new UserInfoSetingPresenter(this, this);
        presenter.getRandomId();
        setvesion();

    }


    @Override
    protected void findView() {
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

        BRTBeaconManagerListener beaconManagerListener =new BRTBeaconManagerListener() {
            @Override
            public void onUpdateBeacon(ArrayList<BRTBeacon> arrayList) {

            }

            @Override
            public void onNewBeacon(BRTBeacon brtBeacon) {

                if (brtBeacon.getMacAddress().equals("000000000001")){
                    // 进入 MacAddress 为"000000000001 的Beacon
                    //弹出框提示 如果有通知就不在提醒



                }
            }

            @Override
            public void onGoneBeacon(BRTBeacon brtBeacon) {
                if (brtBeacon.getApSsid().equals("000000000001")){
                    // 离开 MacAddress 为"000000000001 的Beacon
                }
            }

            @Override
            public void onError(BRTThrowable brtThrowable) {

            }
        };
        BRTBeaconManager.getInstance(this).setBRTBeaconManagerListener(beaconManagerListener);
    }

    @Override
    protected void getInstance() {
        if (pupopview != null) {
            textView1 = (TextView) pupopview.findViewById(R.id.suijishu_number_one);
            textView2 = (TextView) pupopview.findViewById(R.id.suijishu_number_two);
            textView3 = (TextView) pupopview.findViewById(R.id.suijishu_number_there);
            textView4 = (TextView) pupopview.findViewById(R.id.suijishu_number_frou);
            setListeners();
        }

    }


    @Override
    public void onClick(View v) {
        if (v == textView1) {
            createAccountPresenter.getRandomId(textView1.getText().toString(), randomIds.get(0));
            //            Toast.makeText(getApplicationContext(), textView1.getText(), Toast.LENGTH_LONG).show();
            //            pupopWindowUitls.dismiss();

        } else if (v == textView2) {
            orderPresenter.createOrder(randomIds.get(0));
            //            Toast.makeText(getApplicationContext(), textView2.getText(), Toast.LENGTH_LONG).show();
            //            pupopWindowUitls.dismiss();

        } else if (v == textView3) {
            modifyOrderPresenter.createOrder();
            //            Toast.makeText(getApplicationContext(), textView3.getText(), Toast.LENGTH_LONG).show();
            //            pupopWindowUitls.dismiss();

        } else if (v == textView4) {
            Toast.makeText(getApplicationContext(), textView4.getText(), Toast.LENGTH_LONG).show();
            pupopWindowUitls.dismiss();
        }
    }

    @Override
    protected void setListeners() {
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
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
                    transaction.commit();
                    break;
                case 1:
                    transaction.replace(R.id.frameLayout, new SearchFragement());
                    transaction.commit();
                    break;
                case 2:
                    transaction.replace(R.id.frameLayout, new UserInfoFragement());
                    transaction.commit();
                    break;
                case 3:
                    transaction.replace(R.id.frameLayout, new SetingFragement());
                    transaction.commit();
                    break;
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd", "onRepeatClick:" + index + "   TAG: " + tag.toString());
        }
    };

    public void showPupopwindow() {
        pupopWindowUitls = new PupopWindowUitls(this);
        pupopview = pupopWindowUitls.resoultPopupWindowView(R.layout.pupopwindow_uitls_view);
    }



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
            //将其转换成json 字符串 保存到偏好设置 使用 可转成对象在使用
            SharedPreferenceUtils.setStringDataIntoSP("UserInfo", "UserInfo", GsonUtils.gsonString(list.get(0)));
        }

    }

    @Override
    public void updataUserInfoSuccess(WrapperEntity list) {
    }


    @Override
    public void queryByPhoneUserInfoSuccess(List<UserInfoEntity> list) {
        //查询用户信息成功返回
        if (list.size() != 0) {
            //保存用户信息到偏好设置
            SharedPreferenceUtils.setStringDataIntoSP("UserInfo", "UserInfo", GsonUtils.gsonString(list.get(0)));
        } else if (list.size() == 0) {
            userInfoEntity = new UserInfoEntity();
            userInfoEntity.car_user_phone_id = PhoneUtils.getDeviceId(this);
            //证明没有查询到用户信息 需要传入设备号进行创建用户
            userInfoSetingPresenter.queryCreatePhoneUserInfo(userInfoEntity);
        }
    }


    @Override
    public void creatUserInfoSuccess(WrapperEntity list) {
        if (!StringUtils.checkNull(list.result)) {
            //如果创建成功就 根据返回的id 在去查询用户信息 将其保存到偏好设置
            userInfoSetingPresenter.queryuserinfo(Integer.valueOf(list.result.toString()));
        }

    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    setuserinfo();
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    @Override
    public void getUserByIdBalance(List<Balance> list) {

    }

    public void setvesion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }else {
//                setuserinfo();
            }
        }
    }


    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle("获取设备不可用")
                .setMessage("由于需要获取当前设备id,否则无法使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("获取设备id权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用获取设备id来进行设置")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }


    private  void setuserinfo(){
        String deviceId = PhoneUtils.getDeviceId(this).toString();
        Log.i("AP", "" + deviceId);
        if (!StringUtils.checkNull(deviceId)) {
            userInfoSetingPresenter.querybyphoneuserinfo(Integer.valueOf(deviceId));

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode==999){

        }


    }






}
