package com.sanxiongdi.stopcar.uitls;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Locale;
import java.util.Set;

/**
 * 蓝牙帮助模块
 * 
 * @author c
 * 
 */
public class BluetoothUtils {
	/**
	 * 检查蓝牙模块是否存在
	 * 
	 * @return
	 */
	public static boolean checkBluetoothExists() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothAdapter != null) {
			return true;
		}
		return false;
	}

	/**
	 * 打开蓝牙模块
	 * 
	 * @return
	 */
	public static boolean openBluetoothDevice() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (!bluetoothAdapter.isEnabled()) {
			if (bluetoothAdapter.enable()) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	/**
	 * 开启蓝牙模块扫描
	 * 
	 * @return
	 */
	public static void startDiscovery() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (!bluetoothAdapter.isDiscovering()) {
			bluetoothAdapter.startDiscovery();
		}
	}

	/**
	 * Convert hex string to byte[] 把为字符串转化为字节数组
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		hexString = hexString.replaceAll(" ", "");
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase(Locale.getDefault());
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 获取已配对的蓝牙设备集合
	 * 
	 * @return
	 */
	public static Set<BluetoothDevice> getBondedDevices() {
		return BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	}

	/**
	 * 检测当前device是否已经bonded过
	 * 
	 * @param device
	 * @return
	 */
	public static boolean isBonded(BluetoothDevice device) {
		if (checkBluetoothExists()) {
			// 连接之前先确定是否已经bond过，配对过
			Set<BluetoothDevice> bondedDevices = BluetoothAdapter
					.getDefaultAdapter().getBondedDevices();
			if (bondedDevices != null) {
				for (BluetoothDevice bluetoothDevice : bondedDevices) {
					if (bluetoothDevice.getAddress()
							.equals(device.getAddress())) {
						// 该device已经bond过
						return true;
					}
				}
			}
		}
		return false;
	}
}
