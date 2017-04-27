package com.sanxiongdi.stopcar.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sanxiongdi.stopcar.MainActivity;
import com.sanxiongdi.stopcar.activity.IndexActivity;


/**
 * @desc :  基础Activity 的基类
 * @author: wuaomall@gmail.com
 * Created at 2016/11/2  14:49
 */
public  abstract   class BaseActivity  extends AppCompatActivity  implements Toolbar.OnMenuItemClickListener{
    /**
     * 1  管理activity的生命周期
     * 2  定义activity 的栈
     * 3  对于初始化控件和添加监听和查找id
     * 4 对于app的一个保护。在登出 杀死，回收的时候的bug
     */

     private  int APP_STATUS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    //布局文件ID
//    protected abstract int getContentViewId();

//    //布局中Fragment的ID
//    protected abstract int getFragmentContentId();

    protected   abstract void  findView();//查找id

    protected   abstract void  getInstance();//初始化控件

    protected   abstract void  setListeners();// 添加监听

    protected   abstract void  setUpDate(Bundle savedInstanceState);// 设置数据


    protected   void   protectApp(){

        Intent intent = new Intent(this, IndexActivity.class);
        intent.putExtra(String.valueOf(APP_STATUS), 0);
        intent.putExtra("APP_STATUS",0);
        startActivity(intent);

    }
    protected   void   kickOut(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(String.valueOf(APP_STATUS), 1);
        intent.putExtra("APP_STATUS",0);
        startActivity(intent);
    }


    //菜单item的点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


}
