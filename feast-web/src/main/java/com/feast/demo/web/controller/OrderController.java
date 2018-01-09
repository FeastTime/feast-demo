package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.MenuVo;
import com.feast.demo.order.vo.OrderDetailVo;
import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.memory.OrderMemory;

import com.feast.demo.web.service.OrderService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wpp on 2017/5/10.
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Resource
    private OrderService orderService;



    @ResponseBody
    @RequestMapping(value = "/cretaeOrder",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String createOrder(@RequestBody String jsonString){
        System.out.println("createOrder old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("createOrder new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);

        JSONObject rtnJson = new JSONObject();
        OrderObj orderObj = new OrderObj();

        orderObj = orderService.getCreatedOrder(jsono);
        OrderMemory.set(orderObj.getOrderID(), orderObj);

        rtnJson.put("resultCode", orderObj.getResultCode());
        rtnJson.put("orderID", orderObj.getOrderID());
        rtnJson.put("recommendOrderList", parseMapToJSONArray(orderObj.getRecommendDishMap()));

        return JSON.toJSONString(rtnJson);
    }


    @ResponseBody
    @RequestMapping(value = "/addShoppingCart",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String addShoppingCart(@RequestBody String jsonString){
        System.out.println("addShoppingCart old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("addShoppingCart new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);

        JSONObject rtnJson = new JSONObject();

        String orderID = jsono.getString("orderID");
        OrderObj orderObj = OrderMemory.get(orderID);
        orderObj = orderService.addMyDish(jsono, orderObj);
        OrderMemory.set(orderObj.getOrderID(), orderObj);

        rtnJson.put("resultCode", orderObj.getResultCode());
        rtnJson.put("orderID", orderObj.getOrderID());

        rtnJson.put("recommendOrderList", parseMapToJSONArray(orderObj.getRecommendDishMap()));
        rtnJson.put("myOrderList", parseMapToJSONArray(orderObj.getMyDishMap()));
        // 是否需要实现价格计算
        rtnJson.put("totalPrice", orderObj.getPrice());
        rtnJson.put("discountSale", "0.00");
        Random random = new Random();
        int randomM = random.nextInt(20)+1;

        rtnJson.put("orderTime", new Date().getTime()+ randomM*60*1000);
        randomM = random.nextInt(10)+1;
        rtnJson.put("needTime", "" + randomM*60*1000);
        rtnJson.put("status", "0");

        return JSON.toJSONString(rtnJson);
    }



    @ResponseBody
    @RequestMapping(value = "/removeShoppingCart",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String removeShoppingCart(@RequestBody String jsonString){
        System.out.println("removeShoppingCart old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("removeShoppingCart new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("ID is:"+jsono.getString("ID"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        JSONObject rtnJson = new JSONObject();

        String orderID = jsono.getString("orderID");
        OrderObj orderObj = OrderMemory.get(orderID);
        orderObj = orderService.removeMyDish(jsono, orderObj);
        OrderMemory.set(orderObj.getOrderID(), orderObj);

        rtnJson.put("resultCode", orderObj.getResultCode());
        rtnJson.put("orderID", orderObj.getOrderID());

        rtnJson.put("recommendOrderList", parseMapToJSONArray(orderObj.getRecommendDishMap()));
        rtnJson.put("myOrderList", parseMapToJSONArray(orderObj.getMyDishMap()));
        // 是否需要实现价格计算
        rtnJson.put("totalPrice", orderObj.getPrice());
        rtnJson.put("discountSale", "0.00");

        return JSON.toJSONString(rtnJson);
    }

    @ResponseBody
    @RequestMapping(value = "/getShoppingCartList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String getShoppingCartList(@RequestBody String jsonString){
        System.out.println("getShoppingCartList old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("getShoppingCartList new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("ID is:"+jsono.getString("ID"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        JSONObject rtnJson = new JSONObject();

        String orderID = jsono.getString("orderID");
        OrderObj orderObj = OrderMemory.get(orderID);

        rtnJson.put("resultCode", orderObj.getResultCode());
        rtnJson.put("orderID", orderObj.getOrderID());

        rtnJson.put("recommendOrderList", parseMapToJSONArray(orderObj.getRecommendDishMap()));
        rtnJson.put("myOrderList",  parseMapToJSONArray(orderObj.getMyDishMap()));
        // 是否需要实现价格计算
        rtnJson.put("totalPrice", orderObj.getPrice());
        rtnJson.put("discountSale", "0.00");

        rtnJson.put("orderTime", new Date().getTime());
        rtnJson.put("needTime", "150");
        rtnJson.put("status", "1");

        return JSON.toJSONString(rtnJson);
    }



    @ResponseBody
    @RequestMapping(value = "/placeOrder",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String placeOrder(@RequestBody String jsonString){
        System.out.println("placeOrder old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("placeOrder new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        JSONObject rtnJson = new JSONObject();
        String orderID = jsono.getString("orderID");
        OrderObj orderObj = OrderMemory.get(orderID);

        String result = orderService.placeOrder(jsono);

        rtnJson.put("resultCode", result); // orderObj.getResultCode()
        rtnJson.put("orderID", orderObj.getOrderID());

        return JSON.toJSONString(rtnJson);
    }

    @ResponseBody
    @RequestMapping(value = "/payOrder",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String payOrder(@RequestBody String jsonString){
        System.out.println("payOrder old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("payOrder new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        JSONObject rtnJson = new JSONObject();

        String orderID = jsono.getString("orderID");
        OrderObj orderObj = OrderMemory.get(orderID);

        String result = orderService.payOrder(jsono);

        rtnJson.put("amount", orderObj.getPrice());
        rtnJson.put("discount", "0.00");
        rtnJson.put("resultCode", result); // orderObj.getResultCode()
        rtnJson.put("orderID", orderObj.getOrderID());

        return JSON.toJSONString(rtnJson);
    }

    private JSONArray parseMapToJSONArray(HashMap map){
        JSONArray jsonArray = new JSONArray();
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry e = (Map.Entry) it.next();
            Object val = e.getValue();
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(val);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @ResponseBody
    @RequestMapping(value = "/detailvo",method = RequestMethod.POST)
    public Map<String,Object> getOrderdetailvo(@RequestBody String jsonString){
        Map<String,Object> result = Maps.newHashMap();

        System.out.println("payOrder old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("payOrder new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);
        System.out.println("orderID is:"+jsono.getString("orderID"));
        Long orderID = jsono.getLong("orderID");

        List<OrderDetailVo> list = orderService.findOrderVoByOrderId(orderID);
        result.put("list",list);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/addRestrict",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String restrictDetail(@RequestBody String jsonString){
        System.out.println("addRestrict old = " + jsonString);
        jsonString = StringUtils.decode(jsonString);
        System.out.println("addRestrict new = " + jsonString);
        JSONObject jsono = JSONObject.parseObject(jsonString);

        JSONObject rtnJson = new JSONObject();

        String result = orderService.restrictDetail(jsono);

        rtnJson.put("resultCode", result); // orderObj.getResultCode()

        return JSON.toJSONString(rtnJson);
    }

}
