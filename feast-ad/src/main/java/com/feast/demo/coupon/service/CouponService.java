package com.feast.demo.coupon.service;

import com.feast.demo.coupon.entity.Coupon;

import java.util.List;

/**
 * Created by ggke on 2017/8/26.
 */
public interface CouponService {

    public List<Coupon> findAll();
}
