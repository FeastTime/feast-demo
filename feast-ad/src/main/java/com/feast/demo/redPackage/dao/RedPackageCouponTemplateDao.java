package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RedPackageCouponTemplateDao extends PagingAndSortingRepository<RedPackageCouponTemplate,Long>{


    List<RedPackageCouponTemplate> findByRedPackageId(Long id);
}
