package com.sanxiongdi.stopcar.activity.order;

import android.app.Service;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.SynthesizerTool;
import com.baidu.tts.client.TtsMode;
import com.google.gson.Gson;
import com.mt.sdk.ble.model.BLEBaseAction;
import com.mt.sdk.ble.model.ErroCode;
import com.mt.sdk.ble.model.WriteCharactAction;
import com.sanxiongdi.StopContext;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.base.BaseApplication;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.entity.Wallet;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.presenter.ComputeAmountPresenter;
import com.sanxiongdi.stopcar.presenter.QueryOrderPresenter;
import com.sanxiongdi.stopcar.presenter.UpdataOrderPresenter;
import com.sanxiongdi.stopcar.presenter.WalletPresenter;
import com.sanxiongdi.stopcar.presenter.view.IComputeAmount;
import com.sanxiongdi.stopcar.presenter.view.IQueryOrder;
import com.sanxiongdi.stopcar.presenter.view.IUpdataOrder;
import com.sanxiongdi.stopcar.presenter.view.Iwallet;
import com.sanxiongdi.stopcar.uitls.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by wuaomall@gmail.com on 2017/6/21.
 */

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener, IQueryOrder, Iwallet, SpeechSynthesizerListener, IComputeAmount,IUpdataOrder {


    private LinearLayout lly_back, order_tool_bar;
    private TextView edit_uitl_save, order_new_time, detalis_order_one, detalis_order;
    private Button zhifu_icon;
    private View view;
    private String ordername;
    private QueryOrderPresenter orderPresenter;
    private ComputeAmountPresenter computeAmountPresenter;
    private TextView order_name, car_order_start_date, car_order_state, car_order_stop_state, car_order_authorize_id;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private ShakeListener shakeListener;
    private SweetAlertDialog pDialog;
    private WalletPresenter walletPresenter;
    private String mSampleDirPath;
    private String computeAmout;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static final int PRINT = 0;
    private static final int UI_CHANGE_INPUT_TEXT_SELECTION = 1;
    private static final int UI_CHANGE_SYNTHES_TEXT_SELECTION = 2;
    private static final String TAG = "MainActivity";
    private   QueryOrderEntity queryOrderbena;

    // 语音合成客户端
    private SpeechSynthesizer mSpeechSynthesizer;
    private UpdataOrderPresenter updataOrderPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetails);
        initialEnv();
        startTTS();
        orderPresenter = new QueryOrderPresenter(this, this);
        walletPresenter=new WalletPresenter(this,this);
        computeAmountPresenter = new ComputeAmountPresenter(this, this);
        updataOrderPresenter=new UpdataOrderPresenter(this, this);
        if (!StringUtils.checkNull(getIntent().getStringExtra("ordername"))) {
            ordername = getIntent().getStringExtra("ordername");
            //请求数据详情
            orderPresenter.getOrderByNmae(ordername);

        }
        findView();
        setListeners();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        shakeListener = new ShakeListener();
        sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);
        isNotification(getIntent().getStringExtra("out"));

    }

    @Override
    protected void findView() {
        order_tool_bar = (LinearLayout) findViewById(R.id.order_tool_bar);
        edit_uitl_save = (TextView) findViewById(R.id.order_tool_bar).findViewById(R.id.edit_uitl_save);
        lly_back = (LinearLayout) findViewById(R.id.order_tool_bar).findViewById(R.id.lly_back);

        order_name = (TextView) findViewById(R.id.order_name);
        order_new_time = (TextView) findViewById(R.id.order_new_time);
        zhifu_icon = (Button) findViewById(R.id.zhifu_icon);
        detalis_order_one = (TextView) findViewById(R.id.detalis_order_one);
        detalis_order = (TextView) findViewById(R.id.detalis_order);
        car_order_start_date = (TextView) findViewById(R.id.car_order_start_date);
        car_order_state = (TextView) findViewById(R.id.car_order_state);
        car_order_stop_state = (TextView) findViewById(R.id.car_order_stop_state);
        car_order_authorize_id = (TextView) findViewById(R.id.car_order_authorize_id);
        order_tool_bar.setBackgroundColor(Color.rgb(255, 113, 28));
        edit_uitl_save.setVisibility(View.GONE);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        order_new_time.setText(dateNowStr);
    }

    @Override
    protected void getInstance() {

    }

    @Override
    protected void setListeners() {
        lly_back.setOnClickListener(this);
        edit_uitl_save.setOnClickListener(this);
        zhifu_icon.setOnClickListener(this);
    }

    @Override
    protected void setUpDate(Bundle savedInstanceState) {

    }

    @Override
    protected void protectApp() {
        super.protectApp();
    }

    @Override
    protected void kickOut() {
        super.kickOut();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return super.onMenuItemClick(item);
    }


    @Override
    public void onClick(View v) {

        if (v == lly_back) {
            finish();
        } else if (v == zhifu_icon) {
            mSpeechSynthesizer.speak("落叶偏偏");
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            if (computeAmout != null) {
                pDialog.setTitleText("是否支付")
                        .setContentText("支付" + computeAmout + "元,摇一摇或者点击确认支付")
                        .setCancelText("取消")
                        .setConfirmText("确认")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(final SweetAlertDialog sDialog) {
                                //获取当前的余额是否有这么多 不然就跳转到充值页面 如果有就跳转到充值页面
                                String mengey = StopContext.getInstance().getBalance().balance;
                                if (!StringUtils.checkNull(mengey)) {
                                    if (Double.parseDouble(mengey) > Double.parseDouble(computeAmout)) {
                                        assembleData();
                                        sDialog.dismiss();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "余额不足 请充值", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "没有钱啦 请充值哦", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }).show();
            }
        }
    }

    @Override
    public void queryOrderSuccess(List<QueryOrderEntity> list) {

    }

    @Override
    public void queryOrderFailure(boolean isRequest, int code, String msg) {

    }

    @Override
    public void queryOrderDetailsFailure(boolean isRequest, int code, String msg) {

    }

    @Override
    public void queryOrderDetailsSuccess(List<QueryOrderEntity> list) {
        if (list.size() != 0) {
            queryOrderbena=list.get(0);

            computeAmountPresenter.computeAmount(list.get(0).id);
            order_name.setText(list.get(0).name);
            car_order_start_date.setText(list.get(0).car_order_start_date);

            if (list.get(0).car_order_authorize_id.equals("false") || list.get(0).car_order_authorize_id.equals("")) {
                car_order_authorize_id.setText("");
            } else {
                car_order_authorize_id.setText(list.get(0).car_order_authorize_id);
            }
            if (list.get(0).car_order_stop_state.equals("0")) {
                car_order_stop_state.setText("停放");
            } else if (list.get(0).car_order_stop_state.equals("1")) {
                car_order_stop_state.setText("离开");
            } else if (list.get(0).car_order_stop_state.equals("2")) {
                car_order_stop_state.setText("进场");
            } else if (list.get(0).car_order_stop_state.equals("false")) {
                car_order_stop_state.setText("");
            }
            if (list.get(0).car_order_state.equals("0")) {
                zhifu_icon.setVisibility(View.GONE);
                car_order_state.setText("完成");
                detalis_order_one.setText("温馨提示:订单已经完成");
                detalis_order.setText("已完成");
            } else if (list.get(0).car_order_state.equals("1")) {
                zhifu_icon.setVisibility(View.VISIBLE);
                car_order_state.setText("进行");
                detalis_order_one.setText("温馨提示:订单进行中");
                detalis_order.setText("进行中");

            } else if (list.get(0).car_order_state.equals("2")) {
                zhifu_icon.setVisibility(View.GONE);
                car_order_state.setText("取消");
                detalis_order_one.setText("温馨提示:订单已取消");
                detalis_order.setText("已取消");
            } else if (list.get(0).car_order_state.equals("3")) {
                zhifu_icon.setVisibility(View.GONE);
                car_order_state.setText("授权");
                detalis_order_one.setText("温馨提示:订单已授权");
                detalis_order.setText("已授权");
            } else if (list.get(0).car_order_state.equals("false")) {
                car_order_state.setText("");
            }
        }


    }


    //摇一摇监听器
    public class ShakeListener implements SensorEventListener {
        /**
         * 检测的时间间隔
         */
        static final int UPDATE_INTERVAL = 100;
        /**
         * 上一次检测的时间
         */
        long mLastUpdateTime;
        /**
         * 上一次检测时，加速度在x、y、z方向上的分量，用于和当前加速度比较求差。
         */
        float mLastX, mLastY, mLastZ;

        /**
         * 摇晃检测阈值，决定了对摇晃的敏感程度，越小越敏感。
         */
        public int shakeThreshold = 4000;

        @Override
        public void onSensorChanged(SensorEvent event) {
            long currentTime = System.currentTimeMillis();
            long diffTime = currentTime - mLastUpdateTime;
            if (diffTime < UPDATE_INTERVAL) {
                return;
            }
            mLastUpdateTime = currentTime;
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float deltaX = x - mLastX;
            float deltaY = y - mLastY;
            float deltaZ = z - mLastZ;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            float delta = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / diffTime * 10000);
            // 当加速度的差值大于指定的阈值，认为这是一个摇晃
            if (delta > shakeThreshold) {
                //发送数据
                pDialog.dismiss();
                startTTS();
                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                finish();
                vibrator.vibrate(200);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }


    @Override
    public void createWallet(WrapperEntity list) {

    }

    @Override
    public void Walletfaile(boolean isRequest, int code, String msg) {

    }

    @Override
    public void createTransaction(WrapperEntity list) {
        if (list!=null){
            Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
            //发送数据更改订单状态
            if (queryOrderbena!=null){
                updataOrderPresenter.updataOrder(queryOrderbena.id);
            }

            }


    }

    // 初始化语音合成客户端并启动
    private void startTTS() {
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        this.mSpeechSynthesizer.setContext(BaseApplication.mContext);
        // 设置语音合成状态监听器
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        this.mSpeechSynthesizer.setApiKey("e8UM0BOkIxOqGBqbAGGHRkBP", "I5A4vchDNjs22GCrzv0anmuMs0llUY4b");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        this.mSpeechSynthesizer.setAppId("9818545");
        //        // 设置语音合成文本模型文件
        //        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
        //                + TEXT_MODEL_NAME);
        //        // 声学模型文件路径 (离线引擎使用)
        //        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
        //                + SPEECH_FEMALE_MODEL_NAME);
        //        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        //        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
        //                + LICENSE_FILE_NAME);
        //        // 获取语音合成授权信息
        //        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0setContext");
        //        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);

    }

    public void onError(String arg0, SpeechError arg1) {
        // 监听到出错，在此添加相关操作
        Log.d("TAG", "监听到出错");

    }

    public void onSpeechFinish(String arg0) {
        // 监听到播放结束，在此添加相关操作
        Log.d("TAG", "播放结束");
    }

    public void onSpeechProgressChanged(String arg0, int arg1) {
        // 监听到播放进度有变化，在此添加相关操作
        Log.d("TAG", "播放进度有变化");

    }

    public void onSpeechStart(String arg0) {
        // 监听到合成并播放开始，在此添加相关操作
        Log.d("TAG", "合成并播放开始" + arg0);


    }

    public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
        // 监听到有合成数据到达，在此添加相关操作
        Log.d("TAG", "有合成数据到达");
    }

    public void onSynthesizeFinish(String arg0) {
        // 监听到合成结束，在此添加相关操作
        Log.d("TAG", "合成结束");


    }

    public void onSynthesizeStart(String arg0) {
        // 监听到合成开始，在此添加相关操作
        Log.d("TAG", "合成开始" + arg0);
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印引擎so库版本号及基本信息和model文件的基本信息
     */
    private void printEngineInfo() {
        toPrint("EngineVersioin=" + SynthesizerTool.getEngineVersion());
        toPrint("EngineInfo=" + SynthesizerTool.getEngineInfo());
        String textModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + TEXT_MODEL_NAME);
        toPrint("textModelInfo=" + textModelInfo);
        String speechModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        toPrint("speechModelInfo=" + speechModelInfo);
    }

    private void toPrint(String str) {
        //        Message msg = Message.obtain();
        //        msg.obj = str;
        //        this.mHandler.sendMessage(msg);

        Toast.makeText(BaseApplication.mContext, str, Toast.LENGTH_SHORT).show();

    }


    /**
     * 从通知栏点击进来的
     *
     * @param data
     */
    private void isNotification(String data) {
        if (data != null && data.equals("out")) {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            if (computeAmout != null) {

                pDialog.setTitleText("是否支付")
                        .setContentText("支付" + computeAmout + "元,摇一摇或者点击确认支付")
                        .setCancelText("取消")
                        .setConfirmText("确认")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(final SweetAlertDialog sDialog) {
                                //获取当前的余额是否有这么多 不然就跳转到充值页面 如果有就跳转到充值页面
                                String mengey = StopContext.getInstance().getBalance().toString();
                                if (Double.parseDouble(mengey) > Double.parseDouble(computeAmout)) {
                                    assembleData();
                                    sDialog.dismiss();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "余额不足 请充值", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();

            }
        }


    }

    /**
     * 发送消息到客户端
     *
     * @param msg
     */
    private void send(String msg) {
        try {
            BaseApplication.bleBase.addWriteDatasAction(new WriteCharactAction(null, msg.getBytes("GBK"), new BLEBaseAction.Option(1000)) {
                @Override
                public void onSuccess() {
                    Log.d("===", "发送数据成功");
                    super.onSuccess();
                }

                @Override
                public void onFail(ErroCode erro) {
                    Log.d("===", "发送数据失败");
                    super.onFail(erro);
                }

            });

        } catch (UnsupportedEncodingException e) {
            Log.d("===", "获取字节流失败异常--" + e.getMessage());
        }

    }

    @Override
    public void computeAmount(WrapperEntity list) {

        if (list != null) {
            computeAmout = list.result.toString();
            Log.d("===", computeAmout + "======computeAmout");
        }


    }

    @Override
    public void computeAmountFaile(boolean isRequest, int code, String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    private void assembleData() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("ZJKZ", list);
        map2.put("YYBB", "支付" + computeAmout + "元");
        map2.put("CN", "123");
        if (BaseApplication.isConnBule) {
            send(new Gson().toJson(map2));
        }
        //扣款
        Wallet wallet = new Wallet();
        wallet.user_id = StopContext.getInstance().getUserInfo().id + "";
        wallet.amount = "-"+computeAmout;
        wallet.state = "1";
        walletPresenter.createTransaction(wallet);
        Log.d("====", "发送数据" + new Gson().toJson(map2));

    }


    @Override
    public void updataOrderSuccess(WrapperEntity list) {
        Log.d("====", "更新成功");
        //刷新界面
    }

    @Override
    public void updataOrderFailure(boolean isRequest, int code, String msg) {
        Log.d("====", "失败");
    }
}
