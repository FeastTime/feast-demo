package com.feast.demo.web.service;

import com.feast.demo.coupon.entity.Coupon;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ggke on 2017/8/26.
 */

@org.springframework.stereotype.Service
public class CouponService {

    @Resource
    private com.feast.demo.coupon.service.CouponService couponRemoteService;

    public List<Coupon> findAllCoupon(){
        return couponRemoteService.findAll();
    }

}
