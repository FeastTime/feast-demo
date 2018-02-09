package com.feast.demo.web.entity;


/**
 *
 * Created by Administrator on 2017/10/27.
 */
public class UserBean {

    public static final int STORE = 2;

    public static final int CUSTOMER = 2;

    private String userID;
    private String name;
    private long price;
    private int numberPerTable;
    // 用户类型 1 普通用户, 2 商户
    private int userType;
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

    public int getNumberPerTable() {
        return numberPerTable;
    }

    public void setNumberPerTable(int numberPerTable) {
        this.numberPerTable = numberPerTable;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
