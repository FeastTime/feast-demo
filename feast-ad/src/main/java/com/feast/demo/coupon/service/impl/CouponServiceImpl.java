package com.feast.demo.coupon.service.impl;

import com.feast.demo.coupon.dao.CouponTemplateDao;
import com.feast.demo.coupon.dao.UserCouponDao;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import com.feast.demo.coupon.service.CouponService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public UserCoupon useCoupon(Long storeId,  String couponCode) {
        return userCouponDao.findByStoreIdAndCouponCode(storeId,couponCode);
    }

    public ArrayList<CouponTemplate> queryCouponTemplateList(Long storeId) {
        return couponTemplateDao.findByStoreId(storeId);
    }

    public Map<Long,List<UserCoupon>> queryCouponList(Long userId, Integer flag, List<Long> storeIds,Integer isUse) {
        Map<Long,List<UserCoupon>> userCoupons = Maps.newHashMap();
        for (Long storeId : storeIds) {
            ArrayList<UserCoupon> couponList = userCouponDao.findByUserIdAndStoreIdAndIsUse(userId,storeId,isUse);
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
            userCoupons.put(storeId,couponList);
        }
        System.out.println(userCoupons);
        return userCoupons;
    }

    public Iterable<CouponTemplate> findAllCouponTemplate() {
        return couponTemplateDao.findAll();
    }


    public UserCoupon saveUserCoupon(UserCoupon userCoupon) {
        return userCouponDao.save(userCoupon);
    }

    public CouponTemplate findCouponTemplateById(Long id) {
        return couponTemplateDao.findOne(id);
    }

    public List<Long> findStoreIdByUserId(Long userId) {
        return userCouponDao.findStoreIdByUserId(userId);
    }

}
