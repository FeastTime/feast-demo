package com.feast.demo.web.service;

import com.alibaba.fastjson.JSONArray;
import com.feast.demo.redPackage.entity.RedPackage;
import com.feast.demo.redPackage.service.RedPackageService;
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

    public void createRedPackage(RedPackage redPackage, List<Long> couponTemplateIds) {
        redPackageService.createRedPackage(redPackage,couponTemplateIds);
    }

    public void setRedPackageIsUse(Long redPackageId, Long storeId) {
        redPackageService.setRedPackageIsUse(redPackageId,storeId);
    }

    public List<RedPackage> queryRedPackageList(Long storeId) {
        return redPackageService.queryRedPackageList(storeId);
    }

    public void setRedPackageAutoSendTime( Integer time,Long storeId) {
        redPackageService.setRedPackageAutoSendTime(time,storeId);
    }

    public List<RedPackage> findRedPackageByIsUse(Integer isUse) {
        return redPackageService.findRedPackageByIsUse(isUse);
    }
}
