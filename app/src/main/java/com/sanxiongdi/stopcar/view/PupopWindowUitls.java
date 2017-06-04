package com.sanxiongdi.stopcar.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.sanxiongdi.stopcar.R;

/**
 * Created by wuaomall@gmail.com on 2017/4/23.
 */

public class PupopWindowUitls extends PopupWindow {

    public     View  view;
    public Context context;



    public PupopWindowUitls(Context context) {
        super(context);
        this.context=context;

    }
    /**
     *  创建显示自定义的popupwindow view 视图
     * @param view
     */
    public    void initShareView(View view){
        this.setWidth(800);
        this.setHeight(900);
        this.setContentView(view);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setBackgroundDrawable(new ColorDrawable(0xFF2196F3));
//        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        this.setFocusable(true);
        this.showAtLocation(view, Gravity.CENTER, 0, 0);
        this.setAnimationStyle(R.style.anim_menu_bottombar);
    }

    /**
     *    返回自定义的popupwindow view
     * @param layoutview   界面ID
     * @return
     */
    public  View   resoultPopupWindowView(int  layoutview){
        view=LayoutInflater.from(context).inflate(layoutview, null);
        return view;
    }

}
