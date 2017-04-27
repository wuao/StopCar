package com.sanxiongdi.stopcar.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.DisplayMetrics;

import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.IBle;
import com.brtbeacon.sdk.utils.L;
import com.sanxiongdi.stopcar.uitls.BaseSyatemHelperUitls;
import com.sanxiongdi.stopcar.uitls.LogUtils;
import com.sanxiongdi.stopcar.uitls.SharedPreferencesUtil;

/**
 * Application config
 * <p>
 * Created by wuaomall@gmail.com on 2017/3/27.
 */

public class BaseApplication extends Application {


    private static BaseApplication sBaseApplication;
    private static Context mContext;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private static String TAG = BaseApplication.class.getName();
    private BRTBeaconManager beaconManager;
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
        beaconManager.startService();
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

}
