package com.feast.demo.web.memory;


import com.feast.demo.web.entity.OrderObj;

import java.util.HashMap;
import java.util.Map;

public class OrderMemory {

    private static Map<String, OrderObj> orderMap = new HashMap<String, OrderObj>();

    public static OrderObj get(String key){

        return orderMap.get(key);
    }

    public static void set(String key, OrderObj orderObj){
        orderMap.put(key, orderObj);
    }

    public void remove(String key){
        orderMap.remove(key);
    }


}
