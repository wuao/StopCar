package com.sanxiongdi.stopcar.uitls;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author MLP00
 * @version 1.0
 * @since 2016/10/11
 */
public class ConvertUtils {
    private ConvertUtils() {
    }

    public static String jsonFromBundle(Bundle data) {
        if (data == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            try {
                json.put(key, wrap(data.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

    public static Object wrap(Object o) {
        if (o == null) {
            return JSONObject.NULL;
        }
        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        if (o.equals(JSONObject.NULL)) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return new JSONArray((Collection) o);
            } else if (o.getClass().isArray()) {
                return toJSONArray(o);
            }
            if (o instanceof Map) {
                return new JSONObject((Map) o);
            }
            if (o instanceof Boolean ||
                    o instanceof Byte ||
                    o instanceof Character ||
                    o instanceof Double ||
                    o instanceof Float ||
                    o instanceof Integer ||
                    o instanceof Long ||
                    o instanceof Short ||
                    o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static JSONArray toJSONArray(Object array) throws JSONException {
        JSONArray result = new JSONArray();
        if (!array.getClass().isArray()) {
            throw new JSONException("Not a primitive array: " + array.getClass());
        }
        final int length = Array.getLength(array);
        for (int i = 0; i < length; ++i) {
            result.put(wrap(Array.get(array, i)));
        }
        return result;
    }

    public static final <T> T jsonToObject(String response, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(response, clazz);
    }

    public static HashMap<String, String> object2map(Object obj) {
        if (obj == null) {
            return new HashMap();
        } else {
            LinkedHashMap dataMap = new LinkedHashMap();

            try {
                Field[] var5;
                int var4 = (var5 = obj.getClass().getDeclaredFields()).length;

                for (int var3 = 0; var3 < var4; ++var3) {
                    Field e = var5[var3];
                    e.setAccessible(true);
                    if (e.getType() == String.class && e.get(obj) != null) {
                        dataMap.put(e.getName(), (String) e.get(obj));
                    }
                }

                return dataMap;
            } catch (Exception var6) {
                throw new RuntimeException(var6);
            }
        }
    }

    /**
     * json 转 map
     *
     * @param json
     * @return
     */
    public static Map json2Map(String json) {
        Gson gson = new Gson();
        Map map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());

        return map;
    }

    public static Object myJson2Obj(String json) {
        Gson gson = new Gson();

        Object obj = gson.fromJson(json, new TypeToken<Object>() {

        }.getType());

        return obj;
    }
}
