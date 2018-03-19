package com.feast.demo.web.service;

import com.feast.demo.coupon.entity.CouponTemplate;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageAutoSendTime;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedPackageService {

    @Autowired
    private com.feast.demo.redPackage.service.RedPackageService redPackageRemoteService;

    public void setRedPackageIsUse(Long redPackageId, Long storeId) {

        redPackageRemoteService.setAutoRedPackageIsUse(redPackageId,storeId);

    }

    public List<RedPackage> queryRedPackageList(Long storeId, Integer isCouponEnough) {
        return redPackageRemoteService.queryRedPackageList(storeId,isCouponEnough);
    }

    public void setRedPackageAutoSendTime( Integer time,Long storeId) {
        redPackageRemoteService.setRedPackageAutoSendTime(time,storeId);
    }


    public void createRedPackage(RedPackage redPackage, List<RedPackageCouponTemplate> redPackageCouponTemplates) {
        redPackageRemoteService.createRedPackage(redPackage, redPackageCouponTemplates);
    }

    public ArrayList<RedPackageCouponTemplate> findRedPackageCouponTemplateByRedPackageId(Long id) {
        return redPackageRemoteService.findRedPackageCouponTemplateByRedPackageId(id);
    }

    public List<RedPackage> findRedPackageByIsUse(Integer isUse) {
        return redPackageRemoteService.findRedPackageByIsUse(isUse);
    }

    public RedPackage findByIsUseAndStoreId(Integer isUse,Long storeId) {
        return redPackageRemoteService.findByIsUseAndStoreId(isUse,storeId);

}

    public void setRedPackageIsNotUse(Long redPackageId) {
        redPackageRemoteService.setRedPackageIsNotUse(redPackageId);
    }

    public ArrayList<CouponTemplate> queryCouponInRedPackage(Long redPackageId) {
        return redPackageRemoteService.queryCouponInRedPackage(redPackageId);
    }

    public void deleteAutoRedPackage(Long redPackageId) {
        redPackageRemoteService.deleteAutoRedPackage(redPackageId);
    }

    public int findAutoSendTimeByStoreId(Long storeId) {
        int autoSendTime = redPackageRemoteService.findAutoSendTimeByStoreId(storeId);
        System.out.println(autoSendTime+"  ooooooooooo");
        return autoSendTime;
    }

    public RedPackageAutoSendTime findByStoreId(Long storeId) {
        return redPackageRemoteService.findByStoreId(storeId);
    }

    public void save(RedPackageAutoSendTime redPackageAutoSendTime_) {
        redPackageRemoteService.save(redPackageAutoSendTime_);
    }

    public int queryRedPackageIsCouponEnough(Long redPackageId){
        return redPackageRemoteService.queryRedPackageIsCouponEnough(redPackageId);
    }
}
