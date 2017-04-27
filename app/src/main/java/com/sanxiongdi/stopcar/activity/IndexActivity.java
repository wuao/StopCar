package com.sanxiongdi.stopcar.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.fragement.OrderFragement;
import com.sanxiongdi.stopcar.fragement.SearchFragement;
import com.sanxiongdi.stopcar.fragement.SetingFragement;
import com.sanxiongdi.stopcar.fragement.UserInfoFragement;
import com.sanxiongdi.stopcar.uitls.view.PupopWindowUitls;

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

public class IndexActivity extends BaseActivity  implements View.OnClickListener{
    private PagerBottomTabLayout page_botton_tavlayout;
    private SpeechSynthesizer speechSynthesizer;
    private PupopWindowUitls pupopWindowUitls;
    private TextView textView1 ,textView2,textView3,textView4;
    private View pupopview;
    private  Context context;
    int[] testColors = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    Controller controller;
    LayoutInflater inflater;
    List<Fragment> mFragments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.index_main);
        super.onCreate(savedInstanceState);
        findView();
        startPupopwindow();
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
        if (pupopview!=null) {
            textView1 = (TextView) pupopview.findViewById(R.id.suijishu_number_one);
            textView2 = (TextView) pupopview.findViewById(R.id.suijishu_number_two);
            textView3 = (TextView) pupopview.findViewById(R.id.suijishu_number_there);
            textView4 = (TextView) pupopview.findViewById(R.id.suijishu_number_frou);
            setListeners();
        }

    }


    @Override
    public void onClick(View v) {
         if (v ==textView1){
         Toast.makeText(getApplicationContext(),textView1.getText(),Toast.LENGTH_LONG).show() ;
             pupopWindowUitls.dismiss();

         }else if(v == textView2){
             Toast.makeText(getApplicationContext(),textView2.getText(),Toast.LENGTH_LONG).show() ;
             pupopWindowUitls.dismiss();

         }else if(v == textView3){
             Toast.makeText(getApplicationContext(),textView3.getText(),Toast.LENGTH_LONG).show() ;
             pupopWindowUitls.dismiss();

         }else if(v == textView4){
             Toast.makeText(getApplicationContext(),textView4.getText(),Toast.LENGTH_LONG).show() ;
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

     public  void  showPupopwindow(){
         pupopWindowUitls=new PupopWindowUitls(this);
         pupopview=pupopWindowUitls.resoultPopupWindowView(R.layout.pupopwindow_uitls_view);
     }

    /**
     * 启动Pupopwindow
     */
    public  void  startPupopwindow(){
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 if ("1".equals(getApplicationContext().getSharedPreferences("first", Context.MODE_PRIVATE).getString("FIRST_INSTALL",""))){
                     showPupopwindow();
                     pupopWindowUitls.initShareView(pupopview);
                     getInstance();
                     setListeners();
                 }
             }
         }, 1000);
     }





     public  void  star(){

//         // 获取 tts 实例
//         speechSynthesizer = SpeechSynthesizer.getInstance();
//         // 设置 app 上下文(必需参数)
//         speechSynthesizer.setContext(Context);
//         // 设置 tts 监听器
//         speechSynthesizer.setSpeechSynthesizerListener(SpeechSynthesizerListener);
//         // 文本模型文件路径，文件的绝对路径 (离线引擎使用)
//         speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_MODEL_FILE_FULL_PATH_NAME);
//         // 声学模型文件路径，文件的绝对路径 (离线引擎使用) speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
//         SPEECH_MODEL_FILE_FULL_PATH_NAME);
//         // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径， LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行 设置，如果在[应用管理]中开通了离线授权，不需要设置该参数，建议将该行代码删除(离线引擎)
//         speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, LICENSE_FILE_FULL_PATH_NAME);
//         // 请替换为语音开发者平台上注册应用得到的 App ID (离线授权) speechSynthesizer.setAppId("your_app_id");
//         // 请替换为语音开发者平台注册应用得到的 apikey 和 secretkey (在线授权) speechSynthesizer.setApiKey("your_api_key", "your_secret_key");
//         // 授权检测接口
//         AuthInfo authInfo = speechSynthesizer.auth(TtsMode);
//         // 引擎初始化接口
//         speechSynthesizer.initTts(TtsMode);
     }

}
