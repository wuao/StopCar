package com.sanxiongdi.stopcar.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.google.gson.Gson;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.fragement.OrderFragement;
import com.sanxiongdi.stopcar.fragement.SearchFragement;
import com.sanxiongdi.stopcar.fragement.SetingFragement;
import com.sanxiongdi.stopcar.fragement.UserInfoFragement;
import com.sanxiongdi.stopcar.presenter.CreateAccountPresenter;
import com.sanxiongdi.stopcar.presenter.CreateOrderPresenter;
import com.sanxiongdi.stopcar.presenter.GetRandomIdPresenter;
import com.sanxiongdi.stopcar.presenter.view.ICreateAccount;
import com.sanxiongdi.stopcar.presenter.view.ICreateOrder;
import com.sanxiongdi.stopcar.presenter.view.IGetRandomId;
import com.sanxiongdi.stopcar.uitls.view.PupopWindowUitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 首页
 * <p>
 * Created by wuaomall@gmail.com on 2017/4/6.
 */

public class IndexActivity extends BaseActivity implements View.OnClickListener, IGetRandomId,
        ICreateAccount,ICreateOrder {
    private PagerBottomTabLayout page_botton_tavlayout;
    private ArrayList<HashMap<String, Object>> pr;
    private SpeechSynthesizer speechSynthesizer;
    private PupopWindowUitls pupopWindowUitls;
    private RequestBody body;
    private TextView textView1, textView2, textView3, textView4;
    private View pupopview;
    int[] testColors = {0xFF00796B, 0xFF8D6E63, 0xFF2196F3, 0xFF607D8B, 0xFFF57C00};
    Controller controller;
    List<Fragment> mFragments;
    //获取随机号
    private GetRandomIdPresenter presenter;
    //创建账号
    private CreateAccountPresenter createAccountPresenter;
    private CreateOrderPresenter orderPresenter;
    private List<String> randomIds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.index_main);
        super.onCreate(savedInstanceState);
        findView();
        presenter = new GetRandomIdPresenter(this, this);
        createAccountPresenter = new CreateAccountPresenter(this, this);
        orderPresenter = new CreateOrderPresenter(this, this);
//        presenter.getRandomId();
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
            Toast.makeText(getApplicationContext(), textView3.getText(), Toast.LENGTH_LONG).show();
            pupopWindowUitls.dismiss();

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


    /**
     * 启动Pupopwindow
     */
    public void startPupopwindow(final List<String> list) {
        showPupopwindow();
        pupopWindowUitls.initShareView(pupopview);
        getInstance();
        textView1.setText("用户名1");
        textView2.setText("用户名2");
        textView3.setText("用户名3");
        textView4.setText("用户名4");
    }


    public RequestBody starcareat() {
        Gson gson = new Gson();
        pr = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        HashMap<String, Object> pr2 = new HashMap<String, Object>();
        pr2.put("name", "gouwa");
        pr2.put("login", "gouwa");
        paramsMap.put("login", "admin");
        paramsMap.put("passwrod", "admin");
        paramsMap.put("method", "res.users.create");
        paramsMap.put("args", pr2);
        pr.add(paramsMap);
        String strEntity = gson.toJson(pr);
        String sd = strEntity.substring(1, strEntity.length()).substring(0, strEntity.length() - 1);
        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "args=" + sd);
        return body;
    }

    //获取随机号
    @Override
    public void getRandomIdSuccess(List<String> list) {
        randomIds = list;
        startPupopwindow(list);
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

    }

    @Override
    public void createOrderSuccess() {

    }

    @Override
    public void createOrderFailure(boolean isRequest, int code, String msg) {

    }
}
