package com.sanxiongdi.stopcar.presenter;

import android.content.Context;
import android.util.Log;

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

import static android.R.attr.id;

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
        list.add("login");
        list.add("ref");
        list.add("image");
        list.add("car_user_phone_id");
        list.add("phone");
        list.add("street");
        list.add("car_user_state");
        list.add("login_date");
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
                        if (listWrapperEntity == null || listWrapperEntity.state != 1) {
                            view.queryUserInfoFailure(false, -1, "获取用户信息失败,请检查网络！");
                        } else
                            view.queryUserInfoSuccess(listWrapperEntity.result);
                    }
                });
    }


    public void updataUserInfoSeting(int userid, UserInfoEntity userInfoEntity) {
        put("method", "res.users.write");
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(userid);
        listparam.put("name", userInfoEntity.name);
        listparam.put("phone", userInfoEntity.phone);
        listparam.put("age", userInfoEntity.age);
        listparam.put("sex", userInfoEntity.sex);
        listparam.put("street", userInfoEntity.street);
        lists.add(list);
        lists.add(listparam);
        put("args", lists);
        Log.i("++", map1.values().toString());
        ApiExecutor.getInstance().updataUser(initGson().toJson(map1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WrapperEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(WrapperEntity wrapperEntity) {
                        if (wrapperEntity == null || wrapperEntity.state != 1) {
                            view.queryUserInfoFailure(false, -1, "更新用户信息失败,请检查网络！");
                        } else
                            view.updataUserInfoSuccess(wrapperEntity);
                    }
                });


    }


    /**
     *  传入设备号创建用户
     * @param userInfoEntity
     */
    public void queryCreatePhoneUserInfo(UserInfoEntity userInfoEntity) {

        put("method", "res.users.create");
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        listparam.put("name", userInfoEntity.car_user_phone_id);
        listparam.put("login", userInfoEntity.car_user_phone_id);
        listparam.put("car_user_phone_id", userInfoEntity.car_user_phone_id);
        lists.add(listparam);
        put("args", lists);
        ApiExecutor.getInstance().getCreateUserInfo(initGson().toJson(map1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WrapperEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WrapperEntity wrapperEntity) {
                        if (wrapperEntity == null || wrapperEntity.state != 1) {
                            view.queryUserInfoFailure(false, -1, "设备信息id创建用户失败,请检查网络！");
                        } else
                            view.creatUserInfoSuccess(wrapperEntity);
                    }
                });
    }


    /**
     * 根据设备号 查询是否存在用户
     * @param car_user_phone_id
     */
    public void querybyphoneuserinfo(int car_user_phone_id) {
        put("method", "res.users.search_read");
        List<Object> lists = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        List<List<Object>> lists2 = new ArrayList<>();
        list.add("car_user_phone_id");
        list.add("=");
        list.add(id);
        lists.add(list);
        list = new ArrayList<>();
        list.add("name");
        list.add("login");
        list.add("ref");
        list.add("image");
        list.add("car_user_phone_id");
        list.add("phone");
        list.add("street");
        list.add("car_user_state");
        list.add("login_date");
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
                        if (listWrapperEntity == null || listWrapperEntity.state != 1) {
                            view.queryUserInfoFailure(false, -1, "当前设备id查询失败,请检查网络！");
                        } else
                            view.queryUserInfoSuccess(listWrapperEntity.result);
                    }
                });
    }

}


