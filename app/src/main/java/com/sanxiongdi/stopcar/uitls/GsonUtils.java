package com.sanxiongdi.stopcar.uitls;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sanxiongdi.stopcar.uitls.ConvertUtils.jsonToObject;

/**
 * 功能说明：
 * 创建人： lijian
 * 创建时间： 2017/3/21
 */
public class GsonUtils {

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        return new Gson().toJson(object);
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        return new Gson().fromJson(gsonString, cls);
    }


    /**
     * 返回cla 类型的list数组
     *
     * @param s
     * @param cla
     * @return
     */
    public static <T extends Object> T jsonToBeanList(String s, Class<?> cla) {

        List<Object> ls = new ArrayList<Object>();
        JSONArray ss;
        try {
            ss = new JSONArray(s);
            for (int i = 0; i < ss.length(); i++) {
                String str = ss.getString(i);
                Object a = jsonToObject(str, cla);
                ls.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (T) ls;
    }


    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        return new Gson().fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(new Gson().fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
//    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
//        return new Gson().fromJson(gsonString,
//                new TypeToken<List<Map<String, T>>>() {
//                }.getType());
//    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        return new Gson().fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }
}
