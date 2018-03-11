package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.web.service.CouponService;
import com.feast.demo.web.service.StoreService;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by ggke on 2017/8/26.
 */
@RestController
@RequestMapping(value = "/coupon")
public class CouponController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private CouponService couponService;

    @Autowired
    private StoreService storeService;

    //查询优惠券列表信息
    @RequestMapping(value = "/queryCouponList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String queryCouponList(HttpServletRequest servletRequest){

        logger.info("查询优惠券列表信息");
        Map<String,Object> result = Maps.newHashMap();
        String resultMsg = "";
        Byte resultCode = 1;

        HashMap<String,ArrayList<UserCoupon>> couponMap = null;
        List<Map<String,Object>> couponList = Lists.newArrayList();
        try{
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono = JSON.parseObject(params);
            Integer flag = jsono.getInteger("flag");
            Long userId = jsono.getLong("userId");


            couponMap = couponService.queryCouponList(userId,flag);

            if(couponMap==null&&flag==1){
                resultMsg = "您没有未过期未使用的优惠券";
            }else if(couponMap==null&&flag==3){
                resultMsg = "您没有已过期未使用优惠券";
            }else if(couponMap==null&&flag==2){
                resultMsg = "您没有已使用的优惠券";
            }else if(couponMap!=null){
                Set<String> storeIds = couponMap.keySet();
                for (String storeId : storeIds) {
                    Map<String,Object> map = Maps.newHashMap();
                    map.put("storeName",couponMap.get(storeId).get(0).getStoreName());
                    map.put("dataList",couponMap.get(storeId));
                    couponList.add(map);
                }
                resultMsg = "查询优惠券列表成功";
            }

            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询优惠券列表失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        result.put("couponList",couponList);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/createCouponTemplate",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String createCouponTemplate(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = new HashMap<>();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            CouponTemplate coupon = JSONObject.parseObject(params,CouponTemplate.class);
            Date date = new Date();
            coupon.setCreateTime(date);
            coupon.setLastModified(date);
            couponService.createCouponTemplate(coupon);
            resultCode = 0;
            resultMsg = "创建优惠券模板成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "创建优惠券模板失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/deleteCouponTemplate",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String deleteCouponTemplate(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono = JSON.parseObject(params);
            Long id = jsono.getLong("id");
            couponService.deleteCouponTemplate(id);
            resultCode = 0;
            resultMsg = "删除优惠券模板成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "删除优惠券模板失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/useCoupon",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String useCoupon(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        Byte isSuccess = 1;
        try{
            result = new HashMap<>();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono  = JSON.parseObject(params);
            Long storeId = jsono.getLong("storeId");
            String couponCode = jsono.getString("couponCode");
            UserCoupon userCoupon = couponService.useCoupon(storeId,couponCode);
            if(userCoupon==null){
                resultMsg = "优惠券不存在";
            }else{
                if(userCoupon.getCouponValidity().getTime()<new Date().getTime()){
                    resultMsg = "优惠券已经过期";
                }else if(userCoupon.getIsUse()==UserCoupon.ISUSE_USED){
                    resultMsg = "优惠券已经使用过";
                }else{
                    userCoupon.setIsUse(UserCoupon.ISUSE_USED);
                    couponService.updateUserCoupon(userCoupon);
                    isSuccess = 0;
                    resultMsg = "优惠券可用";
                }
                result.put("coupon",userCoupon);
            }
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultCode = 1;
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        result.put("isSuccess",isSuccess);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/updateCouponTemplate",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String updateCouponTemplate(HttpServletRequest servletRequest){
            Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = new HashMap<>();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            CouponTemplate coupon = JSONObject.parseObject(params,CouponTemplate.class);
            coupon.setLastModified(new Date());
            couponService.updateCouponTemplate(coupon);
            resultCode = 0;
            resultMsg = "修改优惠券模板成功";
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "修改优惠券模板失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/queryCouponTemplateList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String queryCouponTemplateList(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<CouponTemplate> couponTemplateList = null;
        try{
            result = Maps.newHashMap();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono = JSON.parseObject(params);
            Long storeId = jsono.getLong("storeId");
            couponTemplateList = couponService.queryCouponTemplateList(storeId);
            resultMsg = "查询优惠券列表成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询优惠券列表失败";
        }
        result.put("resultMsg",resultMsg);
        result.put("resultCode",resultCode);
        result.put("couponTemplateList",couponTemplateList);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/getUsedCoupon",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String getUsedCoupon(HttpServletRequest servletRequest){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<UserCoupon> couponList = null;
        try{
            result = new HashMap<>();
            String params = (String) servletRequest.getAttribute("json");
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono = JSON.parseObject(params);
            Long storeId = jsono.getLong("storeId");
            couponList = couponService.getUsedCoupon(storeId);
            resultMsg = "查询使用过的优惠券列表成功";
            resultCode = 0;
        }catch (Exception e){
            e.printStackTrace();
            resultMsg = "查询使用过的优惠券安列表失败";
        }
        result.put("resultCode",resultCode);
        result.put("resultMsg",resultMsg);
        result.put("couponList",couponList);
        return JSON.toJSONString(result);
    }
}
