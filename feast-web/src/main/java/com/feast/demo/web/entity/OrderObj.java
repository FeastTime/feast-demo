package com.feast.demo.web.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by wpp on 2017/5/9.
 */
@Data
public class OrderObj {
    private String imei;
    private String androidID;
    private String ipv4;
    private String mac;
    private String mobileNO;
    private String token;
    private String resultCode;
    private String orderID;
    // 下单时间
    private long orderTime;
    // 需要时间
    private long needTime;
    // 状态  0:未下单 1:已下单 2:已完成
    private byte state;
    private HashMap<String, MyDishObj> myDishMap;
    private HashMap<String, RecommendDishObj> recommendDishMap;

    private BigDecimal price;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAndroidID() {
        return androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public HashMap<String, MyDishObj> getMyDishMap() {
        return myDishMap;
    }

    public void setMyDishMap(HashMap<String, MyDishObj> myDishMap) {
        this.myDishMap = myDishMap;
    }

    public HashMap<String, RecommendDishObj> getRecommendDishMap() {
        return recommendDishMap;
    }

    public void setRecommendDishMap(HashMap<String, RecommendDishObj> recommendDishMap) {
        this.recommendDishMap = recommendDishMap;
    }
    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getNeedTime() {
        return needTime;
    }

    public void setNeedTime(long needTime) {
        this.needTime = needTime;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

}
