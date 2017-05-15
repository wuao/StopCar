package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.IUserInfoSeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuaomall@gmail.com on 2017/5/15.
 */

public class UserInfoSetingPresenter extends BasePresenter<IUserInfoSeting> {

    private Map<String, Object> maps;

    public UserInfoSetingPresenter(Context context, IUserInfoSeting view) {
        super(context, view);
        maps = new HashMap<>();
    }
    public void queryuserinfo(int id) {
        put("method", "res.users.search_read");
        List<Object> lists = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        List<List<Object>> lists2 = new ArrayList<>();
        list.add("id");
        list.add("=");
        list.add(id);
        lists.add(list);
        list = new ArrayList<>();
        list.add("name");
        list.add("car_user_callphone_number");
        list.add("car_user_state");
        list.add("car_user_address");
        list.add("car_user_login_time");
        list.add("car_user_online");
        list.add("car_user_vip_out_date");
        lists2.add(lists);
        lists2.add(list);
        put("args", lists2);
        ApiExecutor.getInstance().queryUser(initGson().toJson(map1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WrapperEntity<List<UserInfoEntity>>>() {
                    @Override
                    public void onCompleted() {
//                        Log.d("log---", URLDecoder.decode(oldRequest.toString(),"UTF-8"));
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(WrapperEntity<List<UserInfoEntity>> listWrapperEntity) {
//                        if (listWrapperEntity == null || listWrapperEntity.state != 1) {
//                            view.queryOrderFailure(false, -1, "");
//                        } else
//                            view.queryOrderSuccess(listWrapperEntity.result);
                    }
                });
    }


    public void updataUserInfoSeting(String userid, UserInfoEntity userInfoEntity) {
        put("method", "park.order.search_read");
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        List<List<Object>> lists2 = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        list.add(userid);
        listparam.put("name", userInfoEntity.name);
        listparam.put("car_user_callphone_number", userInfoEntity.car_user_callphone_number);
        listparam.put("car_user_state", userInfoEntity.car_user_state);
        listparam.put("car_user_address", userInfoEntity.car_user_address);
        listparam.put("car_user_login_time", userInfoEntity.car_user_login_time);
        listparam.put("car_user_online", userInfoEntity.car_user_online);
        listparam.put("car_user_vip_out_date", userInfoEntity.car_user_vip_out_date);
        lists2.add(list);
        lists.add(lists2);
        lists.add(listparam);
        put("args", map2);
    }

}


