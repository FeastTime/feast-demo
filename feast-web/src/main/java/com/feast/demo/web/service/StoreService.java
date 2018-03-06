package com.feast.demo.web.service;

import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.entity.RedPackageCouponTemplate;
import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private com.feast.demo.store.service.StoreService storeRemoteService;

    @Autowired
    private com.feast.demo.redPackage.service.RedPackageService redPackageService;

    public Store getStoreInfo(Long storeId){
        return storeRemoteService.getStoreInfo(storeId);
    }

    public ArrayList<User> queryHadVisitUser(Long storeId) {
        return storeRemoteService.queryHadVisitUser(storeId);
    }

    public String findStoreName(Long storeId) {
        return storeRemoteService.findStoreName(storeId);
    }



    public void setRedPackageIsUse(Long redPackageId, Long storeId) {


        redPackageService.setAutoRedPackageIsUse(redPackageId,storeId);

    }

    public List<RedPackage> queryRedPackageList(Long storeId) {
        return redPackageService.queryRedPackageList(storeId);
    }

    public void setRedPackageAutoSendTime( Integer time,Long storeId) {
        redPackageService.setRedPackageAutoSendTime(time,storeId);
    }


    public void createRedPackage(RedPackage redPackage, List<RedPackageCouponTemplate> redPackageCouponTemplates) {
        redPackageService.createRedPackage(redPackage, redPackageCouponTemplates);
    }

    public List<RedPackageCouponTemplate> findRedPackageCouponTemplateByRedPackageId(Long id) {
        return redPackageService.findRedPackageCouponTemplateByRedPackageId(id);
    }

    public List<RedPackage> findRedPackageByStoreIdAndIsUse(List<Long> storeIds, Integer isUse) {
        return redPackageService.findRedPackageByStoreIdAndIsUse(storeIds,isUse);
    }

    public List<RedPackage> findRedPackageByIsUse(Integer isUse) {
        return redPackageService.findRedPackageByIsUse(isUse);
    }

    public ArrayList<Store> getStoreInfoList(ArrayList<Long> storeIds) {
        return storeRemoteService.getStoreInfoList(storeIds);
    }

    public RedPackage findByIsUseAndStoreId(Integer isUse,Long storeId) {
        return redPackageService.findByIsUseAndStoreId(isUse,storeId);
    }
}
