package com.feast.demo.web.controller;

import com.feast.demo.coupon.entity.Coupon;
import com.feast.demo.web.service.CouponService;
import com.feast.demo.web.util.StringUtils;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

        List<Coupon> list = couponService.findAllCoupon();
        result.put("data",list);
        result.put("resultCode",1);
        return result;
    }
}
