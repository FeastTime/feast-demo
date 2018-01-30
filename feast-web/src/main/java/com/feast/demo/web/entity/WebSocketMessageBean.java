package com.feast.demo.web.entity;

/**
 * Created by LiXiaoQing on 2018/1/29.
 */
public class WebSocketMessageBean {

    private String message;
    private String userId;
    private String storeId;

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public WebSocketMessageBean setMessage(String message){
        this.message = message;
        return this;
    }

    public WebSocketMessageBean toUser(String userId){
        this.userId = userId;
        this.storeId = null;
        return this;
    }

    public WebSocketMessageBean toStore(String storeId){
        this.storeId = storeId;
        this.userId = null;
        return this;
    }

}
