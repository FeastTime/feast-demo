package com.feast.demo.web.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/27.
 */
public class UserBean {
    private String userID;
    private String name;
    private BigDecimal price;
    private BigDecimal highPrice;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }
}
