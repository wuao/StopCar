package com.sanxiongdi.stopcar.activity.order;

import android.app.Service;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.presenter.QueryOrderPresenter;
import com.sanxiongdi.stopcar.presenter.WalletPresenter;
import com.sanxiongdi.stopcar.presenter.view.IQueryOrder;
import com.sanxiongdi.stopcar.presenter.view.Iwallet;
import com.sanxiongdi.stopcar.uitls.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by wuaomall@gmail.com on 2017/6/21.
 */

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener, IQueryOrder,Iwallet {


    private LinearLayout lly_back, order_tool_bar;
    private TextView edit_uitl_save,order_new_time;
    private Button zhifu_icon;
    private View view;
    private String ordername;
    private QueryOrderPresenter orderPresenter;
    private TextView order_name, car_order_start_date, car_order_state, car_order_stop_state, car_order_authorize_id;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private ShakeListener shakeListener;
    private  SweetAlertDialog pDialog;
    private  WalletPresenter walletPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetails);
        orderPresenter = new QueryOrderPresenter(this, this);
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
    }

    @Override
    protected void findView() {
        order_tool_bar = (LinearLayout) findViewById(R.id.order_tool_bar);
        edit_uitl_save = (TextView) findViewById(R.id.order_tool_bar).findViewById(R.id.edit_uitl_save);
        lly_back = (LinearLayout) findViewById(R.id.order_tool_bar).findViewById(R.id.lly_back);

        order_name = (TextView) findViewById(R.id.order_name);
        order_new_time= (TextView) findViewById(R.id.order_new_time);
        zhifu_icon = (Button) findViewById(R.id.zhifu_icon);
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
//            Toast.makeText(getApplicationContext(),"2222",Toast.LENGTH_SHORT).show();
              pDialog =new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("是否支付")
                    .setContentText("支付20元,点击确认")
                    .setCancelText("取消")
                    .setConfirmText("确认")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(final SweetAlertDialog sDialog) {
                            //获取当前的余额是否有这么多 不然就跳转到充值页面 如果有就跳转到充值页面
                            Toast.makeText(getApplicationContext(),"支付成功",Toast.LENGTH_SHORT).show();
//                            Wallet wallet=new Wallet();
//                            wallet.user_id= StopContext.getInstance().getUserInfo().id+"";
//                            wallet.amount="20";
//                            wallet.state="1";
//                            walletPresenter.createTransaction(wallet);
                            finish();
                            sDialog.cancel();
                        }
                    }).show();


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
                car_order_state.setText("完成");
                zhifu_icon.setVisibility(View.GONE);
            } else if (list.get(0).car_order_state.equals("1")) {
                zhifu_icon.setVisibility(View.VISIBLE);
                car_order_state.setText("进行");
            } else if (list.get(0).car_order_state.equals("2")) {
                zhifu_icon.setVisibility(View.GONE);
                car_order_state.setText("取消");
            } else if (list.get(0).car_order_state.equals("3")) {
                zhifu_icon.setVisibility(View.GONE);
                car_order_state.setText("授权");
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

                pDialog.cancel();
                Toast.makeText(getApplicationContext(),"支付成功",Toast.LENGTH_SHORT).show();
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

    }




}
