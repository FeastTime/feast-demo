package com.feast.demo.web.entity;

import lombok.Data;

/**
 * Created by wang-pp on 2017/10/22.
 */
@Data
public class ComeinRestBean {
    private String imei;
    private String androidID;
    private String ipv4;
    private String mac;
    private String mobileNO;
    private String storeID;
    private String type;
    private String deskID;
    private String resultCode;

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

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getDeskID() {
        return deskID;
    }

    public void setDeskID(String deskID) {
        this.deskID = deskID;
    }
}
