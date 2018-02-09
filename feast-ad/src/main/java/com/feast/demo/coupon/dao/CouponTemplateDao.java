package com.feast.demo.coupon.dao;

import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

/**
 * Created by ggke on 2017/8/26.
 */
public interface CouponTemplateDao extends PagingAndSortingRepository<CouponTemplate,Long>,CouponDaoCustom  {


    @Query("select ct from CouponTemplate ct order by ct.lastModified desc")
    ArrayList<CouponTemplate> findByStoreId(Long storeId);
}
