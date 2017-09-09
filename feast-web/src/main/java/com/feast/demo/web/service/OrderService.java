package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONObject;
import com.feast.demo.menu.vo.MenuVo;
import com.feast.demo.order.entity.OrderDetail;
import com.feast.demo.order.entity.OrderInfo;
import com.feast.demo.order.vo.OrderDetailVo;
import com.feast.demo.web.entity.*;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    private com.feast.demo.menu.service.MenuService menuRemoteService;

    public OrderObj getCreatedOrder(JSONObject jsono){

        System.out.println("Create order start...");

        String storeid = jsono.getString("storeId") == null ? "0" : jsono.getString("storeId");
        String tableid = jsono.getString("tableId") == null ? "0" : jsono.getString("tableId");
        String userid = jsono.getString("userId") == null ? "0" : jsono.getString("userId");
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
        orderObj.setPrice(new BigDecimal(0));
        HashMap<String, RecommendDishObj> recommendDishMap = findRecommendPrdByStoreIdAndHomeFlag(jsono);
        orderObj.setRecommendDishMap(recommendDishMap);

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

    public OrderObj removeMyDish(JSONObject jsono, OrderObj orderObj){

        String storeid = jsono.getString("storeId") == null ? "0" : jsono.getString("storeId");
        String tableid = jsono.getString("tableId") == null ? "0" : jsono.getString("tableId");;
        String userid = jsono.getString("userId") == null ? "0" : jsono.getString("userId");;
        String id = jsono.getString("ID");
        Long dishID = Long.valueOf(jsono.getString("dishId"));
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

        HashMap<String, RecommendDishObj> recommendDishMap = findRecommendPrdByStoreIdAndHomeFlag(jsono);
        orderObj.setRecommendDishMap(recommendDishMap);

        System.out.println("remove Dish success...");

        return orderObj;
    }

    public OrderObj addMyDish(JSONObject jsono, OrderObj orderObj){


        // 老马提供根据菜品ID查询菜品信息的接口


        System.out.println("Add Dish start...");
        String storeID = jsono.getString("storeId") == null ? "0" : jsono.getString("storeId");
        String tableID = jsono.getString("tableId") == null ? "0" : jsono.getString("tableId");;
        String userID = jsono.getString("userId") == null ? "0" : jsono.getString("userId");;;
        String orderID = jsono.getString("orderID");
        String actualprice = jsono.getString("actualprice") == null ? "0" : jsono.getString("actualprice");

        String id = jsono.getString("dishId");
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
            orderDetail.setUserid(Long.valueOf(jsono.getString("userId")));
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

        HashMap<String, RecommendDishObj> recommendDishMap = findRecommendPrdByStoreIdAndHomeFlag(jsono);
        orderObj.setRecommendDishMap(recommendDishMap);

        System.out.println("Add Dish success...");

        return orderObj;
    }

    public String placeOrder(JSONObject jsono){
        System.out.println("placeOrder start...");
        String result = "0";
        String orderID = jsono.getString("orderID");
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(orderID));
        orderInfo.setStatus(1);
        orderRemoteService.update(orderInfo);
        System.out.println("placeOrder success...");
        return result;
    }

    public String payOrder(JSONObject jsono){
        System.out.println("payOrder start...");
        String result = "0";
        String orderID = jsono.getString("orderID");
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(orderID));
        orderInfo.setStatus(2);
        orderRemoteService.update(orderInfo);
        System.out.println("payOrder success...");
        return result;
    }

    public List<OrderDetailVo> findOrderVoByOrderId(Long orderId){
        return orderRemoteService.findVoByOrderId(orderId);
    }


    public HashMap<String, RecommendDishObj> findRecommendPrdByStoreIdAndHomeFlag(JSONObject jsonObj) {
        System.out.println("storeId is:" + jsonObj.getString("storeId"));
        System.out.println("isHomePage is:" + jsonObj.getString("isHomePage"));

        HashMap<String, RecommendDishObj> recommendDishMap = new HashMap<String, RecommendDishObj>();
        try {
            List<MenuVo> list = menuRemoteService.findRecommendPrdByStoreIdAndHomeFlag(jsonObj);

            if (list != null && list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    RecommendDishObj recommendDish = new RecommendDishObj();
                    MenuVo menuVo = (MenuVo) list.get(i);
                    recommendDish.setDishId(menuVo.getDishId());
                    recommendDish.setDishNo(menuVo.getDishNo());
                    recommendDish.setDishImgUrl(menuVo.getDishImgUrl());
                    recommendDish.setTvUrl(menuVo.getTvUrl());
                    recommendDish.setHotFlag(menuVo.getHotFlag());
                    recommendDish.setMaterialFlag(menuVo.getMaterialFlag());
                    recommendDish.setTitleAdImgUrl(menuVo.getTitleAdImgUrl());
                    recommendDish.setTitleAdUrl(menuVo.getTitleAdUrl());
                    recommendDish.setEatTimes(menuVo.getEatTimes());
                    recommendDish.setDishName(StringUtils.encode(menuVo.getDishName()));
                    recommendDish.setDetail(StringUtils.encode(menuVo.getDetail()));
                    recommendDish.setDiscountsTime(menuVo.getDiscountsTime());
                    recommendDish.setCost(String.valueOf(menuVo.getCost()));
                    recommendDish.setPrice(String.valueOf(menuVo.getPrice()));
                    recommendDish.setSales(menuVo.getSales());
                    recommendDish.setWaitTime(menuVo.getWaitTime());
                    recommendDish.setExponent(StringUtils.encode("钠含量30克，热量50卡"));
                    recommendDish.setStarLevel(menuVo.getStarLevel());
                    recommendDish.setPungencyDegree(menuVo.getPungencyDegree());
                    recommendDish.setTmpId(menuVo.getTmpId());
                    recommendDish.setPageId(menuVo.getPageId());
                    recommendDishMap.put(menuVo.getDishId(), recommendDish);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return recommendDishMap;
    }

    public String restrictDetail(JSONObject jsono){
        System.out.println("Add restrictDetail start...");
        String result = "0";
        String storeID = jsono.getString("storeId") == null ? "0" : jsono.getString("storeId");
        String tableID = jsono.getString("tableId") == null ? "0" : jsono.getString("tableId");;
        String userID = jsono.getString("userId") == null ? "0" : jsono.getString("userId");;;
        String orderID = jsono.getString("orderID");
        String restrictDetail = jsono.getString("restrictDetail") == null ? "" : jsono.getString("restrictDetail");;;

        String id = jsono.getString("dishId");
        // 加入购物车：更新订单表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderid(Long.valueOf(orderID));
        orderInfo.setStoreid(Long.valueOf(storeID));
        orderInfo.setTableid(Long.valueOf(tableID));
        orderInfo.setUserid(Long.valueOf(userID));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail = orderRemoteService.findByOrderIdAndDishID(Long.valueOf(orderID), Long.valueOf(id));
        orderDetail.setRestrictdetail(restrictDetail);

        orderRemoteService.update(orderInfo);
        orderRemoteService.update(orderDetail);

        System.out.println("Add restrictDetail success...");

        return result;
    }

}
