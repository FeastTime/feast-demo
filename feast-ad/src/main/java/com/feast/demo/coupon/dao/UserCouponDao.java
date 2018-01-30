package com.feast.demo.coupon.dao;

import com.feast.demo.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserCouponDao extends PagingAndSortingRepository<UserCoupon,Long>{

    @Query("select uc from UserCoupon uc where uc.userId = ?1 and uc.storeId = ?2")
    ArrayList<UserCoupon> findByUserIdAndStoreId(Long userId, Long storeId);

    UserCoupon findByStoreIdAndUserIdAndCouponCode(Long storeId, Long userId, String couponCode);

    ArrayList<UserCoupon> findByStoreIdAndIsUse(Long storeId, Integer isUse);

    @Query("select distinct uc.storeId from UserCoupon uc where uc.userId =?1")
    List<Long> findStoreIdByUserId(Long userId);
}
