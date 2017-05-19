package com.sanxiongdi.stopcar.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lin.woo on 2017/5/4.
 */

public class BasePresenter<T> {
    protected Context mContext;
    protected T view;
    protected Gson gson;
    //外层基本参数
    protected HashMap<String, Object> map1;
    //独特参数
    protected HashMap<String, Object> map2 = new HashMap<>();;

    public BasePresenter(Context context, T view) {
        this.view = view;
        this.mContext = context;
        put("login", "admin");
        put("password", "admin");
        put("args", map2);
    }


    protected Gson initGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    protected HashMap<String, Object> put(String key, Object value) {
        if (map1 == null) {
            map1 = new HashMap<>();
        }
        if (TextUtils.isEmpty(key))
            return map1;

        map1.put(key, value);
        return map1;
    }

    protected HashMap<String,Object> put2(String key,Object value){
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        if (TextUtils.isEmpty(key))
            return map2;

        map2.put(key, value);
        return map2;
    }
}
