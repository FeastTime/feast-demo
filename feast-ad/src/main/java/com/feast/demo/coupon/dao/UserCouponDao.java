package com.feast.demo.coupon.dao;

import com.feast.demo.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface UserCouponDao extends PagingAndSortingRepository<UserCoupon,Long>{

    @Query("select uc from UserCoupon uc where uc.userId = ?1")
    ArrayList<UserCoupon> findByUserId(Long userId);

    UserCoupon findByStoreIdAndUserIdAndCouponCode(Long storeId, Long userId, String couponCode);

    ArrayList<UserCoupon> findByStoreIdAndIsUse(Long storeId, Integer isUse);


}
