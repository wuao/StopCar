package com.sanxiongdi.stopcar.base;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.IBle;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.brtbeacon.sdk.utils.L;
import com.mt.sdk.ble.MTBLEManager;
import com.mt.sdk.ble.base.MTSeriaBase;
import com.mt.sdk.ble.model.ErroCode;
import com.sanxiongdi.StopContext;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.activity.IndexActivity;
import com.sanxiongdi.stopcar.activity.order.OrderDetailsActivity;
import com.sanxiongdi.stopcar.entity.Balance;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.presenter.UserInfoSetingPresenter;
import com.sanxiongdi.stopcar.presenter.view.IUserInfoSeting;
import com.sanxiongdi.stopcar.uitls.AppConfigUitls;
import com.sanxiongdi.stopcar.uitls.BaseSyatemHelperUitls;
import com.sanxiongdi.stopcar.uitls.BluetoothClient;
import com.sanxiongdi.stopcar.uitls.GsonUtils;
import com.sanxiongdi.stopcar.uitls.LogUtils;
import com.sanxiongdi.stopcar.uitls.PhoneUtils;
import com.sanxiongdi.stopcar.uitls.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Application config
 * <p>
 * Created by wuaomall@gmail.com on 2017/3/27.
 */

public class BaseApplication extends Application implements IUserInfoSeting {


    public static BaseApplication sBaseApplication;
    public static Context mContext;
    public static String TAG = BaseApplication.class.getName();
    public static BRTBeaconManager beaconManager;
    public static Context context = null;
    public NotificationManager mNotificationManager;
    private UserInfoSetingPresenter userInfoSetingPresenter;
    private UserInfoEntity userInfoEntity;
    private BluetoothAdapter mBluetoothAdapter;
    public  MTBLEManager manager;
    public static Boolean  isConnBule=false;
    public static MTSeriaBase bleBase;

    /**
     * 被发现的设备
     */
    private List<BluetoothDevice> discoverDevices = new ArrayList<BluetoothDevice>();

    public BaseApplication() {
    }

    public BaseApplication(Context context) {
        context = this.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        userInfoSetingPresenter = new UserInfoSetingPresenter(this, this);
        userInfoSetingPresenter.querybyphoneuserinfo(PhoneUtils.getDeviceId(BaseApplication.mContext));
        setFristInstallApplication();
        if (suuorpsdk(mContext)) {
            //初始化第三方的sdk
            InstanceSDK();
            printAppParameter();
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            IntentFilter filter = new IntentFilter();
            //发现设备
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            //设备连接状态改变
            filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            //蓝牙设备状态改变
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            //第一次启动

        }
    }

    /**
     * @Description: 获取BaseApplication实例初始化
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/1 17:27
     */
    public static BaseApplication getInstance(Context context) {
        if (sBaseApplication == null) {
            sBaseApplication = new BaseApplication(context.getApplicationContext());
        }
        return sBaseApplication;
    }

    /**
     * @Description: 获取BaseApplication实例初始化
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/1 17:27
     */
    public static BaseApplication getInstance() {
        if (sBaseApplication == null) {
            sBaseApplication = new BaseApplication();
        }
        return sBaseApplication;
    }

    public static Context getContextObject() {
        return mContext;
    }

    public static boolean suuorpsdk(Context context) {
        boolean fa;
        //在创建的时候我们需要做一些必要的操作如 关于版本sdk的最小支持，和第三方的sdk 的初始化参数设置
        if (BaseSyatemHelperUitls.IsSupporrtMinSDK()) {
            fa = true;
        } else {
            BaseSyatemHelperUitls.ShowToast(context, "不支持该版本系统，请升级到安卓4.4版本以上");
            fa = false;
            BaseSyatemHelperUitls.exit();
        }
        return fa;
    }

    /**
     * @Description: 初始化第三方的sdk
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/2 9:34
     */
    public void InstanceSDK() {
        startBletooch();
        startManTou();
    }


