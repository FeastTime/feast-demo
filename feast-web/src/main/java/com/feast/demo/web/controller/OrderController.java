package com.feast.demo.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.service.AdverstismentService;
import com.feast.demo.web.service.OrderService;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wpp on 2017/5/10.
 */
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private static OrderObj orderObj;

    @ResponseBody
    @RequestMapping(value = "/createOrder",method = RequestMethod.GET)
    public Map<String,Object> createOrder(@ModelAttribute("order") OrderObj orderObj1){
        System.out.println("androidID is:"+orderObj1.getAndroidID());
        System.out.println("imei is:"+orderObj1.getImei());
        System.out.println("ipv4 is:"+orderObj1.getIpv4());
        System.out.println("mac is:"+orderObj1.getMac());
        System.out.println("mobileNO is:"+orderObj1.getMobileNO());

        Map<String,Object> result = Maps.newHashMap();

        orderObj = orderService.getCreatedOrder(orderObj);

        result.put("order",orderService.getCreatedOrder(orderObj));

        return result;
    }
}
