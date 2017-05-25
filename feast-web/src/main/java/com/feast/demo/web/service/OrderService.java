package com.feast.demo.web.service;

import com.alibaba.dubbo.common.json.JSONObject;
import com.feast.demo.web.entity.MyDishObj;
import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.entity.RecommendDishObj;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


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
        orderObj.setMyDishMap(new HashMap<String, MyDishObj>());
        orderObj.setRecommendDishMap(new HashMap<String, RecommendDishObj>());
        System.out.println("Create order success...");

        return orderObj;
    }



    public OrderObj addMyDish(JSONObject jsono, OrderObj orderObj){

        System.out.println("Add Dish start...");
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("ID is:"+jsono.getString("ID"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        String id = jsono.getString("ID");

        HashMap<String, MyDishObj> myDishMap = orderObj.getMyDishMap();
        if(myDishMap.containsKey(id)){
            int tempAmout = Integer.valueOf(myDishMap.get(id).getAmount());
            myDishMap.get(id).setAmount(String.valueOf(tempAmout+1));
            // 暂时不做批量修改购物车数量，默认每次只添加一个菜
        }else{
            MyDishObj myDish = new MyDishObj();
            myDish.setDishID(id);
            // 以下数据为查询数据库所得，DEMO中可在配置文件中写死，读取配置文件获取
            myDish.setAmount("1");
            myDish.setDishImgUrl("http://www.baidu.com/");
            myDish.setDishName("宫保鸡丁");
            myDish.setDishNO("00001001");
            myDish.setExtraFlag("1");
            myDish.setPrice("18");
            myDish.setTodayPrice("16");
            myDish.setDishID(jsono.getString("ID"));

            myDishMap.put(id, myDish);
        }

        HashMap<String, RecommendDishObj> recommendDishMap = orderObj.getRecommendDishMap();
        RecommendDishObj recommendDish = new RecommendDishObj();
        // 以下数据为大数据平台所得，并关联数据库查询
        recommendDish.setAmount("1");
        recommendDish.setBeforeOrderTimes("3");
        recommendDish.setDishID("1002");
        recommendDish.setDishImgUrl("http://www.baidu.com/");
        recommendDish.setDishName("京酱肉丝");
        recommendDish.setDishNO("00001002");
        recommendDish.setExtraFlag("1");
        recommendDish.setTodayPrice("10");

        recommendDishMap.put(id, recommendDish);

        System.out.println("Add Dish success...");

        return orderObj;
    }

}
