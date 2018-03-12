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

import java.util.*;

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
        return couponTemplateDao.findByStoreIdOrderByLastModifiedDesc(storeId);
    }

    public HashMap<String,ArrayList<UserCoupon>> queryCouponList(Long userId, Integer flag) {

        HashMap<String,ArrayList<UserCoupon>> userCouponMap = Maps.newHashMap();

        ArrayList<UserCoupon> couponList = null;
        //1:未使用(未使用 && 未过期)
        if (flag == 1) {
            couponList = userCouponDao.findByIsUseAndCouponValidityOrderByStoreIdAndTakeTime(userId,UserCoupon.ISUSE_UNUSED, new Date());
        } else if (flag == 2) {
            couponList = userCouponDao.findByUserIdAndIsUseOrderByStoreIdAndTakeTime(userId,UserCoupon.ISUSE_USED);
            //3:已过期(未使用 && 过期)
        } else if (flag == 3) {
            couponList = userCouponDao.findByIsUseAndCouponInValidityOrderByStoreIdAndTakeTime(userId, UserCoupon.ISUSE_UNUSED, new Date());
        }

        for (UserCoupon userCoupon : couponList) {
            String storeId = userCoupon.getStoreId()+"";
            if(!userCouponMap.containsKey(storeId)){
                ArrayList<UserCoupon> coupons = Lists.newArrayList();
                coupons.add(userCoupon);
                userCouponMap.put(storeId+"",coupons);
            }else{
                userCouponMap.get(storeId).add(userCoupon);
            }

        }

        return userCouponMap;
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

    public Integer findTakePeopleNumber(Long storeId) {
        return userCouponDao.findTakePeopleNumberByStoreIdGroupByUserId(storeId);
    }

    public Integer findTakeCouponNumber(Long storeId) {
        return userCouponDao.findTakeCouponNumberByStoreId(storeId);
    }

    public Integer findUsePeopleNumber(Long storeId) {
        return userCouponDao.findUsePeopleNumberByStoreIdAndIsUse(storeId);
    }

    public Integer findUseCouponNumber(Long storeId) {
        return userCouponDao.findUseCouponNumberByStoreIdAndIsUse(storeId);
    }


}
