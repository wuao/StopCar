package com.sanxiongdi.stopcar.entity;

/**
 * Created by lin.woo on 2017/5/3.
 */

public class WrapperEntity<T> extends BaseEntity<T> {
    public int state;
    public T result;
}
