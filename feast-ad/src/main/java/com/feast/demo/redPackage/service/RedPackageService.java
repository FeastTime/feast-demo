package com.feast.demo.redPackage.service;

import com.alibaba.dubbo.common.json.JSONArray;
import com.feast.demo.redPackage.entity.RedPackage;

import java.util.List;

public interface RedPackageService {

    public void createRedPackage(RedPackage redPackage, List<Long> couponTemplateIds);

    public void setRedPackageIsUse(Long redPackageId, Long storeId);

    public List<RedPackage> queryRedPackageList(Long storeId);

    public void setRedPackageAutoSendTime(Integer time,Long storeId);
}
