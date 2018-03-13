package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

public interface RedPackageCouponTemplateDao extends PagingAndSortingRepository<RedPackageCouponTemplate,Long>{


    ArrayList<RedPackageCouponTemplate> findByRedPackageId(Long id);

    @Query("select rc.couponTemplateId from RedPackageCouponTemplate rc where rc.redPackageId = ?1")
    ArrayList<Long> findCouponTemplateIdByRedPackageId(Long redPackageId);

    @Modifying
    @Query("delete from RedPackageCouponTemplate rc where rc.redPackageId = ?1")
    void deleteByRedPackageId(Long redPackageId);
}
