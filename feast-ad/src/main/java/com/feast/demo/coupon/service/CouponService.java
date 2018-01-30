package com.feast.demo.coupon.service;

import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggke on 2017/8/26.
 */

public interface CouponService {

    public void createCouponTemplate(CouponTemplate coupon);

    public void deleteCouponTemplate(Long couponTemplateId);

    public void updateUserCoupon(UserCoupon userCoupon);

    public void updateCouponTemplate(CouponTemplate coupon);

    public ArrayList<UserCoupon> getUsedCoupon(Long storeId);

    public UserCoupon useCoupon(Long storeId, Long userId, String couponCode);

    public ArrayList<CouponTemplate> queryCouponTemplateList(Long storeId);

    public List<List<UserCoupon>> queryCouponList(Long userId, Integer flag);

    public Iterable<CouponTemplate> findAllCouponTemplate();

    public UserCoupon saveUserCoupon(UserCoupon userCoupon);

    public CouponTemplate findCouponTemplateById(Long id);
}
