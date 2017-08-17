package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.vo.OrderDetailVo;
import com.feast.demo.web.entity.MyDishObj;
import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.entity.RecommendDishObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by wpp on 2017/5/9.
 */
@Service
public class OrderService {

    @Autowired
    private com.feast.demo.order.service.OrderService orderRemoteService;

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
        String orderID = jsono.getString("imei").substring(10)
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


        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(orderID));

        System.out.println(orderID);
        System.out.println(Long.valueOf(orderID));


        // 保存数据库
        orderRemoteService.create(orderInfo);


        System.out.println("Create order success...");

        return orderObj;
    }



    public OrderObj addMyDish(JSONObject jsono, OrderObj orderObj){

        System.out.println("Add Dish start...");
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        // 菜品ID
        System.out.println("ID is:"+jsono.getString("ID"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        String id = jsono.getString("ID");
        // 加入购物车：更新订单表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(jsono.getString("orderID")));
        // 其他信息。。。。

        OrderDetail orderDetail = new OrderDetail();

        HashMap<String, MyDishObj> myDishMap = orderObj.getMyDishMap();
        if(myDishMap.containsKey(id)){
            int tempAmout = Integer.valueOf(myDishMap.get(id).getAmount());
            myDishMap.get(id).setAmount(String.valueOf(tempAmout+1));
            orderDetail.setAmount(tempAmout+1);
            // 暂时不做批量修改购物车数量，默认每次只添加一个菜
        }else{
            // 根据菜品ID查询菜品信息《调用菜品查询实现》
            orderDetail.setDishid(Long.valueOf(jsono.getString("ID")));
            orderDetail.setOrderid(Long.valueOf(jsono.getString("orderID")));
            orderDetail.setAmount(1);

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
            myDish.setStartTime(new Date().getTime());
            myDish.setNeedTime(15);
            myDish.setState((byte)'0');
            myDish.setDishID(jsono.getString("ID"));

            myDishMap.put(id, myDish);
        }

        orderRemoteService.update(orderInfo);
        orderRemoteService.update(orderDetail);

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

    public OrderObj removeMyDish(JSONObject jsono, OrderObj orderObj){

        System.out.println("remove Dish start...");
        System.out.println("androidID is:"+jsono.getString("androidID"));
        System.out.println("imei is:"+jsono.getString("imei"));
        System.out.println("ipv4 is:"+jsono.getString("ipv4"));
        System.out.println("mac is:"+jsono.getString("mac"));
        System.out.println("ID is:"+jsono.getString("ID"));
        System.out.println("orderID is:"+jsono.getString("orderID"));

        String id = jsono.getString("ID");
        Long dishID = Long.valueOf(jsono.getString("ID"));
        Long orderID = Long.valueOf(jsono.getString("orderID"));


        // 删除菜品：更新订单表/订单明细表
        OrderInfo orderInfo = new OrderInfo();
        OrderDetail orderDetail = new OrderDetail();


        HashMap<String, MyDishObj> myDishMap = orderObj.getMyDishMap();

        if(myDishMap.containsKey(id)){
            if("1".equals(myDishMap.get(id).getAmount())){
                myDishMap.remove(id);
                orderInfo.setOrderid(orderID);
                // 订单表其他信息。。。。
                orderRemoteService.update(orderInfo);
                orderRemoteService.delete(orderID, dishID);
            }else{
                int amount = Integer.valueOf(myDishMap.get(id).getAmount());
                myDishMap.get(id).setAmount(String.valueOf(amount-1));
                orderInfo.setOrderid(orderID);
                // 订单表其他信息。。。。
                orderDetail.setDishid(dishID);
                orderDetail.setOrderid(orderID);
                orderDetail.setAmount(amount-1);

                orderRemoteService.update(orderInfo);
                orderRemoteService.update(orderDetail);

            }

        }else{
            // 购物车没有此菜品暂时不做处理
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

        System.out.println("remove Dish success...");

        return orderObj;
    }

    public List<OrderDetailVo> findOrderVoByOrderId(Long orderId){
        return orderRemoteService.findVoByOrderId(orderId);
    }

}
