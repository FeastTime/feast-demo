package com.feast.demo.coupon.dao;

import com.feast.demo.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.ArrayList;
import java.util.Date;


public interface UserCouponDao extends PagingAndSortingRepository<UserCoupon,Long>{


    UserCoupon findByStoreIdAndCouponCode(Long storeId, String couponCode);

    ArrayList<UserCoupon> findByStoreIdAndIsUse(Long storeId, Integer isUse);

    @Query("select uc from UserCoupon uc where uc.userId = ?1 and uc.isUse = ?2 and uc.couponValidity > ?3 order by uc.storeId asc,uc.takeTime desc")
    ArrayList<UserCoupon> findByIsUseAndCouponValidityOrderByStoreIdAndTakeTime(Long userId, int isuseUnused, Date date);

    @Query("select uc from UserCoupon uc where uc.userId = ?1 and uc.isUse = ?2 order by uc.storeId asc,uc.takeTime desc")
    ArrayList<UserCoupon> findByUserIdAndIsUseOrderByStoreIdAndTakeTime(Long userId, int isuseUsed);

    @Query("select uc from UserCoupon uc where uc.userId = ?1 and uc.isUse = ?2 and uc.couponValidity < ?3 order by uc.storeId asc,uc.takeTime desc")
    ArrayList<UserCoupon> findByIsUseAndCouponInValidityOrderByStoreIdAndTakeTime(Long userId, int isuseUnused, Date date);

    @Query("select count(uc.couponId) from UserCoupon uc where uc.storeId = ?1")
    Integer findTakeCouponNumberByStoreId(Long storeId);

    @Query("select count(distinct uc.userId) from UserCoupon uc where uc.storeId = ?1")
    Integer findTakePeopleNumberByStoreIdGroupByUserId(Long storeId);

    @Query("select count(distinct uc.userId) from UserCoupon uc where uc.storeId = ?1 and uc.isUse = 2")
    Integer findUsePeopleNumberByStoreIdAndIsUse(Long storeId);

    @Query("select count(uc.couponId) from UserCoupon uc where uc.storeId = ?1 and uc.isUse =2")
    Integer findUseCouponNumberByStoreIdAndIsUse(Long storeId);
}
