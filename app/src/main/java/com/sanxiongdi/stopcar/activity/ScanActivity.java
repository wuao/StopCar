package com.sanxiongdi.stopcar.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseApplication;
import com.sanxiongdi.stopcar.uitls.AppConfigUitls;
import com.sanxiongdi.stopcar.uitls.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by wuaomall@gmail.com on 2017/4/1.
 */

public class ScanActivity extends AppCompatActivity  implements OnItemClickListener,View.OnClickListener{


    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public final static String KEY_ACTION_TYPE = "key_action_type";
    //	Beacon参数配置
    public final static int ACTION_EDIT = 1;
    //	Beacon距离校准
    public final static int ACTION_ADJUST = 2;
    //	Beacon通知
    public final static int ACTION_NOTIFY = 3;
    String s;
    private ListView listView;

     HashSet<String> beaconList2= new HashSet<String>();
    private List<BRTBeacon> beaconList=new ArrayList<>();
    private ArrayAdapter<BRTBeacon> beaconAdapter = null;
    private BRTBeaconManager beaconManager = null;
    private int clickAction = 0;
    private NotificationManager mNotificationManager;
    private  SharedPreferenceUtils sharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dexc);

        clickAction = getIntent().getIntExtra(KEY_ACTION_TYPE, 0);
        listView = (ListView)findViewById(R.id.listView);
        sharedPreferenceUtils=new SharedPreferenceUtils();
        sharedPreferenceUtils.setIntDataIntoSP("NotificationManager","NotificationManager",0);
        beaconAdapter = new ArrayAdapter<BRTBeacon>(this, R.layout.item_device_info, android.R.id.text1, beaconList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tvRssi = (TextView)view.findViewById(R.id.device_rssi);
                TextView tvName = (TextView)view.findViewById(R.id.device_name);
                TextView tvAddr = (TextView)view.findViewById(R.id.device_address);
                TextView tvMajor = (TextView)view.findViewById(R.id.tv_major);
                TextView tvMinor = (TextView) view.findViewById(R.id.tv_minor);
                TextView tvUuid = (TextView) view.findViewById(R.id.tv_uuid);


                BRTBeacon beacon = getItem(position);
                 if (beacon.getName()!=null||beacon.getBattery()!=0){
                     if (String.valueOf(AppConfigUitls.byteArrayToHexStr(beacon.getUserData())).equals("88888888")){
                         showNotificaiton();
                     }
                         String c=new String(beacon.getUserData());
                         tvRssi.setText(String.valueOf(beacon.getRssi()));
                         tvName.setText(String.valueOf(beacon.getName()));
                         tvAddr.setText(beacon.getMacAddress());
                         tvMajor.setText(String.valueOf(beacon.getDeviceMode()));
                         tvMinor.setText(String.valueOf(beacon.getBattery()));
                         tvUuid.setText(String.valueOf(AppConfigUitls.byteArrayToHexStr(beacon.getUserData())));

                         beaconList2.add(beacon.getMacAddress());


                 }


                return view;
            }
        };

        listView.setAdapter(beaconAdapter);
        listView.setOnItemClickListener(this);



        findViewById(R.id.btn_refresh).setOnClickListener(this);

        TextView tvIntro = (TextView) findViewById(R.id.tv_intro);
        if(clickAction == ACTION_ADJUST) {
            tvIntro.setText("请点击Beacon设备,进行距离校准!");
        } else if(clickAction == ACTION_EDIT) {
            tvIntro.setText("请点击Beacon设备,进行参数配置!");
        } else if(clickAction == ACTION_NOTIFY){
            tvIntro.setText("请点击Beacon设备,进行通知测试!");
        } else {
            tvIntro.setVisibility(View.GONE);
        }

        beaconManager = BRTBeaconManager.getInstance(this);
//        beaconManager.setBRTBeaconManagerListener(scanListener);
        beaconManager.startService();
        beaconManager.startRanging();
        beaconManager.setBRTBeaconManagerListener(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        }else{
            checkBluetoothValid();
        }
    }








    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.setBRTBeaconManagerListener(scanListener);
        beaconManager.startRanging();
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.stopRanging();
        beaconManager.setBRTBeaconManagerListener(null);
    }

    private void checkBluetoothValid() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null) {
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("错误").setMessage("你的设备不具备蓝牙功能!").create();
            dialog.show();
            return;
        }

        if(!adapter.isEnabled()) {
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("蓝牙设备未打开,请开启此功能后重试!")
                    .setPositiveButton("确认", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(mIntent, 1);
                        }
                    })
                    .create();
            dialog.show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BRTBeacon beacon = (BRTBeacon)parent.getItemAtPosition(position);
//        switch(clickAction) {
//            case ACTION_EDIT: {
//                DeviceConfigActivity.startActivity(this, beacon);
//                break;
//            }
//
//            case ACTION_ADJUST: {
//                DeviceAdjustActivity.startActivity(this, beacon);
//                break;
//            }
//
//            case ACTION_NOTIFY: {
//                DeviceMonitorActivity.startActivity(this, beacon);
//                break;
//            }
//
//        }
    }

    private BRTBeaconManagerListener scanListener = new BRTBeaconManagerListener() {

        @Override
        public void onUpdateBeacon(final ArrayList<BRTBeacon> beaconArrayList) {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    beaconList.clear();
                    //只显示标题是beacon的 设备
                    beaconList.addAll(beaconArrayList);
                    beaconAdapter.notifyDataSetChanged();
                }
            });

        }

        @Override
        public void onNewBeacon(BRTBeacon brtBeacon) {

//            beaconList.add(brtBeacon);

        }

        @Override
        public void onGoneBeacon(BRTBeacon arg0) {


        }

        @Override
        public void onError(BRTThrowable arg0) {

        }
    };




    public static void startActivity(Context context, int clickType) {
        Intent intent = new Intent(context, ScanActivity.class);
        intent.putExtra(KEY_ACTION_TYPE, clickType);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_refresh: {
                beaconManager.stopRanging();
                beaconList.clear();
                beaconAdapter.notifyDataSetChanged();
                beaconManager.startRanging();
                break;
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkBluetoothValid();
                }
                break;
        }


    }

    /**
     * 发送通知
     */
    public  void  showNotificaiton(){
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("您已进入自动停车场附件")//设置通知栏标题
                .setContentText("是否需要停车")
                .setTicker("新消息") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
        mBuilder.setContentIntent(getDefalutIntent(2));

          if (true){
          //检查当前用户是存在进行中的订单 如果存在就不发送了

        }else {
              if (sharedPreferenceUtils.getIntValueFromSP("NotificationManager","NotificationManager")==0){
                  //说明是第一次 可以发送
                  mNotificationManager.notify(2, mBuilder.build());
                  sharedPreferenceUtils.setIntDataIntoSP("NotificationManager","NotificationManager",1);
          }
      }

    }

    public PendingIntent getDefalutIntent(int flags){
        Intent intent = new Intent(BaseApplication.mContext, IndexActivity.class);
        intent.putExtra("NotificationManager","true");
        PendingIntent pendingIntent= PendingIntent.getActivity(this, flags, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

}
