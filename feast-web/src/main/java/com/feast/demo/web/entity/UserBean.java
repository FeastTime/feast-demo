package com.feast.demo.web.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/27.
 */
public class UserBean {
    private String userID;
    private String name;
    private long price;
//    private long highPrice;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

//    public long getHighPrice() {
//        return highPrice;
//    }
//
//    public void setHighPrice(long highPrice) {
//        this.highPrice = highPrice;
//    }
}
