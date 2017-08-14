package com.sanxiongdi.stopcar.uitls;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.sanxiongdi.stopcar.activity.IndexActivity;
import com.sanxiongdi.stopcar.base.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: CrashHandler
 * @Description: 系统默认的UncaughtException处理类的实现类 用于当程序异常的时候 由该类接管程序并且发送错误信息
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    PendingIntent restartIntent;
    //系统默认的UncaughtException处理异常 
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例  
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的Context对象  
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期作为日志文件名的 时间
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * @param @param context    设定文件
     * @return void    返回类型
     * @throws
     * @Title: init
     * @Description: 初始化方法
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理 
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理异常
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处处理 
            //mDefaultHandler.uncaughtException(thread, ex);  
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : " + e);
            }
        }
    }

    /**
     * @param @param  ex
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: handleException
     * @Description:自定义错误处 理，搜集错误信息发送和错误报告等操作都是在这里完成
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        String logFilePath = saveCrashInfo2File(ex);
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序发生异常！马上重启", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //写完日志信息在重启 虽然考虑到会照成ARN
                        Intent intent = new Intent(BaseApplication.mContext, IndexActivity.class);
                        PendingIntent restartIntent = PendingIntent.getActivity(BaseApplication.mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
                        //        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
                        AlarmManager mgr = (AlarmManager) BaseApplication.mContext.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10,
                                restartIntent); // 1秒钟后重启应用
                        Log.d(TAG, "执行了重启");
                        new ActivityContrl().finishProgram();
                    }
                }, 500);
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * @param @param ctx    设定文件
     * @return void    返回类型
     * @throws
     * @Title: collectDeviceInfo
     * @Description: 收集设备参数信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info" + e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info" + e);
            }
        }
    }

    /**
     * @param @param  ex
     * @param @return 返回文件名称,便于将文件传送到服务器
     * @return String    返回类型
     * @throws
     * @Title: saveCrashInfo2File
     * @Description: 保存错误信息到文件中
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "risoCrash-" + time + "-" + timestamp + ".txt";
            String path = "/sdcard/StopCar/ExceptionLog/";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }


            return path + fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file..." + e);
        }
        return null;
    }


    public UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.d(TAG, "进入了重启");
            AlarmManager mgr = (AlarmManager)BaseApplication.mContext.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    restartIntent); // 1秒钟后重启应用
            Log.d(TAG, "执行了重启");
            new ActivityContrl().finishProgram(); // 自定义方法，关闭当前打开的所有avtivity
        }
    };

    public class ActivityContrl {
        private List<Activity> activityList = new ArrayList<Activity>();

        public void remove(Activity activity) {
            activityList.remove(activity);
        }

        public void add(Activity activity) {
            activityList.add(activity);
        }

        public void finishProgram() {
            for (Activity activity : activityList) {
                if (null != activity) {
                    activity.finish();
                }
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}  