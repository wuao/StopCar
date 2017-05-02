package com.sanxiongdi.stopcar.entity;

import java.util.ArrayList;

/**
 * 返回随机号实体
 * Created by wuaomall@gmail.com on 2017/4/28.
 */

public class RandomNumberEntity {

    public  int state;//返回状态
    public ArrayList<String> result;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

    public RandomNumberEntity() {
        super();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