    /**
     * @Description: 打印app的一些参数
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/2 9:35
     */
    public void printAppParameter() {
        LogUtils.d(TAG, "OS : " + Build.VERSION.RELEASE + " ( " + Build.VERSION.SDK_INT + " )");
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        LogUtils.d(TAG, "height Size: " + dm2.heightPixels + " width Size " + dm2.widthPixels);
    }


    /**
     * 开启智云BEAcon sdk
     */
    public void startBletooch() {
        // 开启log打印
        L.enableDebugLogging(true);
        //获取单例
        beaconManager = BRTBeaconManager.getInstance(this);
        // 注册应用 APPKEY申请:http://brtbeacon.com/main/index.shtml
        beaconManager.registerApp("e15a34ddca0440718d29f2bc21fe6c30");
        // 开启Beacon扫描服务
        beaconManager.setBRTBeaconManagerListener(beaconManagerListener);
        beaconManager.startService();
        beaconManager.startRanging();

    }
    public void startManTou(){
        manager=  MTBLEManager.getInstance(getApplicationContext());
        bleBase=new MTSeriaBase(mContext,manager);
        initBule();
    }




    /**
     * 创建Beacon连接需要传递此参数
     *
     * @return IBle
     */
    public IBle getIBle() {
        return beaconManager.getIBle();
    }

    /**
     * 获取Beacon管理对象
     *
     * @return BRTBeaconManager
     */
    public BRTBeaconManager getBRTBeaconManager() {
        if (beaconManager == null) {
            beaconManager = BRTBeaconManager.getInstance(this);
        }
        return beaconManager;
    }

    /**
     * 第一安装APP 打开 设置 偏好设置
     */
    public void setFristInstallApplication() {
        SharedPreferences sPreferences = getSharedPreferences("first", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("FIRST_INSTALL", "1");
        editor.commit();
    }

    public String getFristInstallApplication() {
        SharedPreferences sPreferences = getSharedPreferences("first", Context.MODE_PRIVATE);
        return sPreferences.getString("FIRST_INSTALL", "");
    }







    public BRTBeaconManagerListener beaconManagerListener = new BRTBeaconManagerListener() {
        @Override
        public void onUpdateBeacon(ArrayList<BRTBeacon> arrayList) {

            for (int i = 0; i < arrayList.size(); i++) {
                Log.d("===", "广播数据=====" + i + "------" + arrayList.get(i).getUuid());
                //                Log.i("aaaa","有消息==BRTBeacon"+AppConfigUitls.byteArrayToHexStr(arrayList.get(0).getUserData()));
                if (String.valueOf(AppConfigUitls.byteArrayToHexStr(arrayList.get(i).getUserData())).equals("88888888")) {
//                    showNotificaiton();
                    //                       showOutNotificaiton();
                }
                if (arrayList.get(i).getUuid().equals(BluetoothClient.uuid.toString())) {
                    Log.d("===","检测到控制盒开始链接");

                    if (!bleBase.isMTSeriaConnect()){
                        ErroCode result = bleBase.connect(arrayList.get(i).getMacAddress(), 2, false,callback);
                        Log.d("===","链接返回代码"+result.getCode());

                    }



                }
                if (String.valueOf(AppConfigUitls.byteArrayToHexStr(arrayList.get(i).getUserData())).equals("99999999")) {
                    showOutNotificaiton();
                }
            }
        }


        @Override
        public void onNewBeacon(BRTBeacon brtBeacon) {
            if (brtBeacon.getUuid().equals(BluetoothClient.uuid.toString())) {
                Toast.makeText(mContext, "找到0000000005CFFF373631484843217437的蓝牙", Toast.LENGTH_SHORT).show();
//                if (!bleBase.isMTSeriaConnect()){
//                    ErroCode result = bleBase.connect(brtBeacon.getMacAddress(), 4, false,callback);
//                    Log.d("===","链接返回代码"+result.getCode());
//
//                }
            }


        }

        @Override
        public void onGoneBeacon(BRTBeacon brtBeacon) {
            if (String.valueOf(AppConfigUitls.byteArrayToHexStr(brtBeacon.getUserData())).equals("88888888")) {
                //离开广播为88888888 的停车场附件
            }

        }

        @Override
        public void onError(BRTThrowable brtThrowable) {

        }
    };
    /**
     * 发送通知
     */
    public void showNotificaiton() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("您已进入自动停车场附近")//设置通知栏标题
                .setContentText("是否需要停车")
                .setTicker("新消息") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
        mBuilder.setContentIntent(getDefalutIntent(2));
        mNotificationManager.notify(2, mBuilder.build());
    }

