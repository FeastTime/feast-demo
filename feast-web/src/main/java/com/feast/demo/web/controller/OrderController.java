package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.common.json.JSONObject;
import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.memory.OrderMemory;
import com.feast.demo.web.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.feast.demo.web.entity.OrderObj;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wpp on 2017/5/10.
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/createOrder",method = RequestMethod.GET)
    public String createOrder(@RequestParam("json") JSONObject jsono){
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("mobileNO is:"+jsono.getString("mobileNO"));

        JSONObject rtnJson = new JSONObject();
        OrderObj orderObj = new OrderObj();

        orderObj = orderService.getCreatedOrder(jsono);
        OrderMemory.set(orderObj.getOrderID(), orderObj);

        rtnJson.put("resultCode", orderObj.getResultCode());
        rtnJson.put("orderID", orderObj.getOrderID());

        return JSON.toJSONString(rtnJson);
    }
}
