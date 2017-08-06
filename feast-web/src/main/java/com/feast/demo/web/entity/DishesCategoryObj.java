package com.feast.demo.web.entity;

import java.util.ArrayList;

/**
 * Created by aries on 2017/5/14.
 */
public class DishesCategoryObj {

    private String imei;
    private String androidID;
    private String ipv4;
    private String mac;
    private String storeID;
    private String resultCode;
    private String resultMsg;
    private ArrayList dishesCategoryList;

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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public ArrayList getDishesCategoryList() {
        return dishesCategoryList;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public void setDishesCategoryList(ArrayList dishesCategoryList) {
        this.dishesCategoryList = dishesCategoryList;
    }
}