    /**
     * 发送通知出场
     */
    public void showOutNotificaiton() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("您已进入停车场出口附近")//设置通知栏标题
                .setContentText("是否需要离开")
                .setTicker("新消息") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
        mBuilder.setContentIntent(getOutIntent(2));
        mNotificationManager.notify(2, mBuilder.build());
    }

    public PendingIntent getDefalutIntent(int flags) {

        Intent intent = new Intent(this, IndexActivity.class);
        intent.putExtra("input", "input");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, flags, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public PendingIntent getOutIntent(int flags) {

        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("out", "out");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, flags, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Override
    public void queryUserInfoFailure(boolean isRequest, int code, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void queryUserInfoSuccess(List<UserInfoEntity> list) {
        if (list.size() != 0) {
            StopContext.getInstance().setUserInfo(GsonUtils.gsonString(list.get(0)));
        }


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
    public void updataUserInfoSuccess(WrapperEntity list) {

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


    private void  initBule(){
//        bleBase=new BLEBase(mContext,manager);
        bleBase = new MTSeriaBase(mContext, manager);

    }

    // 连接回调
    private MTSeriaBase.CallBack callback = new MTSeriaBase.CallBack() {

        // 连接结果，成功或者失败
        @Override
        public void onConnect(ErroCode errocode) {
            Log.d("===", "链接成功或者失败"+ bleBase.isMTSeriaConnect() );

            if (bleBase.isMTSeriaConnect()){
                isConnBule=true;
//                Looper.prepare();
//                Toast.makeText(mContext, "控制盒链接成功", Toast.LENGTH_SHORT).show();
//                Looper.loop();
                Log.d("===", " isConnBule的值"+ isConnBule );

            }

        }

        // 尝试重试连接剩余次数
        @Override
        public void reTryConnect(int lasttimes) {
            isConnBule=false;
//            Looper.prepare();
//            Toast.makeText(mContext, "尝试重新链接的剩余次数--"+lasttimes, Toast.LENGTH_SHORT).show();
//            Looper.loop();
            Log.d("===", "尝试重新链接的剩余次数--"+lasttimes );

        }

        // 被动断开连接
        @Override
        public void onDisConnect(ErroCode errocode) {
            isConnBule=false;
//            Looper.prepare();
//            Toast.makeText(mContext,"被动断开链接---"+errocode.getMsg(), Toast.LENGTH_SHORT).show();
//            Looper.loop();
            Log.d("===", "被动断开链接---"+errocode.getMsg() );
        }

        // 主动调用断开接口，断开回调
        @Override
        public void onManualDisConnect(ErroCode errocode) {
            isConnBule=false;
//            Looper.prepare();
//            Toast.makeText(mContext,"主动调用断开接口，断开回调---", Toast.LENGTH_SHORT).show();
//            Looper.loop();
            Log.d("===", "主动调用断开接口，断开回调---" +errocode.getMsg());

        }

        // 接收到数据
        @Override
        public void onDatasRecive(BluetoothGattCharacteristic rxd_charact, byte[] datas) {
           try {
               if (!StringUtils.checkNull(new String(datas))){
                   Log.d("===","蓝牙回调数据----"+ new String(datas,"GBK"));
               }
           }catch (UnsupportedEncodingException e){
               Log.d("===", "获取字节流失败异常--"+e.getMessage());
           }

        }

    };




}
