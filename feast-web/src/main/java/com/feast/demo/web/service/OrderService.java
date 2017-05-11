package com.feast.demo.web.service;

import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.entity.UserObj;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by wpp on 2017/5/9.
 */
public class OrderService {

    public OrderObj getCreatedOrder(OrderObj orderObj){

        System.out.println("androidID is:"+orderObj.getAndroidID());
        System.out.println("imei is:"+orderObj.getImei());
        System.out.println("ipv4 is:"+orderObj.getIpv4());
        System.out.println("mac is:"+orderObj.getMac());
        System.out.println("mobileNO is:"+orderObj.getMobileNO());

        orderObj.setResultCode("0");
        orderObj.setToken("ljiqsdgf54sdfweq6565f7wes51635sad4f65f");
        orderObj.setOrderID("1111222233334444");

        return orderObj;
    }

}
