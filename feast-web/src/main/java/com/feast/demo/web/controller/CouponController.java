package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.web.service.CouponService;
import com.feast.demo.web.service.UserService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by ggke on 2017/8/26.
 */
@RestController
@RequestMapping(value = "/coupon")
public class CouponController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private CouponService couponService;

    @Autowired
    private UserService userService;

    //查询优惠券列表信息
    @RequestMapping(value = "/queryCouponList",method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String queryCouponList(@RequestBody String params){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<UserCoupon> couponList = null;
        try{
            result = Maps.newHashMap();
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono = JSON.parseObject(params);
            Integer flag = jsono.getInteger("flag");
            Long userId = jsono.getLong("userId");
            couponList = couponService.queryCouponList(userId,flag);
            resultCode = 0;
            resultMsg = "查询优惠券列表成功";
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
    public String createCouponTemplate(@RequestBody String params){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = new HashMap<>();
            params = StringUtils.decode(params);
            logger.info(params);
            CouponTemplate coupon = JSONObject.parseObject(params,CouponTemplate.class);
            coupon.setCreateTime(new Date());
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
    public String deleteCouponTemplate(@RequestBody String params){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = Maps.newHashMap();
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
    public String useCoupon(@RequestBody String params){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        Byte isSuccess = 1;
        try{
            result = new HashMap<>();
            params = StringUtils.decode(params);
            logger.info(params);
            JSONObject jsono  = JSON.parseObject(params);
            Long storeId = jsono.getLong("storeId");
            Long userId = jsono.getLong("userId");
            String couponCode = jsono.getString("couponCode");
            UserCoupon userCoupon = couponService.useCoupon(storeId,userId,couponCode);
            if(userCoupon==null){
                resultMsg = "优惠券不存在";
            }else{
                if(userCoupon.getCouponValidity().getTime()<new Date().getTime()){
                    resultMsg = "优惠券已经过期";
                }else if(userCoupon.getIsUse()==1){
                    resultMsg = "优惠券已经使用过";
                }else{
                    userCoupon.setIsUse(1);
                    couponService.updateUserCoupon(userCoupon);
                    isSuccess = 0;
                    resultMsg = "优惠券可用";
                }
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
    public String updateCouponTemplate(@RequestBody String params){
            Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        try{
            result = new HashMap<>();
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
    public String queryCouponTemplateList(@RequestBody String params){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<CouponTemplate> couponTemplateList = null;
        try{
            result = Maps.newHashMap();
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
    public String getUsedCoupon(@RequestBody String params){
        Map<String,Object> result = null;
        String resultMsg = "";
        Byte resultCode = 1;
        ArrayList<UserCoupon> couponList = null;
        try{
            result = new HashMap<>();
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
