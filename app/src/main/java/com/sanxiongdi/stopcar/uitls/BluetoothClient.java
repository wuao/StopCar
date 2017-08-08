package com.sanxiongdi.stopcar.uitls;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 蓝牙服务器
 * 
 * @author weizh
 * 
 */
public class BluetoothClient {
	private Context context;
	private static final String TAG = "BluetoothClient";
	/**
	 * 消息集合
	 */
	private List<String> listMsg = new ArrayList<String>();
	/**
	 * 是否工作中
	 */
	private boolean isWorking = false;
	/**
	 * spp well-known UUID
	 */
	public static   final UUID uuid = UUID
			.fromString("00000000-05cf-ff37-3631-484843217437");
//			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	/**
	 * 客户端socket
	 */
	private BluetoothSocket mClientSocket;

	public BluetoothClient(Context context) {
		this.context = context;
	}

	/**
	 * 开启服务器
	 */
	public void start() {
		startDiscovery();
	}

	/**
	 * 开始检查设备
	 */
	private void startDiscovery() {
		if (!BluetoothUtils.checkBluetoothExists()) {
			throw new RuntimeException("bluetooth module not exists.");
		}
		// 打开设备
		if (!BluetoothUtils.openBluetoothDevice()) {
			return;
		}
		// 开始扫描设备
		BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
		defaultAdapter.startDiscovery();
	}

	OutputStream outputStream;
	private InputStream inputStream;

	/**
	 * 停止
	 */
	public void stop() {
		isWorking = false;
		if (mClientSocket != null) {
			try {
				mClientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				mClientSocket = null;
			}
		}
	}

	/**
	 * 客户端socket工作类
	 * 
	 * @author weizh
	 * 
	 */
	private class ClientWorkingThread extends Thread {

		public ClientWorkingThread() {
		}

		@SuppressLint("NewApi")
		@Override
		public void run() {
			try {
				// 从输入流中取出数据，插入消息条中
				byte[] buffer = new byte[1024];
				while (isWorking) {
					int read = inputStream.read(buffer);
					if (read != -1) {
						// 有内容
						// 判断是否取得的消息填充满了buffer，未到字符串结尾符；如果不是，证明读取到了一条信息，并且信息是完整的，这个完整的前提是不能粘包，不粘包可以使用flush进行处理。
						StringBuilder sb = new StringBuilder();
						if (read < buffer.length) {
							String msg = new String(buffer, 0, read);
							sb.append(msg);
						} else {
							byte[] tempBytes = new byte[1024 * 4];
							while (read == buffer.length
									&& buffer[read - 1] != 0x7f) {
								read = inputStream.read(buffer);
							}
							String msg = new String(buffer, 0, read);
							sb.append(msg);
						}
						Toast.makeText(context,"客户端收到：" + sb.toString(), Toast.LENGTH_SHORT).show();
						Log.i(TAG, "客户端收到：" + sb.toString());
						synchronized (listMsg) {
							Toast.makeText(context,"服务器发送：" + sb.toString(), Toast.LENGTH_SHORT).show();
							listMsg.add("服务器发送：" + sb.toString());
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 工作完毕，关闭socket
			try {
				mClientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 返回listMsg
	 * 
	 * @return
	 */
	public List<String> getMsgs() {
		synchronized (listMsg) {
			return listMsg;
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 */
	public void send(final String msg) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (mClientSocket != null) {
					try {
						if (outputStream != null) {
							byte[] bytes = msg.getBytes();
							outputStream.write(bytes);
							outputStream.flush();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	/**
	 * 进行连接
	 * 
	 * @param device
	 */
	@SuppressLint("NewApi")
	public void connect(final BluetoothDevice device) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					mClientSocket = device
							.createRfcommSocketToServiceRecord(uuid);
					mClientSocket.connect();
					isWorking = true;
					try {
						outputStream = mClientSocket.getOutputStream();
						inputStream = mClientSocket.getInputStream();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					new ClientWorkingThread().start();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(context,"连接失败", Toast.LENGTH_SHORT).show();

					Log.i(TAG, "连接失败");
				}
			}
		}).start();
	}

}
