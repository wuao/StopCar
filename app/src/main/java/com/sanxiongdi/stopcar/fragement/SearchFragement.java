package com.sanxiongdi.stopcar.fragement;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.adapter.DeviceAdapter;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.uitls.BlueToothController;
import com.sanxiongdi.stopcar.uitls.RootLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuaomall@gmail.com on 2017/4/7.
 */

public class SearchFragement  extends BaseFrament{

    private Context mContext;
    private  View view;
    private Toolbar toolbar;
    private RootLayout mrootLayout;
    private SwipeRefreshLayout layout_swipe_refresh;
    public static final int REQUEST_CODE = 0;
    private List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private List<BluetoothDevice> mBondedDeviceList = new ArrayList<>();

    private BlueToothController mController = new BlueToothController();
    private ListView mListView;
    private DeviceAdapter mAdapter;
    private Toast mToast;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.search_fragement, container, false);
        toolbar=(Toolbar)view.findViewById(R.id.tool_bar);



        IntentFilter filter = new IntentFilter();
        //开始查找
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        //结束查找
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //查找设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备扫描模式改变
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //绑定状态
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());

        broadcastManager.registerReceiver(mReceiver, filter);

        mController.turnOnBlueTooth(getActivity(), REQUEST_CODE);
        initView();
        return  view;
    }

    @Override
    protected void initDate(Bundle  mbundle) {

    }

    @Override
    public void  onClick(View view){


    }

    @Override
    protected void initView() {
        mListView = (ListView) view.findViewById(R.id.device_list);
        mAdapter = new DeviceAdapter(mDeviceList, getContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(bindDeviceClick);
        layout_swipe_refresh=(SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        layout_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                initblue();
                initlook();
            }
        });
    }
    @Override
    protected void onsetListener() {

    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.search_fragement;

    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if( BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action) ) {
                showToast("加载中");
                //初始化数据列表
                mDeviceList.clear();
                mAdapter.notifyDataSetChanged();
            }
            else if( BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                showToast("加载完成");
            }
            else if( BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //找到一个，添加一个
                mDeviceList.add(device);
                mAdapter.notifyDataSetChanged();
            }
            else if( BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,0);
                if( scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    showToast("加载中");
                }
                else {
                    showToast("加载完成");
                }
            }
            else if( BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action) ) {
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if( remoteDevice == null ) {
                    showToast("no device");
                    return;
                }
                int status = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE,0);
                if( status == BluetoothDevice.BOND_BONDED) {
                    showToast("Bonded " + remoteDevice.getName());
                }
                else if( status == BluetoothDevice.BOND_BONDING){
                    showToast("Bonding " + remoteDevice.getName());
                }
                else if(status == BluetoothDevice.BOND_NONE){
                    showToast("Not bond " + remoteDevice.getName());
                }
            }
        }
    };

    private void showToast(String text) {

        if( mToast == null) {
            mToast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        }
        else {
            mToast.setText(text);
        }
        mToast.show();
    }




    private AdapterView.OnItemClickListener bindDeviceClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            BluetoothDevice device = mDeviceList.get(i);
            device.createBond();
        }
    };

     public  void  initblue(){
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
             if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
             }
         }
         mController.enableVisibly(getContext());//打开蓝牙可见性
         mAdapter.refresh(mDeviceList);
         mController.findDevice();
         mListView.setOnItemClickListener(bindDeviceClick);//查找设备
     }
    public  void  initlook(){
        mBondedDeviceList = mController.getBondedDeviceList();
        mAdapter.refresh(mBondedDeviceList);
        mListView.setOnItemClickListener(null);

    }




}
