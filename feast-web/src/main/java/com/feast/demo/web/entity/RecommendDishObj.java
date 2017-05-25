package com.feast.demo.web.entity;

/**
 * Created by Administrator on 2017/5/23.
 */
public class RecommendDishObj {
    
    private String dishID;
    private String dishNO;
    private String dishName;
    private String dishImgUrl;
    private String todayPrice;
    private String amount;
    private String beforeOrderTimes;
    private String extraFlag;

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

    public String getBeforeOrderTimes() {
        return beforeOrderTimes;
    }

    public void setBeforeOrderTimes(String beforeOrderTimes) {
        this.beforeOrderTimes = beforeOrderTimes;
    }

    public String getExtraFlag() {
        return extraFlag;
    }

    public void setExtraFlag(String extraFlag) {
        this.extraFlag = extraFlag;
    }
}
