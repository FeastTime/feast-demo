package com.feast.demo.coupon.dao;

import com.feast.demo.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface UserCouponDao extends PagingAndSortingRepository<UserCoupon,Long>{


    UserCoupon findByStoreIdAndCouponCode(Long storeId, String couponCode);

    ArrayList<UserCoupon> findByStoreIdAndIsUse(Long storeId, Integer isUse);

    @Query("select distinct uc.storeId from UserCoupon uc where uc.userId =?1")
    List<Long> findStoreIdByUserId(Long userId);

    ArrayList<UserCoupon> findByUserIdAndStoreIdAndIsUse(Long userId, Long storeId, Integer isUse);

    @Query("select uc from UserCoupon uc where uc.userId = ?1 and uc.storeId = ?2 and uc.isUse = ?3 and uc.couponValidity > ?4")
    ArrayList<UserCoupon> findIsUseAndCouponValidity(Long userId, Long storeId, int isuseUnused,Date date);

    @Query("select uc from UserCoupon uc where uc.userId = ?1 and uc.storeId = ?2 and uc.isUse = ?3 and uc.couponValidity < ?4")
    ArrayList<UserCoupon> findIsUseAndCouponInValidity(Long userId, Long storeId, int isuseUnused, Date date);
}
