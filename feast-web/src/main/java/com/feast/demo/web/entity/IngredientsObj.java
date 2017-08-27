package com.feast.demo.web.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by matao on 2017/8/27.
 */
@Data
public class IngredientsObj {

    private String imei;
    private String androidId;
    private String ipv4;
    private String mac;
    private String dishId;
    private String resultCode;
    private String resultMsg;
    private ArrayList ingredientsList;
}
