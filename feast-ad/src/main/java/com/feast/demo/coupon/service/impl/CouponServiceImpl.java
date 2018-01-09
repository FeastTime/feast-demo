package com.feast.demo.coupon.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.coupon.dao.CouponTemplateDao;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ggke on 2017/8/26.
 */

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    public List<CouponTemplate> findAll() {
        return (List<CouponTemplate>) couponTemplateDao.findAll();
    }

    public void createCouponTemplate(CouponTemplate coupon) {
        couponTemplateDao.save(coupon);
    }

    public void deleteCouponTemplate(Long couponTemplateId) {
        couponTemplateDao.delete(couponTemplateId);
    }

}
