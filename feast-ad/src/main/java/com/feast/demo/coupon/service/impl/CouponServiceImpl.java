package com.feast.demo.coupon.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.coupon.dao.CouponTemplateDao;
import com.feast.demo.coupon.dao.UserCouponDao;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.coupon.service.CouponService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ggke on 2017/8/26.
 */

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    @Autowired
    private UserCouponDao userCouponDao;

    public void createCouponTemplate(CouponTemplate coupon) {
        couponTemplateDao.save(coupon);
    }

    public void deleteCouponTemplate(Long couponTemplateId) {
        couponTemplateDao.delete(couponTemplateId);
    }

    public void updateUserCoupon(UserCoupon userCoupon) {
        userCouponDao.save(userCoupon);
    }


    public void updateCouponTemplate(CouponTemplate coupon) {
        CouponTemplate oldCouponTemplate = couponTemplateDao.findOne(coupon.getId());
        oldCouponTemplate.setCouponCount(coupon.getCouponCount());
        oldCouponTemplate.setCouponTitle(coupon.getCouponTitle());
        oldCouponTemplate.setCouponType(coupon.getCouponType());
        oldCouponTemplate.setCouponValidity(coupon.getCouponValidity());
        oldCouponTemplate.setPermissionsDescribed(coupon.getPermissionsDescribed());
        couponTemplateDao.save(oldCouponTemplate);
    }

    public ArrayList<UserCoupon> getUsedCoupon(Long storeId) {
        return userCouponDao.findByStoreIdAndIsUse(storeId,2);
    }

    public UserCoupon useCoupon(Long storeId, Long userId, String couponCode) {
        return userCouponDao.findByStoreIdAndUserIdAndCouponCode(storeId,userId,couponCode);
    }

    public ArrayList<CouponTemplate> queryCouponTemplateList(Long storeId) {
        return couponTemplateDao.findByStoreId(storeId);
    }

    public List<List<UserCoupon>> queryCouponList(Long userId,Integer flag) {
        System.out.println("pppp");
        List<List<UserCoupon>> userCoupons = Lists.newArrayList();
        System.out.println("oooo");
        List<Long> storeIdList = userCouponDao.findStoreIdByUserId(userId);
        for (Long storeId : storeIdList) {
            ArrayList<UserCoupon> couponList = userCouponDao.findByUserIdAndStoreId(userId,storeId);
            ArrayList<UserCoupon> couponValidList = Lists.newArrayList();
            ArrayList<UserCoupon> couponInValidList = Lists.newArrayList();
            Long date = new Date().getTime();
            for (UserCoupon userCoupon:couponList) {
                if(userCoupon.getCouponValidity().getTime()>date){
                    couponValidList.add(userCoupon);
                }else{
                    couponInValidList.add(userCoupon);
                }
            }
            if(flag==0){
                couponList = couponValidList;
            }else{
                couponList = couponInValidList;
            }
            userCoupons.add(couponList);
        }
        System.out.println(userCoupons);
        return userCoupons;
    }

    public Iterable<CouponTemplate> findAllCouponTemplate() {
        return couponTemplateDao.findAll();
    }

    public UserCoupon createUserCoupon(UserCoupon userCoupon) {
        return userCouponDao.save(userCoupon);
    }

}
