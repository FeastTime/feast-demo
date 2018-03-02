package com.feast.demo.redPackage.service;

import com.alibaba.dubbo.common.json.JSONArray;
import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;

import java.util.List;

public interface RedPackageService {

    public void createRedPackage(RedPackage redPackage, List<RedPackageCouponTemplate> redPackageCouponTemplateIds);

    public void setAutoRedPackageIsUse(Long redPackageId, Long storeId);

    public List<RedPackage> queryRedPackageList(Long storeId);

    public void setRedPackageAutoSendTime(Integer time,Long storeId);

    public List<RedPackageCouponTemplate> findRedPackageCouponTemplateByRedPackageId(Long id);

    public List<RedPackage> findRedPackageByStoreIdAndIsUse(List<Long> storeIds, Integer isUse);

    public List<RedPackage> findRedPackageByIsUse(Integer isUse);
}
