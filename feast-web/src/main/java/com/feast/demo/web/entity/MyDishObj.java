package com.feast.demo.web.entity;

/**
 * Created by Administrator on 2017/5/23.
 */
public class MyDishObj {

    private String dishID;
    private String dishNO;
    private String dishName;
    private String dishImgUrl;
    private String todayPrice;
    private String amount;
    private String price;
    private String extraFlag;
    // 需要时长
    private long needTime;
    // 开始时间
    private long startTime;
    // 状态 0:未开始 1:下锅 2完成
    private byte state;

    public String getDishID() {
        return dishID;
    }

    public void setDishID(String dishID) {
        this.dishID = dishID;
    }

    public String getDishNO() {
        return dishNO;
    }

    public void setDishNO(String dishNO) {
        this.dishNO = dishNO;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishImgUrl() {
        return dishImgUrl;
    }

    public void setDishImgUrl(String dishImgUrl) {
        this.dishImgUrl = dishImgUrl;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExtraFlag() {
        return extraFlag;
    }

    public void setExtraFlag(String extraFlag) {
        this.extraFlag = extraFlag;
    }

    public long getNeedTime() {
        return needTime;
    }

    public void setNeedTime(long needTime) {
        this.needTime = needTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}
