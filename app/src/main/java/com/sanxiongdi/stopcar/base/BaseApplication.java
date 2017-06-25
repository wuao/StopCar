package com.sanxiongdi.stopcar.base;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.IBle;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.brtbeacon.sdk.utils.L;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.activity.IndexActivity;
import com.sanxiongdi.stopcar.uitls.AppConfigUitls;
import com.sanxiongdi.stopcar.uitls.BaseSyatemHelperUitls;
import com.sanxiongdi.stopcar.uitls.LogUtils;

import java.util.ArrayList;

/**
 * Application config
 * <p>
 * Created by wuaomall@gmail.com on 2017/3/27.
 */

public class BaseApplication extends Application {


    public static BaseApplication sBaseApplication;
    public static Context mContext;
    public static String TAG = BaseApplication.class.getName();
    public  static BRTBeaconManager beaconManager;
    public static Context context = null;
    public NotificationManager mNotificationManager;

    public BaseApplication() {
    }
    public BaseApplication(Context context) {
        context = this.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        setFristInstallApplication();
        if (suuorpsdk(mContext)) {
            //初始化第三方的sdk
            InstanceSDK();
            printAppParameter();
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
    public   void InstanceSDK() {
        startBletooch();
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
    public  void startBletooch(){
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

    /**
     * 创建Beacon连接需要传递此参数
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
        if (beaconManager==null){
            beaconManager= BRTBeaconManager.getInstance(this);
        }
        return beaconManager;
    }

    /**
     * 第一安装APP 打开 设置 偏好设置
     */
     public    void   setFristInstallApplication(){
         SharedPreferences sPreferences = getSharedPreferences("first", Context.MODE_PRIVATE);
         SharedPreferences.Editor    editor = sPreferences.edit();
         editor.putString("FIRST_INSTALL","1");
         editor.commit();
     }
     public  String getFristInstallApplication (){
         SharedPreferences sPreferences = getSharedPreferences("first", Context.MODE_PRIVATE);
         return  sPreferences.getString("FIRST_INSTALL","");
     }


    public BRTBeaconManagerListener beaconManagerListener = new BRTBeaconManagerListener() {
        @Override
        public void onUpdateBeacon(ArrayList<BRTBeacon> arrayList) {

         for(int i = 0;i<arrayList.size();i++){
//                Log.i("aaaa","有消息==BRTBeacon"+AppConfigUitls.byteArrayToHexStr(arrayList.get(0).getUserData()));
             if (String.valueOf(AppConfigUitls.byteArrayToHexStr(arrayList.get(0).getUserData())).equals("88888888")){
//                 showNotificaiton();
             }
            }
        }


        @Override
        public void onNewBeacon(BRTBeacon brtBeacon) {
//            Log.i("STOP","新   息==onNewBeacon"+AppConfigUitls.byteArrayToHexStr(brtBeacon.getUserData()));
//            if (String.valueOf(AppConfigUitls.byteArrayToHexStr(brtBeacon.getUserData())).equals("88888888")){
//                //进入广播为88888888 的停车场附件
//             new Handler().post(new Runnable() {
//                 @Override
//                 public void run() {
//                     showNotificaiton();
//                 }
//             });
//
//            }
        }

        @Override
        public void onGoneBeacon(BRTBeacon brtBeacon) {
            if (String.valueOf(AppConfigUitls.byteArrayToHexStr(brtBeacon.getUserData())).equals("88888888")){
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
    public  void  showNotificaiton(){
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("您已进入自动停车场附件")//设置通知栏标题
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

    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, flags, new Intent(this,IndexActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }






}
