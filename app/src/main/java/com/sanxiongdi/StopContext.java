package com.sanxiongdi;

import com.google.gson.Gson;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.uitls.SharedPreferenceUtils;
import com.sanxiongdi.stopcar.uitls.StringUtils;

/**
 * Created by wuaomall@gmail.com on 2017/6/25.
 */

public class StopContext {
    private static StopContext instance;

    public static StopContext getInstance() {
        if (instance == null) {
            instance = new StopContext();
        }
        return instance;
    }


    public UserInfoEntity getUserInfo() {
        String gson = SharedPreferenceUtils
                .getStringValueFromSP("UserInfo", "UserInfo");
        if (!StringUtils.checkNull(gson)) {
            return new Gson().fromJson(gson, UserInfoEntity.class);
        } else {
            return new UserInfoEntity();
        }
    }

    public void setUserInfo(String gson) {
        SharedPreferenceUtils.setStringDataIntoSP("UserInfo", "UserInfo", gson);
    }

}
