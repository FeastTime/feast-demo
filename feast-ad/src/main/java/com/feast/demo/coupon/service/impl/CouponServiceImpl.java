package com.feast.demo.coupon.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.coupon.dao.CouponDao;
import com.feast.demo.coupon.entity.Coupon;
import com.feast.demo.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ggke on 2017/8/26.
 */

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    public List<Coupon> findAll() {
        return (List<Coupon>) couponDao.findAll();
    }
}
