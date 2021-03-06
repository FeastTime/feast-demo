package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.store.entity.Store;
import com.feast.demo.table.entity.TableInfo;
import com.feast.demo.user.entity.User;
import com.feast.demo.web.entity.CouponBean;
import com.feast.demo.web.service.CouponService;
import com.feast.demo.web.service.StoreService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    Logger logger = Logger.getLogger(this.getClass().getName());
    /**
      * 查询商家详细信息
     方法名 getStoreInfo
     */

    private static Map<Long,List<Object>> turnTableMap = Maps.newHashMap();

    private static Map<Long,List<Integer>> chanceMap = Maps.newHashMap();

    @Autowired
    private StoreService storeService;

    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "/getStoreInfo",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String getStoreInfo(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono = JSON.parseObject(params);
            Long storeId = jsono.getLong("storeId");
            Store store = storeService.getStoreInfo(storeId);
            resultCode = 0;
            resultMsg = "查询商家详细信息成功";
            result.put("storeId",store.getStoreId());
            result.put("name",store.getStoreName());
            result.put("phone",store.getPhone());
            result.put("locate",store.getLocate());
            result.put("longitude",store.getLongitude());
            result.put("latitude",store.getLatitude());
            result.put("storePicture",store.getStorePicture());
            result.put("storeIcon",store.getStoreIcon());
        }catch(Exception e){
            e.printStackTrace();
            resultMsg = "查询商家详细信息失败";
        }
        result.put("resultCode",resultCode);
        result.put("reslutMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/getStoreInfoList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String getStoreInfoList(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsonObject = JSONObject.parseObject(params);
            JSONArray storeId = jsonObject.getJSONArray("storeId");
            ArrayList<Long> storeIds = (ArrayList<Long>)JSONArray.parseArray(JSON.toJSONString(storeId),Long.class);
            ArrayList<Store> stores = storeService.getStoreInfoList(storeIds);
            resultCode = 0;
            resultMsg = "查询商家详细信息列表成功";
            result.put("stores",stores);
        }catch(Exception e){
            e.printStackTrace();
            resultMsg = "查询商家详细信息列表失败";
        }
        result.put("resultCode",resultCode);
        result.put("reslutMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/queryHadVisitUser",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String queryHadVisitUser(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Integer resultCode = 1;
        ArrayList<User> userList = null;
        try{
            result = Maps.newHashMap();
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject obj = JSONObject.parseObject(text);
            Long storeId = obj.getLong("storeId");
            userList = storeService.queryHadVisitUser(storeId);
            resultCode = 0;
            resultMsg = "查询历史用户成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询历史用户失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        result.put("userList",userList);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/sendCoupon",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String sendCoupon(HttpServletRequest servletRequest){
        String resultMsg;
        Integer resultCode = 1;
        try{
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsonObject = JSONObject.parseObject(text);
            Long storeId = jsonObject.getLong("storeId");
            JSONArray couponArray = jsonObject.getJSONArray("couponBeans");
            List<CouponBean> couponBeans = JSONArray.parseArray(JSON.toJSONString(couponArray), CouponBean.class);
            int sum = 0;
            OUT:while(true){
                sum++;
                for (int i=0;i<couponBeans.size();i++) {
                    if(turnTableMap.get(storeId).size()==6){
                        break OUT;
                    }
                    CouponBean couponBean = couponBeans.get(i);
                    Long couponTemplateId = couponBean.getCouponTemplateId();
                    CouponTemplate couponTemplate= couponService.findCouponTemplateById(couponTemplateId);
                    turnTableMap.computeIfAbsent(storeId,k-> Lists.newArrayList());
                    turnTableMap.get(storeId).add(couponTemplate);
                    if(sum==0){
                        int count = couponBean.getCount();
                        chanceMap.computeIfAbsent(storeId,K->Lists.newArrayList());
                        for (int j=0;j<count;j++){
                            chanceMap.get(storeId).add(i);
                        }
                    }
                }
            }
            turnTableMap.get(storeId).add("未中奖");
            resultCode = 0;
            resultMsg = "发送红包成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "发送红包失败";
        }
        Map<String,Object> result = Maps.newHashMap();
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/sendTable",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    public String sendTable(HttpServletRequest servletRequest){
        String resultMsg;
        Integer resultCode = 1;
        try{
            String text = (String) servletRequest.getAttribute("json");
            text = StringUtils.decode(text);
            logger.info(text);
            JSONObject jsonObject = JSONObject.parseObject(text);
            Long storeId = jsonObject.getLong("storeId");
            TableInfo tableInfo = JSON.parseObject(text, TableInfo.class);
            turnTableMap.computeIfAbsent(storeId,k-> Lists.newArrayList());
            if(tableInfo!=null){
                turnTableMap.get(storeId).add(tableInfo);
                chanceMap.get(storeId).add(7,8);

            }else{
                turnTableMap.get(storeId).add(null);
                chanceMap.get(storeId).add(null);
            }
            resultCode = 0;
            resultMsg = "发送桌位成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "发送桌位失败";
        }
        Map<String,Object> result = Maps.newHashMap();
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }
}

