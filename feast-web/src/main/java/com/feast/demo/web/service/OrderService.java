package com.feast.demo.web.service;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.entity.UserObj;
import com.feast.demo.web.memory.OrderMemory;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by wpp on 2017/5/9.
 */
@Service
public class OrderService {

    public OrderObj getCreatedOrder(JSONObject jsono){

        System.out.println("Create order start...");
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("mobileNO is:"+jsono.getString("mobileNO"));
        System.out.println("token is:"+jsono.getString("token"));

        //jsono.put("resultCode", "0");
        String currentTime = String.valueOf(System.currentTimeMillis());
        String mobileNo = jsono.getString("mobileNO");
        String orderID = jsono.getString("imei")
                + mobileNo.substring(mobileNo.length()-4 , mobileNo.length())
                + currentTime.substring(currentTime.length()-9 , currentTime.length());
        //jsono.put("orderID", orderID);

        OrderObj orderObj = new OrderObj();
        orderObj.setOrderID(orderID);
        orderObj.setAndroidID(jsono.getString("androidID"));
        orderObj.setImei(jsono.getString("imei"));
        orderObj.setIpv4(jsono.getString("ipv4"));
        orderObj.setMac(jsono.getString("mac"));
        orderObj.setMobileNO(jsono.getString("mobileNO"));
        orderObj.setToken(jsono.getString("token"));
        orderObj.setResultCode("0");
        System.out.println("Create order success...");

        return orderObj;
    }

}
