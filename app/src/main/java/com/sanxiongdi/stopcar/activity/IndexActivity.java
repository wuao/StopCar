package com.sanxiongdi.stopcar.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.fragement.OrderFragement;
import com.sanxiongdi.stopcar.fragement.SearchFragement;
import com.sanxiongdi.stopcar.fragement.SetingFragement;
import com.sanxiongdi.stopcar.fragement.UserInfoFragement;

import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/**
 * 首页
 *
 * Created by wuaomall@gmail.com on 2017/4/6.
 */

public class IndexActivity extends BaseActivity {
    private PagerBottomTabLayout page_botton_tavlayout;
    int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    Controller controller;
    LayoutInflater inflater;
    List<Fragment> mFragments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.index_main);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void findView() {
        page_botton_tavlayout =(PagerBottomTabLayout) findViewById(R.id.tab_page);
                //用TabItemBuilder构建一个导航按钮
                TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                        .setDefaultIcon(R.drawable.order)
                        .setText("订单")
                        .setSelectedColor(testColors[0])
                        .build();
                //构建导航栏,得到Controller进行后续控制
                controller = page_botton_tavlayout.builder()
                        .addTabItem(tabItemBuilder)
                        .addTabItem(R.drawable.search, "搜索",testColors[1])
                        .addTabItem(R.drawable.user, "用户",testColors[2])
                        .addTabItem(R.drawable.seting, "设置",testColors[3])
                        .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                        .build();

                controller.addTabItemClickListener(listener);
    }

    @Override
    protected void getInstance() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void setUpDate(Bundle savedInstanceState) {

    }

    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag)
        {
            Log.i("asd","onSelected:"+index+"   TAG: "+tag.toString());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
            switch (index){
                case 0:
                    transaction.replace(R.id.frameLayout, new OrderFragement());
                    transaction.commit();
                    break;
                case 1:
                    transaction.replace(R.id.frameLayout,new SearchFragement());
                    transaction.commit();
                    break;
                case 2:
                    transaction.replace(R.id.frameLayout,new UserInfoFragement());
                    transaction.commit();
                    break;
                case 3:
                    transaction.replace(R.id.frameLayout,new SetingFragement());
                    transaction.commit();
                    break;
            }
        }
        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
        }
    };


}
