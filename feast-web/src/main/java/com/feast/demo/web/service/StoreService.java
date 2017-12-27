package com.feast.demo.web.service;

import com.feast.demo.store.entity.HistoryPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    @Autowired
    private com.feast.demo.store.service.StoreService storeRemoteService;


    public HistoryPerson findByUserIdAndStoreId(Long userId,Long storeId) {
        return storeRemoteService.findByUserIdAndStoreId(userId,storeId);
    }

    public void save(HistoryPerson history) {
        storeRemoteService.save(history);
    }
}
