package com.feast.demo.web.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by matao on 2017/5/10.
 */
@Data
public class MenuObj {
    private String imei;
    private String androidId;
    private String ipv4;
    private String mac;
    private String mobileNo;
    private String token;
    private String orderId;
    private String categoryId;
    private String storeId;
    private String pageNo;
    private String pageNum;
    private String resultCode;
    private String resultMsg;
    private String tmpId;
    private String recordCount;
    private ArrayList dishesList;
}
