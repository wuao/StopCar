package com.sanxiongdi.stopcar.uitls;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @desc :  一些关于全局的帮助工具类
 * @author: wuaomall@gmail.com
 * Created at 2016/11/1  17:36
 */

public class BaseSyatemHelperUitls {

    private static boolean sIsAtLeastGB;
    public  static  int  APP_STATUS;

    /**
     * @Description: 是否支持最小的sdk版本支持
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/1 17:39
     */
    public static boolean IsSupporrtMinSDK() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }else{
            sIsAtLeastGB = false;
        }
        return sIsAtLeastGB;
    }

    /**
     * @Description:  传入toast
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/1 17:44
     */
     public  static  void  ShowToast(Context mcontext,CharSequence text){
         if (mcontext!=null){
             Toast.makeText(mcontext,text,Toast.LENGTH_SHORT).show();;
         }
     }

    /**
     * @Description:  退出App
     * @Author: wuaomall@gmail.com
     * creat at  2016/11/1 17:59
     */
       public  static   void  exit(){
           android.os.Process.killProcess(android.os.Process.myPid());
       }


        public  void setAppStatus(int status){
         this.APP_STATUS=status;
        }

       public  int getAppStatus(){
           return this.APP_STATUS;
       }


    /**
     * 设置状态栏 颜色
     */
    public  static  void  setColor(Activity activity, int color){

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

               // 设置状态栏透明
               activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
               // 生成一个状态栏大小的矩形
               View statusView = createStatusView(activity, color);
               // 添加 statusView 到布局中
               ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
               decorView.addView(statusView);
               // 设置根布局的参数
               ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
               rootView.setFitsSystemWindows(true);
               rootView.setClipToPadding(true);

         }



    }
    /** *
     *  生成一个和状态栏大小相同的矩形条
     *  @param activity 需要设置的activity
     *  @param color 状态栏颜色值
     *  @return 状态栏矩形条
     *  */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

}