package com.feast.demo.web.service;

import com.feast.demo.store.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private com.feast.demo.store.service.StoreService storeRemoteService;

    public Store getStoreInfo(Long storeId){
        return storeRemoteService.getStoreInfo(storeId);
    }
}
