package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RedPackageDao extends PagingAndSortingRepository<RedPackage,Long>{

    List<RedPackage> findRedPackageByIsUse(Integer isUse);

    @Modifying
    @Query("update RedPackage rp set rp.isUse = ?1 where rp.storeId = ?2 and rp.redPackageId <> ?3")
    void updateRedPackageId(Integer isUse,Long storeId, Long redPackageId);

    @Query("select rd from RedPackage rd where rd.isUse = ?1 and rd.storeId = ?2")
    RedPackage findByIsUseAndStoreId(Integer isUse, Long storeId);

    @Modifying
    @Query("update RedPackage rp set rp.isUse = ?1 where rp.redPackageId = ?2")
    void updateByRedPackageIdAndIsUse(Integer isUse,Long redPackageId);

    @Query(value = "select ifnull(min(c.coupon_count >= b.coupon_count),0) as is_coupon_enough, a.* from red_package a left join redpackage_coupontemplate b on a.red_package_id = b.red_package_id left join coupon_template c on b.coupon_template_id = c.id where a.store_id = ?1 group by a.red_package_id",nativeQuery = true)
    List<Object[]> queryRedPackageList(Long storeId);

    @Query(value = "select ifnull(min(c.coupon_count >= b.coupon_count),0) as is_coupon_enough from red_package a left join redpackage_coupontemplate b on a.red_package_id = b.red_package_id \n" +
            "left join coupon_template c on b.coupon_template_id = c.id \n" +
            "where a.red_package_id = ?1",nativeQuery = true)
    Object queryRedPackageIsCouponEnough(Long redPackageId);
}
