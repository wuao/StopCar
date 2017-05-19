package com.sanxiongdi.stopcar.uitls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Date;
/**
 * Created by lin.woo on 2017/5/3.
 */
public class Config {

	public static boolean isDebug = true;
	public static boolean isLogSave = true;
	public static final String TAG = "Config";

	public static void e(Object msg) {
		if (isDebug)
			Log.e(getTag(4), msg == null ? "null" : msg.toString());
	}

	public static void d(Object msg) {
		if (isDebug)
			Log.d(getTag(4), msg == null ? "null" : msg.toString());
	}

	public static void i(Object msg) {
		if (isDebug)
			Log.i(getTag(4), msg == null ? "null" : msg.toString());
	}

	private static File logFile;
	private static Date logDate;


	public static void showTip(Context context, String msg) {
		if (null == context) {
			return;
		}
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showTip(Context context, int msg) {
		if (null == context) {
			return;
		}
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static String getTag(int level) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[level];
		StringBuilder sb = new StringBuilder();
		sb.append(getSimpleClassName(ste.getClassName()));
		sb.append('.');
		sb.append(ste.getMethodName());
		sb.append('(');
		sb.append(ste.getLineNumber());
		sb.append(')');
		return sb.toString();
	}

	public static String getSimpleClassName(String path) {
		int index = path.lastIndexOf('.');
		if (index < 0) {
			return path;
		} else {
			return path.substring(index + 1);
		}
	}
}
