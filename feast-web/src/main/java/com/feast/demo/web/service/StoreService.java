package com.feast.demo.web.service;

import com.feast.demo.store.entity.Store;
import com.feast.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StoreService {

    @Autowired
    private com.feast.demo.store.service.StoreService storeRemoteService;

    public Store getStoreInfo(Long storeId){
        return storeRemoteService.getStoreInfo(storeId);
    }

    public ArrayList<User> queryHadVisitUser(Long storeId) {
        return storeRemoteService.queryHadVisitUser(storeId);
    }

    public String findStoreName(Long storeId) {
        return storeRemoteService.findStoreName(storeId);
    }
}
