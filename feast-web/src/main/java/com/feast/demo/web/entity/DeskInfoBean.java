package com.feast.demo.web.entity;

import lombok.Data;

/**
 * Created by wang-pp on 2017/10/22.
 */
@Data
public class DeskInfoBean {
    private String maxPerson;
    private String minPerson;
    private String mobileNO;
    private String storeID;
    private String type;
    private String desc;
    private String resultCode;
    private String deskID;
    private String bid;

    public String getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(String maxPerson) {
        this.maxPerson = maxPerson;
    }

    public String getMinPerson() {
        return minPerson;
    }

    public void setMinPerson(String minPerson) {
        this.minPerson = minPerson;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
