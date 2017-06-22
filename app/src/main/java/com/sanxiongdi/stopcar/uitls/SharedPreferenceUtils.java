package com.sanxiongdi.stopcar.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sanxiongdi.stopcar.base.BaseApplication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 缓存的工具类
 *
 *
 *
 */
public class SharedPreferenceUtils {

	/*
	 * 使用默认模式 Context.Context.MODE_PRIVATE=0 创造的文件仅能被调用的应用存取（或者共用相同user ID的所有应用）
	 * 另外两种模式
	 * Context.MODE_WORLD_READABLE,Context.MODE_WORLD_WRITEABLE在api里已不建议使用
	 */
	private static int MODE_PRIVATE = Context.MODE_PRIVATE;

	/**
	 * 将一个String数据存入到缓存中
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            要存入缓存中的key
	 * @param valueStr
	 *            要存入缓存中的value
	 */
	public static void setStringDataIntoSP(String spName, String keyStr,
                                           String valueStr) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sp.edit().putString(keyStr, valueStr).commit();

	}

	/**
	 * 将一个Boolean数据存入到缓存中
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            要存入缓存中的key
	 * @param valueStr
	 *            要存入缓存中的value
	 */
	public static void setBooleanDataIntoSP(String spName, String keyStr,
                                            Boolean valueStr) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sp.edit().putBoolean(keyStr, valueStr).commit();
	}

	/**
	 * 将一个Int数据存入到缓存中
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            要存入缓存中的key
	 * @param valueStr
	 *            要存入缓存中的value
	 */
	public static void setIntDataIntoSP(String spName, String keyStr,
                                        int valueStr) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sp.edit().putInt(keyStr, valueStr).commit();
	}

	/**
	 * 将一个Float数据存入到缓存中
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            要存入缓存中的key
	 * @param valueStr
	 *            要存入缓存中的value
	 */
	public static void setFloatDataIntoSP(String spName, String keyStr,
                                          Float valueStr) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sp.edit().putFloat(keyStr, valueStr).commit();
	}

	/**
	 * 将一个Long数据存入到缓存中
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            要存入缓存中的key
	 * @param valueStr
	 *            要存入缓存中的value
	 */
	public static void setLongDataIntoSP(String spName, String keyStr,
                                         Long valueStr) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sp.edit().putLong(keyStr, valueStr).commit();
	}

	/**
	 * 将一系列String数据存入到缓存中
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            要存入缓存中的key
	 * @param valueStringSet
	 *            一系列的String数据
	 */
	public static void setStringSetDataIntoSP(String spName, String keyStr,
                                              Set<String> valueStringSet) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sp.edit().putStringSet(keyStr, valueStringSet);
	}

	/**
	 * 将键值对数组，存入到缓存中
	 *
	 * @param spName
	 *            缓存的名称
	 * @param keyValueMap
	 *            要存入缓存中的键值对,<String,
	 *            Object>其中Object只能是String,Integer,Boolean,Long,Float
	 */
	public static void setHashMapDataIntoSP(String spName,
											HashMap<String, Object> keyValueMap) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		Editor editor = sp.edit();
		if (keyValueMap != null && !keyValueMap.isEmpty()) {
			Set<String> keySet = keyValueMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = keyValueMap.get(key);
				if (value.getClass() == String.class) {
					editor.putString(key, (String) value);
				} else if (value.getClass() == Integer.class) {
					editor.putInt(key, (Integer) value);
				} else if (value.getClass() == Boolean.class) {
					editor.putBoolean(key, (Boolean) value);
				} else if (value.getClass() == Long.class) {
					editor.putLong(key, (Long) value);
				} else if (value.getClass() == Float.class) {
					editor.putFloat(key, (Float) value);
				}
			}
			editor.commit();
		}
	}

	/**
	 * 获取缓存中的一个String数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @return 缓存中对应参数key的value，如果没有则返回“”
	 */
	public static String getStringValueFromSP(String spName, String keyStr) {
		return getStringValueFromSP(spName, keyStr, "");
	}

	/**
	 * 获取缓存中的一个String数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @param defaultValue
	 *            缓存中对应参数key的默认值
	 * @return 缓存中对应参数key的value，如果没有则返回defaultValue
	 */
	public static String getStringValueFromSP(String spName, String keyStr,
                                              String defaultValue) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		return sp.getString(keyStr, defaultValue);
	}

	/**
	 * 获取缓存中的一个Float数据sp.getFloat(key, 0.0f)
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @return 缓存中对应参数key的value,如果没有则返回0.0f
	 */
	public static Float getFloatValueFromSP(String spName, String keyStr) {
		return getFloatValueFromSP(spName, keyStr, 0.0f);
	}

	/**
	 * 获取缓存中的一个Float数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @param defaultValue
	 *            缓存中对应参数key的默认值
	 * @return 缓存中对应参数key的value，如果没有则返回defaultValue
	 */
	public static Float getFloatValueFromSP(String spName, String keyStr,
                                            Float defaultValue) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		return sp.getFloat(keyStr, defaultValue);
	}

	/**
	 * 获取缓存中的一个Int数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @return 缓存中对应参数key的value，如果没有则返回0
	 */
	public static int getIntValueFromSP(String spName, String keyStr) {
		return getIntValueFromSP(spName, keyStr, 0);
	}

	/**
	 * 获取缓存中的一个Int数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @param defaultValue
	 *            缓存中对应参数key的默认值
	 * @return 缓存中对应参数key的value,如果没有则返回defaultValue
	 */
	public static int getIntValueFromSP(String spName, String keyStr,
                                        int defaultValue) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		return sp.getInt(keyStr, defaultValue);
	}

	/**
	 * 获取缓存中的一个Boolean数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @return 缓存中对应参数key的value,如果没有则返回false
	 */
	public static boolean getBooleanValueFromSP(String spName, String keyStr) {
		return getBooleanValueFromSP(spName, keyStr, false);
	}

	/**
	 * 获取缓存中的一个Boolean数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @param defaultValue
	 *            缓存中对应参数key的默认值
	 * @return 缓存中对应参数key的value,如果没有则返回defaultValue
	 */
	public static boolean getBooleanValueFromSP(String spName, String keyStr,
                                                Boolean defaultValue) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		return sp.getBoolean(keyStr, defaultValue);
	}

	/**
	 * 获取缓存中的一个Long数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @return 缓存中对应参数key的value,如果没有则返回0l
	 */
	public static Long getLongValueFromSP(String spName, String keyStr) {
		return getLongValueFromSP(spName, keyStr, 0l);
	}

	/**
	 * 获取缓存中的一个Long数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @param defaultValue
	 *            缓存中对应参数key的默认值
	 * @return 缓存中对应参数key的value,如果没有则返回defaultValue
	 */
	public static Long getLongValueFromSP(String spName, String keyStr,
                                          Long defaultValue) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		return sp.getLong(keyStr, defaultValue);
	}

	/**
	 * 获取缓存中的一系列String数据
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyStr
	 *            已存入缓存中的key
	 * @return 缓存中对应参数key的一系列String值,如果没有则返回null
	 */
	public static Set<String> getStringSetValueFromSP(String spName,
                                                      String keyStr) {
		Set<String> sets = new HashSet<String>();
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sets = sp.getStringSet(keyStr, null);
		return sets;
	}

	/**
	 * 获取多个key值对应的values
	 *
	 * @param spName
	 *            缓存的名字
	 * @param keyValueMap
	 *            要获取的缓存中的key值
	 * @return
	 */
	public static HashMap<String, Object> getHashMapValuesFromSP(String spName,
                                                                 HashMap<String, Object> keyValueMap) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		if (keyValueMap != null && !keyValueMap.isEmpty()) {
			Set<String> keySet = keyValueMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = keyValueMap.get(key);
				if (value instanceof String) {
					values.put(key, sp.getString(key, ""));
				} else if (value instanceof Integer) {
					values.put(key, sp.getInt(key, 0));
				} else if (value instanceof Boolean) {
					values.put(key, sp.getBoolean(key, false));
				} else if (value instanceof Long) {
					values.put(key, sp.getLong(key, 0l));
				} else if (value instanceof Float) {
					values.put(key, sp.getFloat(key, 0.0f));
				}
			}
		}
		return values;
	}

	/**
	 * 获取缓存中所有的数据
	 *
	 * @param spName
	 *            缓存的数据
	 * @return
	 */
	public static Map<String, ?> getAllFromSP(String spName) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 验证缓存中是否已经有某个key值
	 *
	 * @param spName
	 *            缓存的名字
	 * @param key
	 *            要查询的key值
	 * @return
	 */
	public static boolean hasKeyInSP(String spName, String key) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 删除缓存中的某个键值对
	 *
	 * @param spName
	 *            缓存的名字
	 * @param key
	 *            欲删除的缓存中的key值
	 */
	public static void deleteValueInSP(String spName, String key) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		if (sp.contains(key)) {
			sp.edit().remove(key).commit();
		}
	}

	/**
	 * 删除缓存中的所有值
	 *
	 * @param spName
	 *            缓存的名字
	 */
	public static void deleteAllInSP(String spName) {
		SharedPreferences sp = BaseApplication.mContext
				.getSharedPreferences(spName, MODE_PRIVATE);
		sp.edit().clear().commit();
	}

}
