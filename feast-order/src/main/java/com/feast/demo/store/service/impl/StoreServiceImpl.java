package com.feast.demo.store.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.feast.demo.store.dao.StoreDao;
import com.feast.demo.store.entity.Store;
import com.feast.demo.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ggke on 2017/8/1.
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;


    public Store findById(Long id) {
        return storeDao.findOne(id);
    }

    public Store getStoreInfo(Long storeId) {
        return storeDao.findOne(storeId);
    }


}
