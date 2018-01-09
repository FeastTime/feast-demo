package com.feast.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.web.service.CouponService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ggke on 2017/8/26.
 */
@RestController
@RequestMapping(value = "/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @RequestMapping(value = "/list/",method = RequestMethod.POST)
    public Map<String,Object> couponList(@RequestBody String params){
        Map<String,Object> result = Maps.newHashMap();
        System.out.println("转之前"+params);
        params = StringUtils.decode(params);
        System.out.println("转之后"+params);

        List<CouponTemplate> list = couponService.findAllCoupon();
        result.put("data",list);
        result.put("resultCode",1);
        return result;
    }

    @RequestMapping(value="/createCouponTemplate")
    public String createCouponTemplate(@RequestBody String params){

        Map<Object,Object> result = null;
        try{
            result = new HashMap<>();
            params = StringUtils.decode(params);
            CouponTemplate coupon = JSONObject.parseObject(params,CouponTemplate.class);
            coupon.setCreateTime(new Date());
            couponService.createCouponTemplate(coupon);
            result.put("resultCode","0");
            result.put("resultMsg","创建优惠券模板成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put("resultCode","1");
            result.put("resultMsg","创建优惠券模板失败");
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value="/deleteCouponTemplate")
    public String deleteCouponTemplate(@RequestBody String params){
        Map<Object,Object> result = null;
        try{
            params = StringUtils.decode(params);
            JSONObject jsono = JSON.parseObject(params);
            Long couponTemplateId = jsono.getLong("couponTemplateId");
            couponService.deleteCouponTemplate(couponTemplateId);
            result.put("resultCode","0");
            result.put("resultMsg","删除优惠券模板成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put("resultCode","1");
            result.put("resultMsg","删除优惠券模板失败");
        }
        return JSON.toJSONString(result);
    }

}
