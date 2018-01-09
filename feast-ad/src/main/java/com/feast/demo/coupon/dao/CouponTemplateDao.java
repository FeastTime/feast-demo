package com.feast.demo.coupon.dao;

import com.feast.demo.coupon.entity.CouponTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ggke on 2017/8/26.
 */
public interface CouponTemplateDao extends PagingAndSortingRepository<CouponTemplate,Long>,CouponDaoCustom  {

}
