package com.feast.demo.web.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by matao on 2017/5/14.
 */
@Data
public class DishesCategoryObj {

    private String imei;
    private String androidId;
    private String ipv4;
    private String mac;
    private String storeId;
    private String resultCode;
    private String resultMsg;
    private ArrayList dishesCategoryList;
}
