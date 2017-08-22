package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.vo.OrderDetailVo;
import com.feast.demo.web.entity.MyDishObj;
import com.feast.demo.web.entity.OrderObj;
import com.feast.demo.web.entity.RecommendDishObj;
import com.google.common.collect.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        String storeid = jsono.getString("storeid") == null ? "0" : jsono.getString("storeid");
        String tableid = jsono.getString("tableid") == null ? "0" : jsono.getString("tableid");;
        String userid = jsono.getString("userid") == null ? "0" : jsono.getString("userid");;
        String currentTime = String.valueOf(System.currentTimeMillis());
        String mobileNo = jsono.getString("mobileNO");
        String orderID = jsono.getString("imei").substring(10)
                + mobileNo.substring(mobileNo.length()-4 , mobileNo.length())
                + currentTime.substring(currentTime.length()-9 , currentTime.length());

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
        orderObj.setPrice(new BigDecimal(0));


        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(orderID));
        orderInfo.setPayprice(new BigDecimal(0));
        orderInfo.setDiscountdetail("");
        orderInfo.setStatus(0);
        orderInfo.setStoreid(Long.valueOf(storeid));
        orderInfo.setTableid(Long.valueOf(tableid));
        orderInfo.setUserid(Long.valueOf(userid));

        // 保存数据库
        orderRemoteService.create(orderInfo);


        System.out.println("Create order success...");

        return orderObj;
    }



    public OrderObj addMyDish(JSONObject jsono, OrderObj orderObj){

        System.out.println("Add Dish start...");
        String storeID = jsono.getString("storeID") == null ? "0" : jsono.getString("storeID");
        String tableID = jsono.getString("tableID") == null ? "0" : jsono.getString("tableID");;
        String userID = jsono.getString("userID") == null ? "0" : jsono.getString("userID");;;
        String orderID = jsono.getString("orderID");
        String actualprice = jsono.getString("actualprice") == null ? "0" : jsono.getString("actualprice");

        String id = jsono.getString("ID");
        BigDecimal totalPrice = orderObj.getPrice().add(new BigDecimal(actualprice));
        orderObj.setPrice(totalPrice);
        // 加入购物车：更新订单表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(orderID));
        orderInfo.setPayprice(totalPrice);
        orderInfo.setDiscountdetail("");
        orderInfo.setStatus(0);
        orderInfo.setStoreid(Long.valueOf(storeID));
        orderInfo.setTableid(Long.valueOf(tableID));
        orderInfo.setUserid(Long.valueOf(userID));

        OrderDetail orderDetail = new OrderDetail();

        HashMap<String, MyDishObj> myDishMap = orderObj.getMyDishMap();
        if(myDishMap.containsKey(id)){
            orderDetail = orderRemoteService.findByOrderIdAndDishID(Long.valueOf(orderID), Long.valueOf(id));

            orderDetail.setAmount(orderDetail.getAmount()+1);
            orderDetail.setTotalprice(orderDetail.getTotalprice().add(new BigDecimal(actualprice)));

            // 暂时不做批量修改购物车数量，默认每次只添加一个菜
            orderRemoteService.update(orderInfo);
            orderRemoteService.update(orderDetail);

            MyDishObj myDish = new MyDishObj();
            myDish.setDishID(String.valueOf(orderDetail.getDishid()));
            // 以下数据为查询数据库所得，DEMO中可在配置文件中写死，读取配置文件获取
            myDish.setAmount(String.valueOf(orderDetail.getAmount()));
            myDish.setDishImgUrl(String.valueOf(orderDetail.getDishimgurl()));
            myDish.setDishName(String.valueOf(orderDetail.getDishname()));
            myDish.setDishNO(String.valueOf(orderDetail.getDishno()));
            myDish.setPrice(String.valueOf(orderDetail.getTotalprice()));

            myDish.setNeedTime(15);

            myDishMap.put(id, myDish);
        }else{
            // 根据菜品ID查询菜品信息《调用菜品查询实现》
            orderDetail.setDishid(Long.valueOf(id));
            orderDetail.setOrderid(Long.valueOf(orderID));
            orderDetail.setAmount(1L);
            orderDetail.setUserid(Long.valueOf(jsono.getString("userID")));
            System.out.println("dish id = " + orderDetail.getDishid());
            orderDetail.setTotalprice(new BigDecimal(actualprice));

            MyDishObj myDish = new MyDishObj();
            myDish.setDishID(String.valueOf(orderDetail.getDishid()));
            // 以下数据为查询数据库所得，DEMO中可在配置文件中写死，读取配置文件获取
            myDish.setAmount(String.valueOf(orderDetail.getAmount()));
            myDish.setDishImgUrl(String.valueOf(orderDetail.getDishimgurl()));
            myDish.setDishName(String.valueOf(orderDetail.getDishname()));
            myDish.setDishNO(String.valueOf(orderDetail.getDishno()));
            myDish.setPrice(String.valueOf(orderDetail.getTotalprice()));

            myDish.setNeedTime(15);

            myDishMap.put(id, myDish);

            orderRemoteService.update(orderInfo);
            orderRemoteService.create(orderDetail);
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

    public OrderObj removeMyDish(JSONObject jsono, OrderObj orderObj){

        String storeid = jsono.getString("storeid") == null ? "0" : jsono.getString("storeid");
        String tableid = jsono.getString("tableid") == null ? "0" : jsono.getString("tableid");;
        String userid = jsono.getString("userid") == null ? "0" : jsono.getString("userid");;
        String id = jsono.getString("ID");
        Long dishID = Long.valueOf(jsono.getString("ID"));
        Long orderID = Long.valueOf(jsono.getString("orderID"));
        String price = jsono.getString("price") == null ? "0" : jsono.getString("price");
        BigDecimal totalPrice = orderObj.getPrice().subtract(BigDecimal.valueOf(Long.valueOf(price)));
        orderObj.setPrice(totalPrice);

        // 加入购物车：更新订单表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(jsono.getString("orderID")));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail = orderRemoteService.findByOrderIdAndDishID(Long.valueOf(orderID), Long.valueOf(id));


        HashMap<String, MyDishObj> myDishMap = orderObj.getMyDishMap();

        if(myDishMap.containsKey(id)){
            if("1".equals(myDishMap.get(id).getAmount())){
                myDishMap.remove(id);
                orderInfo.setOrderid(orderID);
                // 订单表其他信息。。。。
                orderRemoteService.update(orderInfo);
                orderRemoteService.delete(orderID, dishID);
            }else{
                long amount = Long.valueOf(myDishMap.get(id).getAmount());
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
